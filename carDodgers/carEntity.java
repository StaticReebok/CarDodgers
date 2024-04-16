/* carEntity.java
 * March 27, 2006
 * Represents one of the cars
 */
public class carEntity extends Entity {

	private double moveSpeed = -500; // speed

	private Game game; // the game in which the car exists
	
	// construct a new alien input: game - the game in which the alien is being
	// created // r - the image representing the alien // x, y - initial location of alien
	public carEntity(Game g, String r, int newX, int newY) {
		super(r, newX, newY); // calls the constructor in Entity
		game = g;
		
		if(newX == 220) {
			moveSpeed = -450;
		}
		
		dy = moveSpeed; // start off moving left
	} // constructor


	// move input: delta - time elapsed since last move (ms) purpose: move alien
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
	
	 // doLogic Updates the game logic related to the aliens, ie. move it down the screen 
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
	
	// collidedWith input: other - the entity with which the alien has collided
	// purpose: notification that the alien has collided with something
	public void collidedWith(Entity other) {
		// collisions with cars are handled in roadblockEntity and ShipEntity
	} // collidedWith

} // carEntity class