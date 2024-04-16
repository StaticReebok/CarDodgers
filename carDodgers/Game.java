/* Game.java
 * Space Invaders Main Program
 *
 */

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.net.URL;

public class Game extends Canvas {
	Font arial_40 = new Font("Arial", Font.PLAIN, 40);
	private static byte gameState = 0;
	
	private BufferStrategy strategy; // take advantage of accelerated graphics
	private boolean waitingForKeyPress = true; // true if game held up until a key is pressed
	private boolean leftPressed = false; // true if left arrow key currently pressed
	private boolean rightPressed = false; // true if right arrow key currently pressed
	private boolean upPressed = false; 
	private boolean downPressed = false;
	private boolean shiftPressed = false;
	private boolean cPressed = false;
	private Image road;
	private Image win;
	private Image death;
	private Image welcome;
	private Image instructions;
	private int score = 0;
	
	private boolean gameRunning = true;
	private ArrayList<Entity> entities = new ArrayList<Entity>(); // list of entities
													// in game
	private ArrayList<Entity> removeEntities = new ArrayList<Entity>(); // list of entities to remove this loop
	private Entity robber; // the robber
	private double moveSpeed = 600; // hor. vel. of character (px/s)
	private int carCount; // # of cars left on screen

	private boolean logicRequiredThisLoop = false; // true if logic needs to be applied this loop
	
	// Construct our game and set it running.
	public Game() {
		
		// initialize background
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		URL roadImage = Game.class.getResource("background/background.gif");
		URL winImage = Game.class.getResource("background/win.gif");
		URL deathImage = Game.class.getResource("background/gameover.gif");
		URL welcomeImage = Game.class.getResource("background/welcome.gif");
		URL instructionsImage = Game.class.getResource("background/instructions.gif");
		road = tk.getImage(roadImage);
		win = tk.getImage(winImage);
		death = tk.getImage(deathImage);
		welcome = tk.getImage(welcomeImage);
		instructions = tk.getImage(instructionsImage);
		
		// create a frame to contain game
		JFrame container = new JFrame("Car Dodgers");

		// get hold the content of the frame
		JPanel panel = (JPanel) container.getContentPane();

		// set up the resolution of the game
		panel.setPreferredSize(new Dimension(1000, 1000));
		panel.setLayout(null);

		// set up canvas size (this) and add to frame
		setBounds(0, 0, 1000, 1000);
		panel.add(this);

		// Tell AWT not to bother repainting canvas since that will
		// be done using graphics acceleration
		setIgnoreRepaint(true);

		// make the window visible
		container.pack();
		container.setResizable(false);
		container.setVisible(true);

		// if user closes window, shutdown game and jre
		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			} // windowClosing
		});

		// add key listener to this canvas
		addKeyListener(new KeyInputHandler());

		// request focus so key events are handled by this canvas
		requestFocus();

		// create buffer strategy to take advantage of accelerated graphics
		createBufferStrategy(2);
		strategy = getBufferStrategy();

		// initialize entities
		initEntities();

		// start the game
		gameLoop();
	} // constructor

	 // initEntities input: none output: none purpose: Initialise the starting state
	 // of the ship and car entities. Each entity will be added to the array of
	 // entities in the game.
	private void initEntities() {
		int randomEntitiy;
		int randomYPosition;
		int randomXPosition;
		
		// create the ship and put in center of screen
		robber = new robberEntity(this, "person/sprite.png", 455, 20);
		entities.add(robber);

		// create a block of cars (4 rows of cars)
		carCount = 0;
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 5; col++) {
				randomEntitiy = (int) (Math.random() * (10 - 1) + 1);
				randomYPosition = (int) (Math.random() * (10000 - 1000) + 1000);
				randomXPosition = (int) (Math.random() * (5 - 1) + 1);
				Entity car = new carEntity(this, "cars/Mini_van.gif", 160, 40);
				
				if(randomXPosition == 1) {
					switch (randomEntitiy) {
					  case 1:
						car = new carEntity(this, "cars/Ambulance.gif", 220, randomYPosition);
					    break;
					  case 2:
						car = new carEntity(this, "cars/Audi.gif", 220, randomYPosition);
					    break;
					  case 3:
						car = new carEntity(this, "cars/Black_viper.gif", 220, randomYPosition);
					    break;
					  case 4:
						car = new carEntity(this, "cars/Mini_van.gif", 220, randomYPosition);
					    break;
					  case 5:
						car = new carEntity(this, "cars/Mini_truck.gif", 220, randomYPosition);
					    break;
					  case 6:
						car = new carEntity(this, "cars/Car.gif", 220, randomYPosition);
					    break;
					  case 7:
						  car = new carEntity(this, "cars/taxi.gif", 220, randomYPosition);
					  case 8:
						  car = new carEntity(this, "cars/truck.gif", 220, randomYPosition);
					  default:
						  car = new roadblockEntity(this, "cars/roadblock.gif", 220, randomYPosition);
					} // switch
				} else if(randomXPosition == 2) {
					switch (randomEntitiy) {
					  case 1:
						car = new carEntity(this, "cars/Ambulance.gif", 375, randomYPosition);
					    break;
					  case 2:
						car = new carEntity(this, "cars/Audi.gif", 375, randomYPosition);
					    break;
					  case 3:
						car = new carEntity(this, "cars/Black_viper.gif", 375, randomYPosition);
					    break;
					  case 4:
						car = new carEntity(this, "cars/Mini_van.gif", 375, randomYPosition);
					    break;
					  case 5:
						car = new carEntity(this, "cars/Mini_truck.gif", 375, randomYPosition);
					    break;
					  case 6:
						car = new carEntity(this, "cars/Car.gif", 375, randomYPosition);
					    break;
					  case 7:
						  car = new carEntity(this, "cars/taxi.gif", 375, randomYPosition);
					  default:
						  car = new carEntity(this, "cars/truck.gif", 375, randomYPosition);
					} // switch
				} else if(randomXPosition == 3) {
					switch (randomEntitiy) {
					  case 1:
						car = new carEntity(this, "cars/Ambulance.gif", 520, randomYPosition);
					    break;
					  case 2:
						car = new carEntity(this, "cars/Audi.gif", 520, randomYPosition);
					    break;
					  case 3:
						car = new carEntity(this, "cars/Black_viper.gif", 520, randomYPosition);
					    break;
					  case 4:
						car = new carEntity(this, "cars/Mini_van.gif", 520, randomYPosition);
					    break;
					  case 5:
						car = new carEntity(this, "cars/Mini_truck.gif", 520, randomYPosition);
					    break;
					  case 6:
						car = new carEntity(this, "cars/Car.gif", 520, randomYPosition);
					    break;
					  case 7:
						  car = new carEntity(this, "cars/taxi.gif", 520, randomYPosition);
					  default:
						  car = new carEntity(this, "cars/truck.gif", 520, randomYPosition);
					} // switch
				} else if(randomXPosition == 4) {
					switch (randomEntitiy) {
					  case 1:
						car = new carEntity(this, "cars/Ambulance.gif", 680, randomYPosition);
					    break;
					  case 2:
						car = new carEntity(this, "cars/Audi.gif", 680, randomYPosition);
					    break;
					  case 3:
						car = new carEntity(this, "cars/Black_viper.gif", 680, randomYPosition);
					    break;
					  case 4:
						car = new carEntity(this, "cars/Mini_van.gif", 680, randomYPosition);
					    break;
					  case 5:
						car = new carEntity(this, "cars/Mini_truck.gif", 680, randomYPosition);
					    break;
					  case 6:
						car = new carEntity(this, "cars/Car.gif", 680, randomYPosition);
					    break;
					  case 7:
						  car = new carEntity(this, "cars/taxi.gif", 680, randomYPosition);
					  default:
						  car = new carEntity(this, "cars/truck.gif", 680, randomYPosition);
					} // switch
				} // else if
				
				entities.add(car);
				carCount++;
			} // for
		} // outer for
		
		// prevents car stacking on spawn
		for(int o = 0; o < 3; o++) {
			for(int i = 0; i < entities.size(); i++) {
				for(int j = 0; j < entities.size(); j++) {
					if(entities.get(i).y != entities.get(j).y && entities.get(i).x == entities.get(j).x && (int)entities.get(i).y < (int)entities.get(j).y && (int)(entities.get(i).y + 250) > (int)entities.get(j).y) {
						entities.get(j).y += 500;
					} // if
				} // inner for
			} // middle for
		} // outer for
	} // initEntities
	
	// Notification from a game entity that the logic of the game should be run at the next opportunity
	public void updateLogic() {
		logicRequiredThisLoop = true;
	} // updateLogic

	// Remove an entity from the game. It will no longer be moved or drawn.
	public void removeEntity(Entity entity) {
		removeEntities.add(entity);
	} // removeEntity
	
	// Notification that the player has died.
	public void notifyDeath() {
		score = 0;
		gameState = 4;
		waitingForKeyPress = true;
	} // notifyDeath
	
	// Notification that the play has killed all cars
	public void notifyWin() {
		score = 0;
		gameState = 3;
		waitingForKeyPress = true;
	} // notifyWin
	
	
	// Notification that a car has been killed
	public void notifyCarKilled() {
		carCount--;
		score += 50;
		
		if (carCount == 0) {
			notifyWin();
		} // if

		// speed up existing cars
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = (Entity) entities.get(i);
			if (entity instanceof carEntity) {
				// speed up by 2%
				entity.setHorizontalMovement(entity.getHorizontalMovement() * 1.04);	
			} // if
		} // for
	} // notifyCarKilled

	// gameLoop input: none output: none purpose: Main game loop. Runs throughout
	// game play. Responsible for the following activities: - calculates speed of
	// the game loop to update moves - moves the game entities - draws the screen
	// contents (entities, text) - updates game events - checks input
	public void gameLoop() {
		long lastLoopTime = System.currentTimeMillis();
		gameState = 0;
		
		// keep loop running until game ends
		while (gameRunning) {

			// calc. time since last update, will be used to calculate entities movement
			long delta = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			
			if(gameState == 0) {
				g.drawImage(welcome, 0, 0, 1000, 1000, null);
			} // else if
			
			else if(gameState == 1) {
				g.drawImage(instructions, 0, 0, 1000, 1000, null);
			} // else if
			
			else if(gameState == 2) {
				g.setColor(Color.white);
				g.drawImage(road, 0, 0, 1000, 1000, null);
				g.setFont(arial_40);
				g.drawString("Score: " + score, 10 , 30);
				
				// move each entity
				if (!waitingForKeyPress) {
					for (int i = 0; i < entities.size(); i++) {
						Entity entity = (Entity) entities.get(i);
						entity.move(delta);
					} // for
				} // if

				// draw all entities
				for (int i = 0; i < entities.size(); i++) {
					Entity entity = (Entity) entities.get(i);
					entity.draw(g);
				} // for
			} // else if
			
			else if(gameState == 3) {
				g.drawImage(win, 0, 0, 1000, 1000, null);
			} // else if
			
			if(gameState == 4) {
				g.drawImage(death, 0, 0, 1000, 1000, null);
			} // else if
			
			// brute force collisions, compare every entity
			// against every other entity. If any collisions
			// are detected notify both entities that it has occurred
			for (int i = 0; i < entities.size(); i++) {
				for (int j = i + 1; j < entities.size(); j++) {
					Entity me = (Entity) entities.get(i);
					Entity him = (Entity) entities.get(j);
					if (cPressed) {
						if (him instanceof carEntity) {
							if (me.collidesWith(him)) {
								me.collidedWith(him);
								him.collidedWith(me);
							} // if	
						} // if
					} else {
						if (him instanceof roadblockEntity ||  him instanceof carEntity) {
							if (me.collidesWith(him)) {
								me.collidedWith(him);
								him.collidedWith(me);
							} // if	
						} // if
					} // else
				} // inner for
			} // outer for

			// remove dead entities
			entities.removeAll(removeEntities);
			removeEntities.clear();

			// run logic if required
			if (logicRequiredThisLoop) {
				for (int i = 0; i < entities.size(); i++) {
					Entity entity = (Entity) entities.get(i);
					entity.doLogic();
				} // for
				logicRequiredThisLoop = false;
			} // if
			
			// clear graphics and flip buffer
			g.dispose();
			strategy.show();

			// ship should not move without user input
			robber.setHorizontalMovement(0);
			robber.setVerticalMovement(0);
			
			// respond to user moving ship
			if ((leftPressed) && (!rightPressed) && (!upPressed) && (!downPressed)) {
				if(cPressed) {
					shiftPressed = false;
					robber.setHorizontalMovement(-moveSpeed * 0.5);
				} else if(shiftPressed) {
					cPressed = false;
					robber.setHorizontalMovement(-moveSpeed * 2);
				} else {
					robber.setHorizontalMovement(-moveSpeed);
				} // else
			} else if ((rightPressed) && (!leftPressed) && (!upPressed) && (!downPressed)) {
				if(cPressed) {
					shiftPressed = false;
					robber.setHorizontalMovement(moveSpeed * 0.5);
				} else if(shiftPressed) {
					cPressed = false;
					robber.setHorizontalMovement(moveSpeed * 2);
				} else {
					robber.setHorizontalMovement(moveSpeed);
				} // else
			} else if ((upPressed) && (!leftPressed) && (!rightPressed) && (!downPressed)) {
				if(cPressed) {
					shiftPressed = false;
					robber.setVerticalMovement(-moveSpeed * 0.5);
				} else if(shiftPressed) {
					cPressed = false;
					robber.setVerticalMovement(-moveSpeed * 2);
				} else {
					robber.setVerticalMovement(-moveSpeed);
				} // else
			} else if ((downPressed) && (!leftPressed) && (!rightPressed) && (!upPressed)) {
				if(cPressed) {
					shiftPressed = false;
					robber.setVerticalMovement(moveSpeed * 0.5);
				} else if(shiftPressed) {
					cPressed = false;
					robber.setVerticalMovement(moveSpeed * 2);
				} else {
					robber.setVerticalMovement(moveSpeed);
				} // else
			} else if ((rightPressed) && (upPressed) && (!leftPressed) && (!downPressed)) {
				if(cPressed) {
					shiftPressed = false;
					robber.setHorizontalMovement(moveSpeed * 0.5);
					robber.setVerticalMovement(-moveSpeed * 0.5);
				} else if(shiftPressed) {
					cPressed = false;
					robber.setHorizontalMovement(moveSpeed * 2);
					robber.setVerticalMovement(-moveSpeed * 2);
				}else {
					robber.setHorizontalMovement(moveSpeed);
					robber.setVerticalMovement(-moveSpeed);
				} // else
			} else if ((leftPressed) && (upPressed) && (!rightPressed) && (!downPressed)) {
				if(cPressed) {
					shiftPressed = false;
					robber.setHorizontalMovement(-moveSpeed * 0.5);
					robber.setVerticalMovement(-moveSpeed * 0.5);
				} else if(shiftPressed) {
					cPressed = false;
					robber.setHorizontalMovement(-moveSpeed * 2);
					robber.setVerticalMovement(-moveSpeed * 2);
				}else {
					robber.setHorizontalMovement(-moveSpeed);
					robber.setVerticalMovement(-moveSpeed);
				} // else
			} else if ((rightPressed) && (downPressed) && (!leftPressed) && (!upPressed)) {
				if(cPressed) {
					shiftPressed = false;
					robber.setHorizontalMovement(moveSpeed * 0.5);
					robber.setVerticalMovement(moveSpeed * 0.5);
				} else if(shiftPressed) {
					cPressed = false;
					robber.setHorizontalMovement(moveSpeed * 2);
					robber.setVerticalMovement(moveSpeed * 2);
				}else {
					robber.setHorizontalMovement(moveSpeed);
					robber.setVerticalMovement(moveSpeed);
				} // else
			} else if ((leftPressed) && (downPressed) && (!rightPressed) && (!upPressed)) {
				if(cPressed) {
					shiftPressed = false;
					robber.setHorizontalMovement(-moveSpeed * 0.5);
					robber.setVerticalMovement(moveSpeed * 0.5);
				} else if(shiftPressed) {
					cPressed = false;
					robber.setHorizontalMovement(-moveSpeed * 2);
					robber.setVerticalMovement(moveSpeed * 2);
				} else {
					robber.setHorizontalMovement(-moveSpeed);
					robber.setVerticalMovement(moveSpeed);
				} // else
			}//else
		} // while
	} // gameLoop
	
	 // startGame input: none output: none purpose: start a fresh game, clear old data
	private void startGame() {
		gameState = 2;
		
		// clear out any existing entities and initalize a new set
		entities.clear();

		initEntities();

		// blank out any keyboard settings that might exist
		leftPressed = false;
		rightPressed = false;
		upPressed = false;
		downPressed = false;
		cPressed = false;
		shiftPressed = false;
	} // startGame
	
	// inner class KeyInputHandler handles keyboard input from the user
	private class KeyInputHandler extends KeyAdapter {
	    private int counter = 1;
		private int pressCount = 0; // the number of key presses since waiting for 'any' key press
		
		// The following methods are required for any class that extends the abstract
		// class KeyAdapter. They handle keyPressed, keyReleased and keyTyped events.
		public void keyPressed(KeyEvent e) {
			// if waiting for keypress to start game, do nothing
			if (waitingForKeyPress) {
				return;
			} // if

			// respond to move left, right or fire
			if (e.getKeyCode() == KeyEvent.VK_A) {
				leftPressed = true;
			} // if

			if (e.getKeyCode() == KeyEvent.VK_D) {
				rightPressed = true;
			} // if
			
			if (e.getKeyCode() == KeyEvent.VK_W) {
				upPressed = true;
			} // if
			
			if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
				shiftPressed = true;
			} // if
			
			if (e.getKeyCode() == KeyEvent.VK_C) {
				cPressed = true;
			} // if
			
			if (e.getKeyCode() == KeyEvent.VK_S) {
				downPressed = true;
			} // if
		} // keyPressed

		public void keyReleased(KeyEvent e) {
			// if waiting for keypress to start game, do nothing
			if (waitingForKeyPress) {
				return;
			} // if

			// respond to move left, right or fire
			if (e.getKeyCode() == KeyEvent.VK_A) {
				leftPressed = false;
			} // if

			if (e.getKeyCode() == KeyEvent.VK_D) {
				rightPressed = false;
			} // if

			if (e.getKeyCode() == KeyEvent.VK_W) {
				upPressed = false;
			} // if
			
			if (e.getKeyCode() == KeyEvent.VK_S) {
				downPressed = false;
			} // if
			
			if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
				shiftPressed = false;
			} // if
			
			if (e.getKeyCode() == KeyEvent.VK_C) {
				cPressed = false;
			} // if
		} // keyReleased

		public void keyTyped(KeyEvent e) {
			// if waiting for key press to start game
			if (waitingForKeyPress) {
				if (pressCount == 0 && counter == 1) {
					Game.gameState = 1;
		        }
				if (pressCount == 1) {
					waitingForKeyPress = false;
					startGame();
					pressCount = 0;
					counter = 0;
				} else {
					pressCount++;
				} // else
			} // if waitingForKeyPress

			// if escape is pressed, end game
			if (e.getKeyChar() == 27) {
				System.exit(0);
			} // if escape pressed
		} // keyTyped method
	} // class KeyInputHandler

	/** Main Program */
	public static void main(String[] args) {
		// instantiate this object
		new Game();
	} // main
} // Game