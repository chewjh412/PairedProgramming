package maze;

import java.awt.Color;

class RedRoom extends Room{

	public RedRoom(int num) {
		super(num);
		// TODO Auto-generated constructor stub
	}
	@Override
	public Color getColor()
	{
		return Color.RED;
	}
}