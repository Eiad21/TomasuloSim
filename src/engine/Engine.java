package engine;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import components.InstructionUnit;
import components.MemoryUnit;
import components.RegisterFile;
import components.ReservationStation;
import utility.Instruction;
import utility.OP;
import utility.RFEntry;

public class Engine {
	int PC = 0;
	int cycle = 1;
	
	private RegisterFile registerFile;
	private InstructionUnit insUnit;
	public MemoryUnit mem ;
	
	private ReservationStation[] muls;
	private ReservationStation[] adds;
	private ReservationStation[] loads;
	private ReservationStation[] stores;
	
	private Queue<RFEntry> buffer;
	String [] test = {"add 0 1 2","sub 0 1 2"};
	
	public Engine() {
		adds = new ReservationStation[3];
		muls = new ReservationStation[2];
		loads = new ReservationStation[4];
		stores = new ReservationStation[4];
		
		buffer = new LinkedList<RFEntry>();
		
		for(int i = 0;i<adds.length;i++) {
			adds[i] = new ReservationStation(this);
		}
		for(int i = 0;i<muls.length;i++) {
			muls[i] = new ReservationStation(this);
		}
		for(int i = 0;i<loads.length;i++) {
			loads[i] = new ReservationStation(this);
		}
		for(int i = 0;i<stores.length;i++) {
			stores[i] = new ReservationStation(this);
		}
		insUnit= new InstructionUnit();
		registerFile = new RegisterFile();
		mem=new MemoryUnit();
	}
	
	public void publish(ReservationStation rs, double result) {
		RFEntry rf = new RFEntry(result, rs);
		buffer.add(rf);
	}
	
	public void reserveLocation(ReservationStation rs, int dest) {
		registerFile.registerFile[dest].qI = rs;
	}
	
	public void writeBack() {
		RFEntry rf = buffer.poll();
		if(rf!=null) {
			for(int i = 0;i<adds.length;i++) {
				adds[i].writeBack(rf.qI, rf.value);
			}
			for(int i = 0;i<muls.length;i++) {
				muls[i].writeBack(rf.qI, rf.value);
			}
			for(int i = 0;i<stores.length;i++) {
				stores[i].writeBack(rf.qI, rf.value);
			}
			registerFile.writeBack(rf.qI, rf.value);
		}
	}
	
	public void execute() {
		for(int i = 0;i<adds.length;i++) {
			adds[i].run();
		}
		for(int i = 0;i<muls.length;i++) {
			muls[i].run();
		}
		for(int i = 0;i<stores.length;i++) {
			stores[i].run();
		}
		for(int i = 0;i<loads.length;i++) {
			loads[i].run();
		}

	}
	
	public void issue() {
		if(insUnit.instructionUnit.size()<=PC) {
			System.out.println("cant issue");
			return;
		}
		Instruction inst = insUnit.instructionUnit.get(PC);
		ReservationStation rs = null;
		
		// Check for structural hazard adds
		if(inst.op==OP.ADD||inst.op==OP.SUB) {
			for(int i=0;i<adds.length;i++) {
				if(!adds[i].isBusy()) {
					rs=adds[i];
					break;
				}
			}
		}
		
		// Check for structural hazard muls
		if(inst.op==OP.MUL||inst.op==OP.DIV) {
			for(int i=0;i<muls.length;i++) {
				if(!muls[i].isBusy()) {
					rs=muls[i];
					break;
				}
			}
		}
		// HERE: checked isBusy
		// Check if we can perform store
		if(inst.op==OP.STORE) {
			// Dependency on previous store
			for(int i=0;i<stores.length;i++) { 
				if(stores[i].isBusy() && stores[i].A==inst.reg2) { 
					return;
				}
			}
			// Dependency on previous load
			for(int i=0;i<loads.length;i++) { 
				if(stores[i].isBusy() && loads[i].A==inst.reg2) { 
					return;
				}
			}
			// HERE: commented
			// Check for structural hazard stores
			for(int i=0;i<stores.length;i++) {
				if(!stores[i].isBusy()) {
					rs=stores[i];            
					break;
				}
			}
		}
		//HERE: again isBusy
		// Check if we can perform load
		if(inst.op==OP.LOAD) {
			for(int i=0;i<stores.length;i++) { 
				if(stores[i].isBusy() && stores[i].A==inst.reg2) {
					return;
				}
			}
			
			// Check for structural hazard loads
			for(int i=0;i<loads.length;i++) {
				if(!loads[i].isBusy()) {
					rs=loads[i];
					break;
				}
			}
		}
		if(rs!=null&&(inst.op==OP.ADD||inst.op==OP.SUB||inst.op==OP.MUL||inst.op==OP.DIV)) {
			RFEntry entry1 = registerFile.getEntry(inst.reg1);
			RFEntry entry2 = registerFile.getEntry(inst.reg2);
			double vJ = entry1.value;
			double vK = entry2.value;
			ReservationStation qJ = entry1.qI;
			ReservationStation qK = entry2.qI;
			
			reserveLocation(rs, inst.dest);
			rs.issueInst(inst.op, vJ, vK, qJ, qK);
			
			PC++;
		}
		if(rs!=null&&(inst.op==OP.STORE||inst.op==OP.LOAD)) {
			RFEntry entry1 = registerFile.getEntry(inst.reg1);
			double vJ = entry1.value;
			ReservationStation qJ = entry1.qI;
			
			if(inst.op == OP.LOAD) {
				reserveLocation(rs, inst.dest);
			}
			rs.issueInst(inst.op, vJ, 0, qJ, null);
			
			PC++;
		}
		
	}
	
	
	public void displayCycleInfo() {
		
		System.out.println(registerFile);
		System.out.println("*********************");
		for(int i = 0;i<adds.length;i++) {
			if(adds[i].isBusy())
				System.out.println(adds[i].toString());
		}
		for(int i = 0;i<muls.length;i++) {
			if(muls[i].isBusy())
				System.out.println(muls[i].toString());
		}
		for(int i = 0;i<stores.length;i++) {
			if(stores[i].isBusy())
				System.out.println(stores[i].toString());
		}
		for(int i = 0;i<loads.length;i++) {
			if(loads[i].isBusy())
				System.out.println(loads[i].toString());
		}
	}
	// Writing Back
	// Execute
	// Issue
	
	public void runCycle() {
		writeBack();
		execute();
		issue();		
		displayCycleInfo();
		System.out.println("---------------------------------");
	}
	
	public static void main(String[]args) {
		Engine eng = new Engine();
		eng.insUnit.instructionUnit.add(new Instruction(OP.ADD, 0, 2, 3));
		eng.insUnit.instructionUnit.add(new Instruction(OP.SUB, 1, 0, 4));
		
		eng.runCycle();
		eng.runCycle();
		eng.runCycle();
		eng.runCycle();
		eng.runCycle();
		eng.runCycle();
		eng.runCycle();
		eng.runCycle();
		eng.runCycle();
		eng.runCycle();
		eng.runCycle();
		eng.runCycle();
	}
	
	public void init() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("please enter number of instructions ?");
		int insCount=scanner.nextInt();
		insUnit=new InstructionUnit();
		for(int i=0;i<insCount;i++) {
				System.out.println("enter instruction " + (i+1)+" type :");
				String temp1=scanner.next();
				System.out.println("enter instruction " + (i+1)+" destination :");
				int temp2=scanner.nextInt();
				System.out.println("enter instruction " + (i+1)+" register 1 :");
				int temp3=scanner.nextInt();
				System.out.println("enter instruction " + (i+1)+" register 2 :");
				int temp4=scanner.nextInt();
			
				if(temp1.equals("add"));
				insUnit.instructionUnit.add(new Instruction(OP.ADD, temp2, temp3, temp4));
				if(temp1.equals("sub"));
				insUnit.instructionUnit.add(new Instruction(OP.SUB, temp2, temp3, temp4));
				if(temp1.equals("mul"));
				insUnit.instructionUnit.add(new Instruction(OP.MUL, temp2, temp3, temp4));
				if(temp1.equals("div"));
				insUnit.instructionUnit.add(new Instruction(OP.DIV, temp2, temp3, temp4));
				if(temp1.equals("load"));
				insUnit.instructionUnit.add(new Instruction(OP.LOAD, temp2, temp3, temp4));
				if(temp1.equals("store"));
				insUnit.instructionUnit.add(new Instruction(OP.STORE, temp2, temp3, temp4));		
			}
		}
}
