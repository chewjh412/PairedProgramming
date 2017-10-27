package maze;

public class BlueMazeFactory extends MazeFactory {

	public BlueWall makeWall() {
	return new BlueWall();
	}

	public GreenRoom makeRoom(int id) {
	return new GreenRoom(id);
	}

	public BrownDoor makeDoor(Room r1, Room r2) {
	return new BrownDoor(r1, r2);
	}
}