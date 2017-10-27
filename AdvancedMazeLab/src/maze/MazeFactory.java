package maze;

public class MazeFactory{

	//Factory create maze method
	public Maze MakeMaze(){
		return new Maze();
	}
	
	//Factory create wall method
	public Wall MakeWall(){
		return new Wall();
	}
	
	//Factory create room method
	public Door MakeDoor(Room room1, Room room2){
		return new Door(room1,room2);
	}
	
	//Factory create room method
	public Room MakeRoom(int num){
		return new Room(num);
	}
	
	//Helper create door method to handle rooms and boolean open
	protected Door newDoor(Room room1, Room room2, boolean open){
		Door door = new Door(room1,room2);
		door.setOpen(open);
		
		return door;
	}
}