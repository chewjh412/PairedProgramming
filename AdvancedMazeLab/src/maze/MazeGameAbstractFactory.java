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

import maze.ui.MazeViewer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * 
 * @author Sunny
 * @version 1.0
 * @since 1.0
 */
public class MazeGameAbstractFactory
{
	/**
	 * Creates a small maze.
	 */
	public Maze createMaze(String path, MazeFactory mazeGame){
		Maze maze = null;
		if(path==null){ // No path, create a sample maze
			maze = mazeGame.MakeMaze();
			Room r0 = mazeGame.MakeRoom(0);
			Room r1 = mazeGame.MakeRoom(1);
			Door d1 = mazeGame.MakeDoor(r0, r1);
			d1.setOpen(false);

			r0.setSide(Direction.North, mazeGame.MakeWall());
			r0.setSide(Direction.East, mazeGame.MakeDoor(r0, r1));
			r0.setSide(Direction.South, mazeGame.MakeWall());
			r0.setSide(Direction.West, mazeGame.MakeWall());
			r1.setSide(Direction.North, mazeGame.MakeWall());
			r1.setSide(Direction.East, mazeGame.MakeWall());
			r1.setSide(Direction.South, mazeGame.MakeWall());
			r1.setSide(Direction.West, mazeGame.MakeDoor(r0, r1));
			
			maze.addRoom(r0);
			maze.addRoom(r1);
			
			return maze;
		} 
		else{
			maze = mazeGame.MakeMaze();
			//Pre-defined arrays
			String[] commands = new String[31]; 
			String[] splitted = new String[100];
			String[] splitted2= new String[31];
			//arraylists for adding doors and rooms
			List<Room> rooms = new ArrayList<Room>();
			List<Door> doors = new ArrayList<Door>();
			int i=0, roomCount=0; //i for indexing BufferedReader
			String north=null, south=null, east=null, west=null;
			char doorName; // for use of the door's value
			try {
				BufferedReader file = new BufferedReader(new FileReader(path)); //file input
				for(String line=file.readLine(); line!=null; line=file.readLine()){
					commands[i]=line; //save commands to a local variable
					i++;
				}
				file.close();
			}
			
			//exception handling
			catch (FileNotFoundException e) {
				System.out.println(e);
			}
			catch(IOException e){
				System.out.println(e);
			}
	
			for(int z=0;z<commands.length;z++){ //for all commands
				if(commands[z]!=null){ //ignore null instances within array 
					//System.out.println(commands[z]);
					splitted=commands[z].toString().split("\\s+"); //split row of commands up
					if("room".equals(splitted[0])&&(splitted[0]!=null)){ //instructions for creating rooms
						north=splitted[2]; //instructions for the rooms when setting the walls
						south=splitted[3];
						east=splitted[4];
						west=splitted[5];
						Room roomObj = mazeGame.MakeRoom(Integer.parseInt(splitted[1])); // get the room object for adding
						rooms.add(roomObj);
						maze.addRoom(roomObj);
					}
					else if("door".equals(splitted[0])&&(splitted[0]!=null)){ //instructions for creating doors
						int room1 = Integer.parseInt(splitted[2]);
						int room2 = Integer.parseInt(splitted[3]);
						boolean hodor = ("close".equals(splitted[4]))?false:true;
						//doors.add(newDoor(rooms.get(room1),rooms.get(room2),hodor)); //helper method in MazeFactory.java for creating doors
						
						//TODO: add door, get door from arraylist, set door to open
						doors.add(mazeGame.MakeDoor(rooms.get(room1),rooms.get(room2)));
						doorName = splitted[1].charAt(1);
						Door dor = doors.get(Character.getNumericValue(doorName));
						dor.setOpen(hodor);
					}
					else{ // debugging
						System.out.println("else condition");
					}
				}
			}
	
			for(int w=0;w<commands.length;w++){//loop through commands again
				if(commands[w]!=null){ //ignore null instances within array 
					splitted2=commands[w].toString().split("\\s+"); //split row of commands up
					if("room".equals(splitted2[0])&&(splitted2[0]!=null)){
						Room roomList=rooms.get(roomCount);//roomCount for iterating
						roomCount++;
						//Wall construction for each direction
						//Case for making a wall, setting doors, or orienting rooms
						if("wall".equals(splitted2[2])&&(splitted2[2]!=null)){
							roomList.setSide(Direction.North, mazeGame.MakeWall());
						}
						else if((splitted2[2].charAt(0)=='d')&&(splitted2[2]!=null)){
							//System.out.println("Hey, I found a door in the west!");
							doorName=splitted2[2].charAt(1);
							roomList.setSide(Direction.North, doors.get(Character.getNumericValue(doorName)));
						}
						else{
							//System.out.println(rooms.get(Integer.parseInt(splitted2[2])));
							roomList.setSide(Direction.North, rooms.get(Integer.parseInt(splitted2[2])));
						}
						if("wall".equals(splitted2[3])&&(splitted2[3]!=null)){
							roomList.setSide(Direction.South, mazeGame.MakeWall());
						}
						else if((splitted2[3].charAt(0)=='d')&&(splitted2[3]!=null)){
							//System.out.println("Hey, I found a door in the west!");
							doorName=splitted2[3].charAt(1);
							//System.out.print(doorName);
							roomList.setSide(Direction.South, doors.get(Character.getNumericValue(doorName)));
						}
						else{
							//System.out.println(rooms.get(Integer.parseInt(splitted2[3])));
							roomList.setSide(Direction.South, rooms.get(Integer.parseInt(splitted2[3])));
						}
						if("wall".equals(splitted2[4])&&(splitted2[4]!=null)){
							roomList.setSide(Direction.East, mazeGame.MakeWall());
						}
						else if((splitted2[4].charAt(0)=='d')&&(splitted2[4]!=null)){
							//System.out.println("Hey, I found a door in the west!");
							doorName=splitted2[4].charAt(1);
							roomList.setSide(Direction.East, doors.get(Character.getNumericValue(doorName)));
						}
						else{
							//System.out.println(rooms.get(Integer.parseInt(splitted2[4])));
							roomList.setSide(Direction.East, rooms.get(Integer.parseInt(splitted2[4])));
						}
						if("wall".equals(splitted2[5])&&(splitted2[5]!=null)){
							roomList.setSide(Direction.West, mazeGame.MakeWall());
						}
						else if((splitted2[5].charAt(0)=='d')&&(splitted2[5]!=null)){
							//System.out.println("Hey, I found a door in the west!");
							doorName=splitted2[5].charAt(1);
							roomList.setSide(Direction.West, doors.get(Character.getNumericValue(doorName)));
						}
						else{
							//System.out.println(rooms.get(Integer.parseInt(splitted2[5])));
							roomList.setSide(Direction.West, rooms.get(Integer.parseInt(splitted2[5])));
						}
					}
				}
			} //End for
			return maze;
		}// End if
	}// End createMaze
}
