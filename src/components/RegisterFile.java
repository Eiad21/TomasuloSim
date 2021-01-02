package components;

import utility.*;
import components.*;
import java.util.*;

public class RegisterFile {
	private RFEntry[] registerFile;
	
	public RFEntry getEntry(int index) {
		return registerFile[index];
	}
	
	public void writeBack(ReservationStation rs ,double res) {
		for(RFEntry x:this.registerFile) {
			if(rs==x.qI)
			{
				x.value =res ;
				x.qI=null;
			}	
		}
	}
}
