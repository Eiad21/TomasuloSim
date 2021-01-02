package utility;

public class Instruction {
public OP op ;
public int dest;
public int reg1;
public int reg2;
public Instruction(OP op, int dest, int reg1, int reg2) {
	super();
	this.op = op;
	this.dest = dest;
	this.reg1 = reg1;
	this.reg2 = reg2;
}


}
