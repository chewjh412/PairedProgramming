package maze;

import java.awt.Color;

class BrownDoor extends Door{

	public BrownDoor(Room r1, Room r2) {
		super(r1, r2);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Color getColor(){
		return new Color(139,69,19);
	}
	
}