package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.main.GameScreen;

public class Asteroid extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 4;
	
	private int asteroidWidth1 = 32;
	private int asteroidHeight1 = 32;
	private int asteroidWidth2 = 42;
	private int asteroidHeight2 = 42;
	private int speed = DEFAULT_SPEED;
	private int asteroidMovement = 5;
	private boolean isBig;
	private int positionX;
	private int positionY;

	private Random rand = new Random();
	
	/**
	 * Crates a new asteroid at a random x location at the top of the screen 
	 * @param screen the game screen
	 */
	public Asteroid(GameScreen screen){
		//randomize big and small asteroids
		int numero =rand.nextInt(2);
		
		if (numero == 1){
			this.setLocation(rand.nextInt(screen.getWidth() - this.getAsteroidWidth()), 0);
			//this.positionX = rand.nextInt(screen.getWidth() - this.getAsteroidWidth());
			//this.positionY = 0;
			this.isBig = true;
			//this.setLocation(x, y);
			this.setSize(this.getAsteroidWidth(), this.getAsteroidWidth());
			this.setAsteroidMovement(rand.nextInt(10)-5);
			
			
			
			
		}else{
			this.isBig = false;
			this.setLocation(rand.nextInt(screen.getWidth() - this.getAsteroidWidth()), 0);
			this.setSize(this.getAsteroidWidth(), this.getAsteroidHeight());
			this.setAsteroidMovement(rand.nextInt(10)-5);
			
		}
		
	}
	public int getBigAsteroidX(){
		return this.positionX;
	}
	
	public int getBigAsteroidY(){
		return this.positionY;
	}

	public int getAsteroidWidth() {
		if(this.getBig()){
			asteroidWidth2 = GraphicsManager.getBigAsteroidImg().getWidth();
			
			return asteroidWidth2;
		}else{
			asteroidWidth1 = GraphicsManager.getSmallAsteroidImg().getWidth();
			return asteroidWidth1;
		}
		
	}
	public int getAsteroidHeight() {
		if(this.getBig()){
			asteroidHeight2 = GraphicsManager.getBigAsteroidImg().getHeight();
			return asteroidHeight2;
		}else{
			asteroidHeight1 = GraphicsManager.getSmallAsteroidImg().getHeight();
			return asteroidHeight1;
		}
	}
	public void setAsteroidMovement(int asteroidMovement ){		
		this.asteroidMovement = asteroidMovement;
	}

	/**
	 * Returns the current asteroid speed
	 * @return the current asteroid speed
	 */
	public int getSpeed() {
	
		return speed;
	}
	
	/**
	 * Set the current asteroid speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/**
	 * Returns the default asteroid speed.
	 * @return the default asteroid speed
	 */
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
	public int getAsteroidMovement(){
		return asteroidMovement;
	}
	
	public boolean getBig(){
		return this.isBig;
		
	}

}