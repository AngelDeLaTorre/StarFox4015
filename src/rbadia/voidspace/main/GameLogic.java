package rbadia.voidspace.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyBullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.Ship;
import rbadia.voidspace.sounds.SoundManager;


/**
 * Handles general game logic and status.
 */
public class GameLogic {

	private GameScreen gameScreen;
	private GameStatus status;
	private SoundManager soundMan;
	private Ship ship;
	private EnemyShip enemy;
	private Asteroid asteroid;
	private List<Bullet> bullets;
	private List<EnemyBullet> Ebullets;
	private ArrayList<Asteroid> asteroids;
	
	

	/**
	 * Craete a new game logic handler
	 * @param gameScreen the game screen
	 */
	public GameLogic(GameScreen gameScreen){
		this.gameScreen = gameScreen;

		// initialize game status information
		status = new GameStatus();
		// initialize the sound manager
		soundMan = new SoundManager();

		// init some variables
		bullets = new ArrayList<Bullet>();		
		
		Ebullets = new ArrayList<EnemyBullet>();
		asteroids = new ArrayList<Asteroid>();
	}

	/**
	 * Returns the game status
	 * @return the game status 
	 */
	public GameStatus getStatus() {
		return status;
	}

	public SoundManager getSoundMan() {
		return soundMan;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	/**
	 * Prepare for a new game.
	 */
	public void newGame(){
		status.setGameStarting(true);
		
		
		soundMan.playBackground();
		System.out.println("do a barrel roll");
		// init game variables
		bullets = new ArrayList<Bullet>();
		Ebullets = new ArrayList<EnemyBullet>();
		asteroids = new ArrayList<Asteroid>();
	
		
		status.setShipsLeft(3);
		status.setGameOver(false);
		status.setAsteroidsDestroyed(0);
		status.setNewAsteroid(false);

		// init the ship and the asteroid
		newShip(gameScreen);
		
		newEnemyShip(gameScreen);
		
		newAsteroid(gameScreen);

		// prepare game screen
		gameScreen.doNewGame();

		// delay to display "Get Ready" message for 1.5 seconds
		Timer timer = new Timer(1500, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameStarting(false);
				status.setGameStarted(true);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}

	/**
	 * Check game or level ending conditions.
	 */
	public void checkConditions(){
		// check game over conditions
		if(!status.isGameOver() && status.isGameStarted()){
			if(status.getShipsLeft() == 0){
				gameOver();
			}
		}
	}

	/**
	 * Actions to take when the game is over.
	 */
	public void gameOver(){
		soundMan.playGameOver1();
		System.out.println("Game over");
		status.setGameStarted(false);
		status.setGameOver(true);
		gameScreen.doGameOver();

		// delay to display "Game Over" message for 3 seconds
		Timer timer = new Timer(3000, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameOver(false);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	

	
	
	

	public void youWin(){
		soundMan.playFinalLevel();
		System.out.println("You Pass ICOM4015");
		status.setGameStarted(false);
		status.setWon(true);
		gameScreen.doWon();

		// delay to display "Game Over" message for 3 seconds
		Timer timer = new Timer(3000, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setWon(false);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}




	/**
	 * Fire a bullet from ship.
	 */
	public void fireBullet(){
		Bullet bullet = new Bullet(ship);
		bullets.add(bullet);


		soundMan.playBulletSound();
	}


	public void fireEnemyBullet(){
		EnemyBullet enemyBullet = new EnemyBullet(enemy);
		Ebullets.add(enemyBullet);
		soundMan.playBulletSound();
	}




	//creates a new asteroid
	public  void createAsteroid(){
		Asteroid asteroid = new Asteroid(gameScreen);

		asteroids.add(asteroid);
	

	}

	/**
	 * Move a bullet once fired.
	 * @param bullet the bullet to move
	 * @return if the bullet should be removed from screen
	 */
	public boolean moveBullet(Bullet bullet){
		if(bullet.getY() - bullet.getSpeed() >= 0){
			bullet.translate(0, -bullet.getSpeed());
			return false;
		}
		else{
			return true;
		}
	}

	public boolean moveEnemyBullet(EnemyBullet enemyBullet){
		if(enemyBullet.getY() +enemyBullet.getSpeed() <= gameScreen.getWidth()){
			enemyBullet.translate(0, enemyBullet.getSpeed());
			return false;
		}
		else{
			return true;
		}
	}


	// move asteroid in screen
	public boolean moveAsteroid(Asteroid asteroid){
		if(asteroid.getY() + asteroid.getSpeed() <= gameScreen.getHeight()){		
			asteroid.translate(asteroid.getAsteroidMovement() , asteroid.getSpeed());
			return false;
		}
		else{
			this.createAsteroid();
			return true;
		}
	}

	
	
	


	public void moveEnemy(EnemyShip enemy)
	{
		int speed = enemy.getEnemySpeed();

		if(speed > 0)
		{
			enemy.translate(speed, 0);		
		}
		if(speed < 0)
		{
			enemy.translate(speed , 0);		
		}
		if((enemy.getX() + enemy.getWidth()+GraphicsManager.eShipImg.getWidth() == gameScreen.getWidth()) || (enemy.getX() + enemy.getWidth() - 20-GraphicsManager.eShipImg.getWidth() == 0))
		{
			enemy.setEnemySpeed(speed *= -1);
			enemy.translate(speed, 0);
		}
	}

	/**
	 * Create a new ship (and replace current one).
	 */
	public Ship newShip(GameScreen screen){
		this.ship = new Ship(screen);
		return ship;
	}


	public EnemyShip newEnemyShip(GameScreen screen){
		this.enemy = new EnemyShip(screen);
		return enemy;
	}


	/**
	 * Create a new asteroid.
	 */
	public Asteroid newAsteroid(GameScreen screen){
		this.asteroid = new Asteroid(screen);

		this.createAsteroid();
		this.createAsteroid();
		this.createAsteroid();

		return asteroid;
	}
	
	
	

	/**
	 * Returns the ship.
	 * @return the ship
	 */
	public Ship getShip() {
		return ship;
	}

	public EnemyShip getEnemyShip() {
		return enemy;
	}

	/**
	 * Returns the asteroid.
	 * @return the asteroid
	 */
	public Asteroid getAsteroid() {
		return asteroid;
	}

	
	
	public ArrayList<Asteroid> getAsteroids(){
		return asteroids;
	}

	/**
	 * Returns the list of bullets.
	 * @return the list of bullets
	 */
	public List<Bullet> getBullets() {
		return bullets;
	}


	public List<EnemyBullet> getEnemyBullets() {
		// TODO Auto-generated method stub
		return Ebullets;
	}



}
