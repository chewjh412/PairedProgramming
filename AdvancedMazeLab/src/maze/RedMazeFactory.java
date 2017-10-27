package maze;

class RedMazeFactory extends MazeFactory{
	
	//To build red maze...
	public RedWall makeWall(){
		return new RedWall();
	}
	
	public RedRoom makeRoom(int id){
		return new RedRoom(id);
	}
	
}