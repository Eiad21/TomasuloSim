package components;

import utility.OP;

public class ReservationStation {
	private boolean busy;
	private OP op;
	private double vJ, vK;
	private ReservationStation qJ, qK;
	private int A;
	private int timeRemExec;
	
	public ReservationStation() {
		
	}
	
	public boolean isBusy() {
		return busy;
	}
	
	public void issueInst(OP op, int vJ, int vK, ReservationStation qJ, ReservationStation qK) {
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
	
	public void run() {
		timeRemExec--;
	}
}
