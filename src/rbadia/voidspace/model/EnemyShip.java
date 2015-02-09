package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.main.GameScreen;

/**
 * Represents a ship/space craft.
 *
 */
public class EnemyShip extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_ENEMY_SPEED = 2;
	//private static final int Y_OFFSET = 5; // initial y distance of the ship from the bottom of the screen 
	private Random rand = new Random();

	private int enemyShipWidth = GraphicsManager.eShipImg.getWidth();
	private int enemyShipHeight = GraphicsManager.eShipImg.getHeight();
	private int enemySpeed = DEFAULT_ENEMY_SPEED;
	private int enemyMovement = 5;
	
	/**
	 * Creates a new ship at the default initial location. 
	 * @param screen the game screen
	 */
	public EnemyShip(GameScreen screen){
		this.setLocation((screen.getWidth() - enemyShipWidth)/2,0 + enemyShipHeight);
		this.setSize(enemyShipWidth, enemyShipHeight);
		this.setEnemyMovement(rand.nextInt(10)-5);
	}
	
	
	
	
	
	
	
	/**
	 * Get the default ship width
	 * @return the default ship width
	 */
	public int getEnemyShipWidth() {
		return enemyShipWidth;
	}
	
	/**
	 * Get the default ship height
	 * @return the default ship height
	 */
	public int getEnemyShipHeight() {
		return enemyShipHeight;
	}
	
	/**
	 * Returns the current ship speed
	 * @return the current ship speed
	 */
	public int getEnemySpeed() {
		return enemySpeed;
	}
	
	/**
	 * Set the current ship speed
	 * @param speed the speed to set
	 */
	public void setEnemySpeed(int enemySpeed) {
		this.enemySpeed = enemySpeed;
	}
	
	/**
	 * Returns the default ship speed.
	 * @return the default ship speed
	 */
	public int getDefaultEnemySpeed(){
		return DEFAULT_ENEMY_SPEED;
	}
	
	public void setEnemyMovement(int enemyMovement ){		
		this.enemyMovement = enemyMovement;
	}







	public int getEnemyMovement() {
		return enemyMovement;
	}
}
