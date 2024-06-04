import edu.du.dudraw.DUDraw;

/*
 * Author: Charles Jensen
 * Date: 1/13/2022
 * Purpose: Sand World class, sets up canvas and physics of all objects in the world for the sand game.
 */

public class SandWorld {

	//Instance variables
	
	//2D array for each pixel
	public static int pixels[][];
	
	// Current tool in use
	public static int tool = 0;
	
	// Types of objects in the world
	public static final int EMPTY = 0;
	public static final int SAND = 1;
	public static final int FLOOR = 2;
	public static final int WATER = 3;
	public static final int MUD = 4;
	
	//Amount of objects given to player
	public static int amtSand = 10000;
	public static int amtMud = 0;
	
	//Canvas size and stuff
	public static int width = 600;
	public static int widthScale = 300;
	public static int height = 600;
	public static int heightScale = 300;
	
	//Constructor
	public SandWorld() {
		
		//Set canvas size, scale, and enable double buffering
		DUDraw.setCanvasSize(width, height);
		DUDraw.setXscale(0, 300);
		DUDraw.setYscale(0, 300);
		DUDraw.enableDoubleBuffering();
		
		//Create size of pixels array
		pixels = new int[widthScale][heightScale];
		
		//Fill up array with empty particles
		for (int row = 0; row < pixels.length; row++) {
			for (int col = 0; col < pixels[0].length; col++) {
				placeParticle(row, col, EMPTY);
			}
		}
			
		//Show the tool name when object is created
		displayToolName(SandWorld.tool);

	}
	
//	public static boolean placeParticle(int row, int col, int newType) {
//		
//		//Check if the array at row and column is empty, if so then place the object.	
//		if (row < widthScale && row >= 0 && col < heightScale && col >= 0) {
//			if (pixels[row][col] == EMPTY) {
//				pixels[row][col] = newType;
//				swapParticlesWithUpdate(row, col, newType);
//				return true;
//			}
//			// Check if an empty object is replacing something, important for the falling sand
//			else if (pixels[row][col] != EMPTY && newType == EMPTY && row > 0 && row < widthScale && col > 0 && col < heightScale) {
//				pixels[row][col] = newType;
//				swapParticlesWithUpdate(row, col, newType);
//				return true;
//			}
//			else if (pixels[row][col] == WATER && newType == MUD && row > 0 && row < widthScale && col > 0 && col < heightScale) {
//				pixels[row][col] = newType;
//				swapParticlesWithUpdate(row, col, newType);
//				return true;
//			}
//			else if (pixels[row][col] == MUD && newType == WATER && row > 0 && row < widthScale && col > 0 && col < heightScale) {
//				pixels[row][col] = newType;
//				swapParticlesWithUpdate(row, col, newType);
//				return true;
//			}
//			else {
//				return false;
//			}
//		}
//		return false;
//	}
	
public static boolean placeParticle(int row, int col, int newType) {
	
		//Check if the array at row and column is empty, if so then place the object.	
		if (row < widthScale && row >= 0 && col < heightScale && col >= 0) {
			
			if (pixels[row][col] == EMPTY) {
				pixels[row][col] = newType;
				swapParticlesWithUpdate(row, col, newType);
				return true;
			}
			
			//Check if an empty object is replacing something, important for the falling sand
			else if (pixels[row][col] != EMPTY && newType == EMPTY && row >= 0 && row < widthScale && col >= 0 && col < heightScale) {
				pixels[row][col] = newType;
				swapParticlesWithUpdate(row, col, newType);
				return true;
			}
			//Let mud replace water
			else if (pixels[row][col] == WATER && newType == MUD && row >= 0 && row < widthScale && col >= 0 && col < heightScale) {
				pixels[row][col] = newType;
				swapParticlesWithUpdate(row, col, newType);
				return true;
			}
			//Let water replace mud
			else if (pixels[row][col] == MUD && newType == WATER && row >= 0 && row < widthScale && col >= 0 && col < heightScale) {
				pixels[row][col] = newType;
				swapParticlesWithUpdate(row, col, newType);
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}

	public static boolean allowPlacement(int newType) {
		if (newType == SAND && amtSand > 0) {
			return true;
		}
		else if (newType == MUD && amtMud > 0) {
			return true;
		}
		else if (newType == FLOOR) {
			return true;
		}
		
		return false;
	}
	
	public static void collect() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if ((int) (DUDraw.mouseX() - 3 + i) >= 0 && (int) (DUDraw.mouseX() - 3 + i) < 300 && (int) (DUDraw.mouseY() - 3 + j) >= 0 && (int) (DUDraw.mouseY() - 3 + j) < 300) {
					if (pixels[(int) (DUDraw.mouseX() - 3 + i)][(int) (DUDraw.mouseY() - 3 + j)] != EMPTY) {
						if (pixels[(int) (DUDraw.mouseX() - 3 + i)][(int) (DUDraw.mouseY() - 3 + j)] == SAND) {
							SandWorld.placeParticle((int) DUDraw.mouseX()-3 + i, (int) DUDraw.mouseY()-3 + j, SandWorld.tool);
							amtSand++;
						}
						if (pixels[(int) (DUDraw.mouseX() - 3 + i)][(int) (DUDraw.mouseY() - 3 + j)] == MUD) {
							SandWorld.placeParticle((int) DUDraw.mouseX()-3 + i, (int) DUDraw.mouseY()-3 + j, SandWorld.tool);
							amtMud++;
						}
					}
				}
			}
		}
	}
	
	public void displayToolName(int newType) {
		
		//Detects which tool is being used, then updates the text in the corner
		if (newType == EMPTY) {
			DUDraw.setPenColor(DUDraw.WHITE);
			DUDraw.filledRectangle(50, 275, 50, 25);
			DUDraw.setPenColor(DUDraw.BLACK);
			DUDraw.text(50, 275, "COLLECT");
		}	
		else if (newType == SAND) {
			DUDraw.setPenColor(DUDraw.WHITE);
			DUDraw.filledRectangle(50, 275, 50, 25);
			DUDraw.setPenColor(DUDraw.BLACK);
			DUDraw.text(50, 275, "SAND");
		}
		else if (newType == FLOOR) {
			DUDraw.setPenColor(DUDraw.WHITE);
			DUDraw.filledRectangle(50, 275, 50, 25);
			DUDraw.setPenColor(DUDraw.BLACK);
			DUDraw.text(50, 275, "FLOOR");
		}
		else if (newType == WATER) {
			DUDraw.setPenColor(DUDraw.WHITE);
			DUDraw.filledRectangle(50, 275, 50, 25);
			DUDraw.setPenColor(DUDraw.BLACK);
			DUDraw.text(50, 275, "WATER");
		}
		else if (newType == MUD) {
			DUDraw.setPenColor(DUDraw.WHITE);
			DUDraw.filledRectangle(50, 275, 50, 25);
			DUDraw.setPenColor(DUDraw.BLACK);
			DUDraw.text(50, 275, "MUD");
		}
		else {
			DUDraw.setPenColor(DUDraw.WHITE);
			DUDraw.filledRectangle(50, 275, 50, 25);
			DUDraw.setPenColor(DUDraw.BLACK);
			DUDraw.text(50, 275, " ");
		}
		
		DUDraw.setPenColor(DUDraw.WHITE);
		DUDraw.filledRectangle(145, 285, 45, 15);
		DUDraw.setPenColor(DUDraw.BLACK);
		DUDraw.text(145, 285, "AMOUNT SAND:" + amtSand);
		
		DUDraw.setPenColor(DUDraw.WHITE);
		DUDraw.filledRectangle(235, 285, 45, 15);
		DUDraw.setPenColor(DUDraw.BLACK);
		DUDraw.text(235, 285, "AMOUNT MUD:" + amtMud);
		
	}
	
	public static void step() {
		
		//Nested For Loop to check for sand updates
		for (int row = 0; row < pixels.length; row++) {
			for (int col = 0; col < pixels[0].length; col++) {
				
				//Check if current position at row, column is sand
				if (pixels[row][col] == SAND) {
					//Make sure the column and row is not out of bounds before accessing array
					if (col-1 >= 0 && col-1 < widthScale && row >= 0 && row < heightScale) {
						//If below is empty, replace with sand
						if (pixels[row][col-1] == EMPTY) {
							placeParticle(row, col, EMPTY);
							placeParticle(row, col-1, SAND);
						}
						// If below is water, replace with mud
						if (pixels[row][col-1] == WATER) {
							placeParticle(row, col, EMPTY);
							placeParticle(row, col-1, MUD);
						}
						// If below is mud, make current position mud but i dont think this shit workss hahahahahha
						if (pixels[row][col-1] == MUD) {
							placeParticle(row, col, MUD);
							//placeParticle(row, col-1, MUD);
						}
					}
				}
				
				//Check for if the sand can move diagonally
				if (pixels[row][col] == SAND) {
					if (col-1 >= 0 && col-1 < widthScale && row-1 >= 0 && row-1 < heightScale) {
						if (pixels[row-1][col-1] == EMPTY ) {
							placeParticle(row, col, EMPTY);
							placeParticle(row-1, col-1, SAND);
						}
					}
				}
				
				if (pixels[row][col] == SAND) {
					if (col-1 >= 0 && col-1 < widthScale && row+1 >= 0 && row+1 < heightScale) {
						if (pixels[row+1][col-1] == EMPTY ) {
							placeParticle(row, col, EMPTY);
							placeParticle(row+1, col-1, SAND);
						}
					}
				}
				
				//Have mud sink through water
				if (pixels[row][col] == MUD) {
					if (col-1 >= 0 && col-1 < widthScale && row >= 0 && row < heightScale) {
						if (pixels[row][col-1] == WATER) {
							placeParticle(row, col, WATER);
							placeParticle(row, col-1, MUD);
						}
					}
				}
				
				// Have water fall
				if (pixels[row][col] == WATER) {
					if (col-1 >= 0 && col-1 < widthScale && row >= 0 && row < heightScale) {
						if (pixels[row][col-1] == EMPTY) {
							placeParticle(row, col, EMPTY);
							placeParticle(row, col-1, WATER);
						}
					}
				}
				
				// Have water move diagonally
				if (pixels[row][col] == WATER) {
					if (col-1 >= 0 && col-1 < widthScale && row-1 >= 0 && row-1 < heightScale) {
						if (pixels[row-1][col-1] == EMPTY ) {
							placeParticle(row, col, EMPTY);
							placeParticle(row-1, col-1, WATER);
						}
					}
				}
				
				if (pixels[row][col] == WATER) {
					if (col-1 >= 0 && col-1 < widthScale && row+1 >= 0 && row+1 < heightScale) {
						if (pixels[row+1][col-1] == EMPTY) {
							placeParticle(row, col, EMPTY);
							placeParticle(row+1, col-1, WATER);
						}
					}
				}
				
				// Have water move left or right
				if (pixels[row][col] == WATER) {
					if (col >= 0 && col < widthScale && row+1 >= 0 && row+1 < heightScale) {
						if (pixels[row+1][col] == EMPTY) {
							placeParticle(row, col, EMPTY);
							placeParticle(row+1, col, WATER);	
						}
					}
				}
				
				if (pixels[row][col] == WATER) {
					if (col >= 0 && col < widthScale && row-1 >= 0 && row-1 < heightScale) {
						if (pixels[row-1][col] == EMPTY ) {
							placeParticle(row, col, EMPTY);
							placeParticle(row-1, col, WATER);
						}
					}
				}
				
			}
		}
		
	}
	
	public static void swapParticlesWithUpdate(int row, int col, int newType) {
		
		//Check what particle color to use by checking newType
		if (newType == EMPTY) {
			DUDraw.setPenColor(DUDraw.BOOK_LIGHT_BLUE);
		}
		else if (newType == SAND) {
			DUDraw.setPenColor(DUDraw.YELLOW);
		}
		else if (newType == FLOOR) {
			DUDraw.setPenColor(DUDraw.BLACK);
		}
		else if (newType == WATER) {
			DUDraw.setPenColor(DUDraw.BLUE);
		}
		else if (newType == MUD) {
			DUDraw.setPenColor(DUDraw.PRINCETON_ORANGE);
		}
		
		//Put the particle at the row and column of pixels[][]
		DUDraw.filledSquare(row, col, 0.5);
		
	}
	
	public void clear() {
		
		for (int row = 0; row < pixels.length; row++) {
			for (int col = 0; col < pixels[0].length; col++) {
				placeParticle(row, col, EMPTY);
			}
		}
		
		amtSand = 10000;
		amtMud = 0;
				
	}
	
	public void clearFloor() {
		
			//Set all floors to be empty
			for (int row = 0; row < pixels.length; row++) {
				for (int col = 0; col < pixels[0].length; col++) {
					if (pixels[row][col] == FLOOR) {
						placeParticle(row, col, EMPTY);
					}
				}
			}
				
	}
	
	public static void draw() {
		//Draw the canvas when called
		DUDraw.pause(3);
		DUDraw.show();
	}
	
}
