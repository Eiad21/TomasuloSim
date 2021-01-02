package utility;

import components.ReservationStation;

public class RFEntry {
	public double value;
	public ReservationStation qI;
	
	public RFEntry() {
		
	}
	
	public RFEntry(double value, ReservationStation qI) {
		this.value = value;
		this.qI = qI;
	}
}
