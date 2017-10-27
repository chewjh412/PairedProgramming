/*
 * SimpleMazeGame.java
 * Copyright (c) 2008, Drexel University.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Drexel University nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY DREXEL UNIVERSITY ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL DREXEL UNIVERSITY BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package maze;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import maze.ui.MazeViewer;

/**
 * 
 * @author Sunny
 * @version 1.0
 * @since 1.0
 */
public class SimpleMazeGame
{
	/**
	 * Creates a small maze.
	 */
	
	public static void main(String[] args)
	{
//		Maze maze = createMaze();
		Maze maze = loadMaze("C:\\git\\PairedProgramming\\chewjh412\\amaizeing\\mazeLab\\lab1\\large.maze");
	    MazeViewer viewer = new MazeViewer(maze);
	    viewer.run();
	}
	
	public static Maze createMaze()
	{
		Maze maze = new Maze();
		Room roomOne = new Room(0);
		Room roomTwo = new Room(1);
		roomOne = CreateRoomOne(roomOne, roomTwo);
		roomTwo = CreateRoomTwo(roomOne, roomTwo);
		
		maze.addRoom(roomOne);
		maze.addRoom(roomTwo);
		maze.setCurrentRoom(roomOne);
		return maze;
	}

	public static Maze loadMaze(final String path)
	{
		Maze maze = new Maze();
		List<Room> rooms = new ArrayList<Room>();
		List<Door> doors = new ArrayList<Door>();
		List<String> lines = new ArrayList<String>();
		String[] lineSplit = new String[100];
		
		try
		{
			int i = 0;
			BufferedReader file = new BufferedReader(new FileReader(path));
			for (String line = file.readLine(); line != null; line = file.readLine()) {
				lines.add(line);
				i++;
			}
		}
		catch (Exception e)
		{
			Print(e.toString());
		}
		
		// Create empty rooms in the room array, then create the door objects
		for (int i = 0; i < lines.size(); i++) {
			lineSplit = lines.get(i).split("\\s+");
			if (Constants.room.equals(lineSplit[0].toLowerCase())) {
				rooms.add(new Room(Integer.parseInt(lineSplit[1])));
			}
			else if (Constants.door.equals(lineSplit[0].toLowerCase())) {
				int roomOne = Integer.parseInt(lineSplit[2]);
				int roomTwo = Integer.parseInt(lineSplit[3]);
				boolean isOpen = (Constants.close.equals(lineSplit[4].toLowerCase())) ? false : true;
				
				doors.add(NewDoor(rooms.get(roomOne), rooms.get(roomTwo), isOpen));
			}
		}
		

		// Add the rooms to the maze, then return the maze to Main()
		maze.addRoom(rooms.get(0));
		maze.setCurrentRoom(rooms.get(0));
		for (int i = 1; i < rooms.size(); i++) {
			maze.addRoom(rooms.get(i));
		}
		
		// Fill the Rooms with walls and doors as necessary from Load File
		Room currentRoom;
		for (int i = 0; i < lines.size(); i++) {
			lineSplit = lines.get(i).split("\\s+");
			
			if (Constants.room.equals(lineSplit[0].toLowerCase())) {
				currentRoom = rooms.get(Integer.parseInt(lineSplit[1]));
				
				if (Constants.wall.equals(lineSplit[2].toLowerCase())) {
					currentRoom = SetNorth(currentRoom, new Wall());
				}
				else if (lineSplit[0].toLowerCase().charAt(0) == 'd') {
					int doorNum = Character.getNumericValue(lineSplit[0].toLowerCase().charAt(1));
					currentRoom = SetNorth(currentRoom, doors.get(doorNum));
				}
				
				if (Constants.wall.equals(lineSplit[2].toLowerCase())) {
					currentRoom = SetSouth(currentRoom, new Wall());
				}
				else if (lineSplit[0].toLowerCase().charAt(0) == 'd') {
					int doorNum = Character.getNumericValue(lineSplit[0].toLowerCase().charAt(1));
					currentRoom = SetSouth(currentRoom, doors.get(doorNum));
				}
				
				if (Constants.wall.equals(lineSplit[2].toLowerCase())) {
					currentRoom = SetEast(currentRoom, new Wall());
				}
				else if (lineSplit[0].toLowerCase().charAt(0) == 'd') {
					int doorNum = Character.getNumericValue(lineSplit[0].toLowerCase().charAt(1));
					currentRoom = SetEast(currentRoom, doors.get(doorNum));
				}
				
				if (Constants.wall.equals(lineSplit[2].toLowerCase())) {
					currentRoom = SetWest(currentRoom, new Wall());
				}
				else if (lineSplit[0].toLowerCase().charAt(0) == 'd') {
					int doorNum = Character.getNumericValue(lineSplit[0].toLowerCase().charAt(1));
					currentRoom = SetWest(currentRoom, doors.get(doorNum));
				}
			}
			else {
				break;
			}
		}
				
		System.out.println("Maze loaded!");
		return maze;
	}
	
	private static Room SetNorth(Room roomIn, MapSite siteIn) {
		roomIn.setSide(Direction.North, siteIn);
		
		return roomIn;
	}
	
	private static Room SetSouth(Room roomIn, MapSite siteIn) {
		roomIn.setSide(Direction.South, siteIn);
		
		return roomIn;
	}
	
	private static Room SetEast(Room roomIn, MapSite siteIn) {
		roomIn.setSide(Direction.East, siteIn);
		
		return roomIn;
	}
	
	private static Room SetWest(Room roomIn, MapSite siteIn) {
		roomIn.setSide(Direction.West, siteIn);
		
		return roomIn;
	}
	
	private static Door NewDoor(Room roomOne, Room roomTwo) {
		return new Door(roomOne, roomTwo);
	}
	private static Door NewDoor(Room roomOne, Room roomTwo, boolean isOpen) {
		Door door = new Door(roomOne, roomTwo);
		door.setOpen(isOpen);
		
		return door;
	}
	
	
	private static Room CreateRoomOne(Room roomOne, Room roomTwo) {
		roomOne.setSide(Direction.North, NewDoor(roomOne, roomTwo));
		roomOne.setSide(Direction.East, new Wall());
		roomOne.setSide(Direction.South, new Wall());
		roomOne.setSide(Direction.West, new Wall());
		
		return roomOne;
	}
	
	private static Room CreateRoomTwo(Room roomOne, Room roomTwo) {
		roomTwo.setSide(Direction.North, new Wall());
		roomTwo.setSide(Direction.East, new Wall());
		roomTwo.setSide(Direction.South, NewDoor(roomTwo, roomOne));
		roomTwo.setSide(Direction.West, new Wall());
		
		return roomTwo;
	}
	
	private static void Print(String strIn) {
		System.out.println(strIn);
	}
}
