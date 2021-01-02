package components;

import engine.Engine;
import utility.OP;

public class ReservationStation {
	private boolean busy;
	private OP op;
	private double vJ, vK;
	private ReservationStation qJ, qK;
	private int A;
	private double result;
	private int timeRemExec;
	private Engine engine;
	public ReservationStation(Engine engine) {
		busy = false;
		this.engine = engine;
		
	}
	
	public boolean isBusy() {
		return busy;
	}
	
	public void issueInst(OP op, double vJ, double vK, ReservationStation qJ, ReservationStation qK) {
		this.op = op;
		this.vJ = vJ;
		this.vK = vK;
		this.qJ = qJ;
		this.qK = qK;
		timeRemExec = op.execTime();
		busy = true;
	}
	
	public void flushInst(){
		busy = false;
	}
	
	private void setResult() {
		switch(this.op) {
        case ADD: result =  vJ + vK;return;
        case SUB: result =  vJ - vK;return;
        case MUL: result =  vJ * vK;return;
        case DIV: result =  vJ / vK;return;
//        case LOAD:
//        case STORE:
        default: result =  0;return;
		}
	}
	
	public void run() {
		timeRemExec--;
		if(timeRemExec == 0) {
			setResult();
		}
		else if(timeRemExec == -1) {
			busy = false;
			engine.publish(this, result);
		}
		
		
	}
	public void writeBack(ReservationStation rs ,double res) {
		if(rs==qJ)
		{
			vJ =res ;
			qJ=null;
		}
		
		if(rs==qK)
		{
			vK =res ;
			qK=null;
		}
	}
}
