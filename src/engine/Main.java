package engine;

import utility.*;
import components.*;
import java.util.*;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

public class Main {

	static int cycle;
	static InstructionUnit insUnit;
	public static void main(String[] args) {
		init();
	}

	static void displayCycle() {
		System.out.println("current cycle " + cycle);
		System.out.println("------------------------");

	}
	static void init() {
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
