package maze;

import maze.ui.MazeViewer;

class main{
	public static void main(String[] args)
	{
		Maze maze = null;
		MazeGameAbstractFactory game = new MazeGameAbstractFactory();
		//Maze maze = createMaze(); /*Comment out to load a maze from a file*/
		if(args.length==0){
			maze = game.createMaze(null, null);
			maze.setCurrentRoom(0);
		    MazeViewer viewer = new MazeViewer(maze);
		    viewer.run();
		}
		else if(args.length==1){
			if("red".equals(args[0])||"Red".equals(args[0])){
				System.out.println("IS RED");
				RedMazeFactory red = new RedMazeFactory();
				maze = game.createMaze(null,red);
				maze.setCurrentRoom(0);
			    MazeViewer viewer = new MazeViewer(maze);
			    viewer.run();
			}
			if("blue".equals(args[0])||"Blue".equals(args[0])){
				BlueMazeFactory blue = new BlueMazeFactory();
				maze = game.createMaze(null,blue);
				maze.setCurrentRoom(0);
			    MazeViewer viewer = new MazeViewer(maze);
			    viewer.run();
			}
		}
		else if(args.length==2){
			if("red".equals(args[0])||("Red".equals(args[0]))){
				//TODO: Red maze
				System.out.println("Red maze");
				RedMazeFactory red = new RedMazeFactory();
				maze = game.createMaze(args[1],red);
				maze.setCurrentRoom(0);
			    MazeViewer viewer = new MazeViewer(maze);
			    viewer.run();
			}
			else if("blue".equals(args[0])||("Blue".equals(args[0]))){
				//TODO: Blue maze
				BlueMazeFactory blue = new BlueMazeFactory();
				maze = game.createMaze(args[1],blue);
				maze.setCurrentRoom(0);
			    MazeViewer viewer = new MazeViewer(maze);
			    viewer.run();
			}
		}
		else{
			System.out.print("Too many inputs, check input and try again!");
		}
	}
}