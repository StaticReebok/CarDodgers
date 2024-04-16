/* robberEntity.java
 * March 27, 2006
 * Represents player's robber
 */
public class robberEntity extends Entity {

	private Game game; // the game in which the robber exists

	/*
	 * construct the player's robber input: game - the game in which the robber is being
	 * created ref - a string with the name of the image associated to the sprite
	 * for the robber x, y - initial location of robber
	 */
	public robberEntity(Game g, String r, int newX, int newY) {
		super(r, newX, newY); // calls the constructor in Entity
		game = g;
	} // constructor

	/*
	 * move input: delta - time elapsed since last move (ms) purpose: move robber
	 */
	public void move(long delta) {
		// stop at left side of screen
		if ((dx < 0) && (x < 170)) {
			return;
		} // if
			// stop at right side of screen
		if ((dx > 0) && (x > 705)) {
			return;
		} // if
		
		if ((dy < 0) && (y < 0)) {
			return;
		} // if
		
		if ((dy > 0) && (y > 900)) {
			return;
		} // if
		
		super.move(delta); // calls the move method in Entity
	} // move

	/*
	 * collidedWith input: other - the entity with which the robber has collided
	 * purpose: notification that the robber has collided with something
	 */
	public void collidedWith(Entity other) {
		if (other instanceof carEntity || other instanceof roadblockEntity) {
			game.notifyDeath();
		} // if
	} // collidedWith

} // robberEntity class