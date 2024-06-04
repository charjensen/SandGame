import edu.du.dudraw.*;

/*
 * Author: Charles Jensen
 * Date: 1/13/2022
 * Purpose: Main Driver class for the sand game, handles all user interaction
 */

public class SandGame {

	public static void main(String[] args) {
		
		//Create new SandGame object
		SandWorld theGame = new SandWorld();
		
		// Animation loop while SandWorld object is not null
		while (theGame != null) {
			
			//Change the type of object the user is using when they press a key
			if (DUDraw.hasNextKeyTyped()) {
				char nextKeyPressed = DUDraw.nextKeyTyped();
				if (nextKeyPressed == 's') {
					SandWorld.tool = SandWorld.SAND;
				}
				else if (nextKeyPressed == 'f') {
					SandWorld.tool = SandWorld.FLOOR;
				}
				else if (nextKeyPressed == 'e') {
					SandWorld.tool = SandWorld.EMPTY;
				}
				else if (nextKeyPressed == 'w') {
					SandWorld.tool = SandWorld.WATER;
				}
				else if (nextKeyPressed == 'm') {
					SandWorld.tool = SandWorld.MUD;
				}
				else if (nextKeyPressed == 'c') {
					theGame.clear();
				}
				else if (nextKeyPressed == 'a') {
					theGame.clearFloor();
				}
			}
			
			//Check for mouse press and place either sand or floor when the conditions are met
			
			int x = (int) DUDraw.mouseX();
			int y = (int) DUDraw.mouseY();
			
			if (DUDraw.isMousePressed()) {	
				if (SandWorld.tool == SandWorld.SAND && SandWorld.allowPlacement(SandWorld.SAND)) {
					for (int i = 0; i < 6; i++) {
						if (SandWorld.placeParticle(x-5 + (int) (Math.random()*10), y, SandWorld.tool)) {
							SandWorld.amtSand--;
						}
					}
				}
				else if (SandWorld.tool == SandWorld.FLOOR) {
					for (int i = 0; i < 6; i++) {
						for (int j = 0; j < 3; j++) {
							SandWorld.placeParticle((int) DUDraw.mouseX() + i, (int) DUDraw.mouseY() + j, SandWorld.tool);
						}
					}
				}
				else if (SandWorld.tool == SandWorld.EMPTY) {
					
					SandWorld.collect();
					
//					for (int i = 0; i < 6; i++) {
//						for (int j = 0; j < 6; j++) {
//							SandWorld.placeParticle((int) DUDraw.mouseX()-3 + i, (int) DUDraw.mouseY()-3 + j, SandWorld.tool);
//						}
//					}
				}
				else if (SandWorld.tool == SandWorld.WATER) {
					SandWorld.placeParticle((int) DUDraw.mouseX(), (int) DUDraw.mouseY(), SandWorld.tool);
				}
				else if (SandWorld.tool == SandWorld.MUD && SandWorld.allowPlacement(SandWorld.MUD)) {
					for (int i = 0; i < 6; i++) {
						for (int j = 0; j < 6; j++) {
							if (SandWorld.placeParticle(x + i, y + j, SandWorld.tool)) {
								SandWorld.amtMud--;
							}
						}
					}
				}
			}
			
			// Update tool name, world, and then draw to canvas
			theGame.displayToolName(SandWorld.tool);
			SandWorld.step();
			SandWorld.draw();
			
		}
		
	}

}
