package engine;

import java.util.LinkedList;
import java.util.Queue;

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
	private ReservationStation[] muls;
	private ReservationStation[] adds;
	private Queue<RFEntry> buffer;
	private ReservationStation[] loads;
	private ReservationStation[] stores;
	public MemoryUnit mem ;
	String [] test = {"add 0 1 2","sub 0 1 2"};
	
	public Engine() {
		muls = new ReservationStation[2];
		adds = new ReservationStation[3];
		loads = new ReservationStation[4];
		stores = new ReservationStation[4];
		
		buffer = new LinkedList<RFEntry>();
		
		for(int i = 0;i<muls.length;i++) {
			muls[i] = new ReservationStation(this);
		}
		for(int i = 0;i<adds.length;i++) {
			adds[i] = new ReservationStation(this);
		}
		insUnit= new InstructionUnit();
		registerFile = new RegisterFile();
		mem=new MemoryUnit();
	}
	
	public void publish(ReservationStation rs, double result) {
		RFEntry rf = new RFEntry(result, rs);
		buffer.add(rf);
	}
	
	public void writeBack() {
		RFEntry rf = buffer.poll();
		for(int i = 0;i<muls.length;i++) {
			muls[i].writeBack(rf.qI, rf.value);
		}
		for(int i = 0;i<adds.length;i++) {
			adds[i].writeBack(rf.qI, rf.value);
		}
		registerFile.writeBack(rf.qI, rf.value);
	}
	
	
	public void issue() {
		if(insUnit.instructionUnit.size()<=PC) {
			System.out.println("cant issue");
			return;
		}
		Instruction inst = insUnit.instructionUnit.get(PC);
		ReservationStation rs = null;
		if(inst.op==OP.ADD||inst.op==OP.SUB) {
			for(int i=0;i<adds.length;i++) {
				if(!adds[i].isBusy()) {
					rs=adds[i];
					break;
				}
			}
		}
		if(inst.op==OP.MUL||inst.op==OP.DIV) {
			for(int i=0;i<muls.length;i++) {
				if(!muls[i].isBusy()) {
					rs=muls[i];
					break;
				}
			}
		}
		
		if(inst.op==OP.STORE) {
			for(int i=0;i<stores.length;i++) {
				if(!stores[i].isBusy()) {
					rs=stores[i];
					break;
				}
			}
		}
		if(inst.op==OP.LOAD) {
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
			
			rs.issueInst(inst.op, vJ, vK, qJ, qK);
			
			PC++;
		}
		if(rs!=null&&(inst.op==OP.STORE||inst.op==OP.LOAD)) {
			RFEntry entry1 = registerFile.getEntry(inst.reg1);
			double vJ = entry1.value;
			ReservationStation qJ = entry1.qI;
			
			rs.issueInst(inst.op, vJ, 0, qJ, null);
			
			PC++;
		}
		
	}
}
