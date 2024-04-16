/* roadblockEntity.java
 * March 27, 2006
 * Represents one of the road blocks
 */
public class roadblockEntity extends Entity {

	private double moveSpeed = -450; // speed

	private Game game; // the game in which the road blocks exists
	
	// construct a new road blocks input: game - the game in which the road blocks is being
	// created // r - the image representing the car // x, y - initial location of road blocks
	public roadblockEntity(Game g, String r, int newX, int newY) {
		super(r, newX, newY); // calls the constructor in Entity
		game = g;
		dy = moveSpeed; // start off moving left
	} // constructor
	
	// move input: delta - time elapsed since last move (ms) purpose: move road blocks
	public void move(long delta) {
		// if we reach left side of screen and are moving left request logic update
	    if ((dx < 0) && (x < 1000)) {
	      game.updateLogic();   // logic deals with moving entities in other direction and down screen
	    } // if

	    // if we reach right side of screen and are moving right
	    // request logic update
	    if ((dy < 0) && (y < -200)) {
	      game.updateLogic();
	    } // if
		
		// proceed with normal move
		super.move(delta);
	} // move
	
	 // doLogic Updates the game logic related to the road blocks, ie. move it down the screen 
	 //and change direction
	public void doLogic() {
		// swap horizontal direction and move down screen 10 pixels
		dx *= -1;
		y -= 10;

		// if top of screen reached, player dies
		if (y < -200) {
			game.removeEntity(this);
			game.notifyCarKilled();
		} // if
	} // doLogic
	
	// collidedWith input: other - the entity with which the road blocks has collided
	// purpose: notification that the road blocks has collided with something
	public void collidedWith(Entity other) {
		// collisions with cars are handled in roadblockEntity and ShipEntity
	} // collidedWith

} // roadblockEntity class