package utility;

public class Instruction {
OP op ;
int dest;
int reg1;
int reg2;
public Instruction(OP op, int dest, int reg1, int reg2) {
	super();
	this.op = op;
	this.dest = dest;
	this.reg1 = reg1;
	this.reg2 = reg2;
}


}
