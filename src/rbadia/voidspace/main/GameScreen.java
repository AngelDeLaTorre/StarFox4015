package rbadia.voidspace.main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyBullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.Ship;
import rbadia.voidspace.sounds.SoundManager;

/**
 * Main game screen. Handles all game graphics updates and some of the game logic.
 */
public class GameScreen extends JPanel {
	private static final long serialVersionUID = 1L;

	private BufferedImage backBuffer;
	private Graphics2D g2d;

	private static final int NEW_SHIP_DELAY = 500;
	private static final int NEW_ASTEROID_DELAY = 500;

	private long lastShipTime;
	private long lastAsteroidTime;

	private Rectangle asteroidExplosion;
	private Rectangle shipExplosion;
	private Rectangle enemyShipExplosion;


	private JLabel shipsValueLabel;
	private JLabel destroyedValueLabel;

	private Random rand;

	private Font originalFont;
	private Font bigFont;
	private Font biggestFont;

	private GameStatus status;
	private SoundManager soundMan;
	private GraphicsManager graphicsMan;
	private GameLogic gameLogic;
	private static int shieldCounter =0;


	/**
	 * This method initializes 
	 * 
	 */
	public GameScreen() {
		super();
		// initialize random number generator
		rand = new Random();

		initialize();

		// init graphics manager
		graphicsMan = new GraphicsManager();

		// init back buffer image
		backBuffer = new BufferedImage(500, 400, BufferedImage.TYPE_INT_RGB);
		g2d = backBuffer.createGraphics();
	}

	/**
	 * Initialization method (for VE compatibility).
	 */
	private void initialize() {
		// set panel properties
		this.setSize(new Dimension(500, 400));
		this.setPreferredSize(new Dimension(500, 400));
		this.setBackground(Color.BLACK);

	}

	/**
	 * Update the game screen.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// draw current backbuffer to the actual game screen
		g.drawImage(backBuffer, 0, 0, this);
	}

	/**
	 * Update the game screen's backbuffer image.
	 */
	public void updateScreen(){


		Ship ship = gameLogic.getShip();
		EnemyShip enemy = gameLogic.getEnemyShip();
		//Asteroid asteroid = gameLogic.getAsteroid();
		ArrayList<Asteroid> asteroids = gameLogic.getAsteroids();
		List<Bullet> bullets = gameLogic.getBullets();
		List<EnemyBullet> Ebullets = gameLogic.getEnemyBullets();




		// set orignal font - for later use
		if(this.originalFont == null){
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		
		
		// erase screen
		
		
//		g2d.setPaint(Color.RED);
		  GraphicsManager.drawBackGround(g2d);
		//g2d.fillRect(0, 0, getSize().width, getSize().height);
		
		
		
		if(status.isGameStarting()|| status.isGameStarted() || status.isGameOver())
		{
			//g2d.setPaint(Color.BLACK);
			  GraphicsManager.drawBattleField(g2d);
			
			//g2d.fillRect(0, 0, getSize().width, getSize().height);
			drawStars(150);
		}

		// draw 50 random stars
		drawStars(0);

		// if the game is starting, draw "Get Ready" message
		if(status.isGameStarting()){
			drawGetReady();

			return;
		}

if(InputHandler.ShiftIsPressed()){

	GraphicsManager.drawThruster(g2d, ship);
}
		
		if(status.isWon())
		{
			drawWon();
			return;
		}



		// if the game is over, draw the "Game Over" message
		if(status.isGameOver()){
			// draw the message
			drawGameOver();

			long currentTime = System.currentTimeMillis();
			// draw the explosions until their time passes
			if((currentTime - lastAsteroidTime) < NEW_ASTEROID_DELAY){
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
			if((currentTime - lastShipTime) < NEW_SHIP_DELAY){
				graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
			}
			return;
		}

		// the game has not started yet
		if(!status.isGameStarted()){
			// draw game title screen
			initialMessage();
			return;
		}


		// draw asteroids
		for(int i=0; i<asteroids.size(); i++){
			Asteroid asteroid = asteroids.get(i);
			graphicsMan.drawAsteroid(asteroid, g2d, this);

			boolean remove = gameLogic.moveAsteroid(asteroid);
			if(remove){
				asteroids.remove(i);
				i--;

			}
		}




		// draw bullets
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			graphicsMan.drawBullet(bullet, g2d, this);

			boolean remove = gameLogic.moveBullet(bullet);
			if(remove){
				bullets.remove(i);
				i--;
			}
		}
		//draw enemy bullets
		for(int i=0; i<Ebullets.size(); i++){
			EnemyBullet enemyBullet = Ebullets.get(i);
			graphicsMan.drawEnemyBullet(enemyBullet, g2d, this);

			boolean remove = gameLogic.moveEnemyBullet(enemyBullet);
			if(remove){
				Ebullets.remove(i);
				i--;
			}
		}

		// check bullet-asteroid collisions
		for(int i=0; i<bullets.size(); i++){
			for(int j=0; j<asteroids.size();j++){
				Bullet bullet = bullets.get(i);
				Asteroid asteroid = asteroids.get(j);
				if(asteroid.intersects(bullet)){
					// increase asteroids destroyed count
					status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

					// "remove" asteroid
					asteroidExplosion = new Rectangle(
							asteroid.x,
							asteroid.y,
							asteroid.width,
							asteroid.height);
					graphicsMan.drawAsteroidExplosion(asteroids.get(j), g2d, this);
					asteroids.remove(j);

					status.setNewAsteroid(true);
					gameLogic.createAsteroid();
					lastAsteroidTime = System.currentTimeMillis();

					// play asteroid explosion sound
					soundMan.playAsteroidExplosionSound();

					// remove bullet
					bullets.remove(i);
					break;
				}
			}
		}


		// check bullet-enemy collisions
		for(int i=0; i<bullets.size(); i++){

			Bullet bullet = bullets.get(i);

			if(enemy.intersects(bullet)){
				// increase asteroids destroyed count
				//status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);




				// "remove" enemy ship
				enemyShipExplosion = new Rectangle(
						enemy.x,
						enemy.y,
						enemy.width,
						enemy.height);
				enemy.setLocation(this.getWidth() + enemy.width, -enemy.height);
				status.setNewEnemyShip(true);
				lastShipTime = System.currentTimeMillis();
				graphicsMan.drawEnemyShipExplosion(enemy, g2d, this);
				// play ship explosion sound
				soundMan.playShipExplosionSound();
				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();

				//status.setNewAsteroid(true);
				//gameLogic.createAsteroid();
				//lastAsteroidTime = System.currentTimeMillis();

				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();

				// remove bullet
				bullets.remove(i);
				break;
			}
		}
		// check bullet-ship collisions
		for(int i=0; i<Ebullets.size(); i++){

			EnemyBullet enemyBullet = Ebullets.get(i);

			if(ship.intersects(enemyBullet)){
				// increase asteroids destroyed count
				//status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);
				shieldCounter++;
				MainFrame.setHealth();

				//status.setShipsLeft(status.getShipsLeft() - 1);




				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();

				//status.setNewAsteroid(true);
				//gameLogic.createAsteroid();
				//lastAsteroidTime = System.currentTimeMillis();

				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();

				if(shieldCounter == 3){

					// decrease number of ships left
					status.setShipsLeft(status.getShipsLeft() - 1);

					//resets shields for new ship
					shieldCounter = 0;									


					// "remove" ship
					shipExplosion = new Rectangle(
							ship.x,
							ship.y,
							ship.width,
							ship.height);
					ship.setLocation(this.getWidth() + ship.width, -ship.height);
					status.setNewShip(true);
					lastShipTime = System.currentTimeMillis();

					// play ship explosion sound
					soundMan.playShipExplosionSound();


				}

				// remove bullet
				Ebullets.remove(i);
				break;
			}
		}

		// check bullet-bulllet collisions
		for(int i=0; i<bullets.size(); i++){
			for(int j=0; j<Ebullets.size();j++){
				Bullet bullet = bullets.get(i);
				EnemyBullet Ebullet = Ebullets.get(j);
				if(bullet.intersects(Ebullet)){

					bullets.remove(i);
					Ebullets.remove(j);
					break;
				}
			}
		}



		// draw ship
		if(!status.isNewShip()){
			// draw it in its current location

			graphicsMan.drawShip(ship, g2d, this);
		}
		else{
			// draw a new one
			soundMan.playbarrelRoll();
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastShipTime) > NEW_SHIP_DELAY){
				lastShipTime = currentTime;
				status.setNewShip(false);
				ship = gameLogic.newShip(this);
			}
			else{
				// draw explosion
				graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
			}
		}


		// draw enemy ship
		if(!status.isNewEnemyShip()){
			
			// draw it in its current location
			
			graphicsMan.drawEnemyShip(enemy, g2d, this);
			

			gameLogic.moveEnemy(enemy);
			int x = rand.nextInt(100);
			if(x==1){
				gameLogic.fireEnemyBullet();

			}
		}  
		else{
			// draw a new one
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastShipTime) > NEW_SHIP_DELAY){
				lastShipTime = currentTime;
				status.setNewEnemyShip(false);
				
				
				enemy = gameLogic.newEnemyShip(this);
				//gameLogic.moveEnemy(enemy);
			}
			else{
				// draw explosion 
				graphicsMan.drawEnemyShipExplosion(enemyShipExplosion, g2d, this);
				//gameLogic.moveEnemy(enemy);
			}
		}



		// check ship-asteroid collisions
		for(int i=0; i<asteroids.size(); i++){
			Asteroid asteroid= asteroids.get(i);
			if(asteroid.intersects(ship)){
				// decrease number of ships left
				//status.setShipsLeft(status.getShipsLeft() - 1);

				//decrease shields
				shieldCounter++;
				MainFrame.setHealth();


				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);

				// "remove" asteroid
				asteroidExplosion = new Rectangle(
						asteroid.x,
						asteroid.y,
						asteroid.width,
						asteroid.height);
				asteroids.remove(i);			
				status.setNewAsteroid(true);
				gameLogic.createAsteroid();
				lastAsteroidTime = System.currentTimeMillis();




				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();

				if(shieldCounter == 3){

					// decrease number of ships left
					status.setShipsLeft(status.getShipsLeft() - 1);

					//resets shields for new ship
					shieldCounter = 0;


					// "remove" asteroid
					asteroidExplosion = new Rectangle(
							asteroid.x,
							asteroid.y,
							asteroid.width,
							asteroid.height);
					asteroids.remove(i);			
					status.setNewAsteroid(true);
					gameLogic.createAsteroid();
					lastAsteroidTime = System.currentTimeMillis();

					// "remove" ship
					shipExplosion = new Rectangle(
							ship.x,
							ship.y,
							ship.width,
							ship.height);
					ship.setLocation(this.getWidth() + ship.width, -ship.height);
					status.setNewShip(true);
					lastShipTime = System.currentTimeMillis();

					// play ship explosion sound
					soundMan.playShipExplosionSound();
					// play asteroid explosion sound
					soundMan.playAsteroidExplosionSound();

				}

			}
		}
		// update asteroids destroyed label
		destroyedValueLabel.setText(Long.toString(status.getAsteroidsDestroyed()));

		// update ships left label
		shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));


		// check ship-enemy collisions


		if(enemy.intersects(ship)){
			// decrease number of ships left
			//status.setShipsLeft(status.getShipsLeft() - 1);

			//decrease shields
			shieldCounter++;
			MainFrame.setHealth();


			if(shieldCounter == 3){

				// decrease number of ships left
				status.setShipsLeft(status.getShipsLeft() - 1);

				//resets shields for new ship
				shieldCounter = 0;

				// "remove" ship
				shipExplosion = new Rectangle(
						ship.x,
						ship.y,
						ship.width,
						ship.height);
				ship.setLocation(this.getWidth() + ship.width, -ship.height);
				status.setNewShip(true);


			}
			// "remove" ship
			enemyShipExplosion = new Rectangle(
					enemy.x,
					enemy.y,
					enemy.width,
					enemy.height);
			enemy.setLocation(this.getWidth() + enemy.width, -enemy.height);
			status.setNewEnemyShip(true);


			// play ship explosion sound
			soundMan.playShipExplosionSound();
			// play asteroid explosion sound
			soundMan.playAsteroidExplosionSound();


		}
		// update asteroids destroyed label
		destroyedValueLabel.setText(Long.toString(status.getAsteroidsDestroyed()));

		// update ships left label
		shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));



		if(status.getAsteroidsDestroyed()==0)
		{
			String readyStr="Level 1";
			drawLevel(readyStr);
		}
		else if(status.getAsteroidsDestroyed()==5)
		{
			 
			String readyStr="Level 2";
			drawLevel(readyStr);
		}
		else if(status.getAsteroidsDestroyed()==15)
		{
			String readyStr="Level 3";
			drawLevel(readyStr);
		}
		else if(status.getAsteroidsDestroyed()==30)
		{
			String readyStr="Level 4";
			drawLevel(readyStr);
		}
		else if(status.getAsteroidsDestroyed()==50)
		{


			String readyStr="Level 5";
			drawLevel(readyStr);
		}
		else if(status.getAsteroidsDestroyed()==75)
		{
			gameLogic.youWin();

		}


	}

	/**
	 * Draws the "Game Over" message.
	 */
	private void drawGameOver() {
		String gameOverStr = "GAME OVER";
		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameOverStr);
		if(strWidth > this.getWidth() - 10){
			biggestFont = currentFont;
			bigFont = biggestFont;
			fm = g2d.getFontMetrics(bigFont);
			strWidth = fm.stringWidth(gameOverStr);
		}
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setFont(bigFont);
		g2d.setPaint(Color.WHITE);
		g2d.drawString(gameOverStr, strX, strY);
	}

	// draws You Win
	private void drawWon() {
		String gameOverStr = "You Pass ICOM 4015!!!!!";
		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameOverStr);
		if(strWidth > this.getWidth() - 10){
			biggestFont = currentFont;
			bigFont = biggestFont;
			fm = g2d.getFontMetrics(bigFont);
			strWidth = fm.stringWidth(gameOverStr);
		}
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setFont(bigFont);
		g2d.setPaint(Color.WHITE);
		g2d.drawString(gameOverStr, strX, strY);
	}






	/**
	 * Draws the initial "Get Ready!" message.
	 */
	private void drawGetReady() {
		String readyStr = "Get Ready!";
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(readyStr, strX, strY);

	}

	private void drawLevel(String readyString) {
		String readyStr= readyString;
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(readyStr, strX, strY);

	}


	/**
	 * Draws the specified number of stars randomly on the game screen.
	 * @param numberOfStars the number of stars to draw
	 */
	private void drawStars(int numberOfStars) {
		g2d.setColor(Color.WHITE);
		for(int i=0; i<numberOfStars; i++){
			int x = (int)(Math.random() * this.getWidth());
			int y = (int)(Math.random() * this.getHeight());
			g2d.drawLine(x, y, x, y);
		}
	}

	/**
	 * Display initial game title screen.
	 */
	private void initialMessage() {
		String gameTitleStr = "STAR FOX";

		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD).deriveFont(Font.ITALIC);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameTitleStr);
		if(strWidth > this.getWidth() - 10){
			bigFont = currentFont;
			biggestFont = currentFont;
			fm = g2d.getFontMetrics(currentFont);
			strWidth = fm.stringWidth(gameTitleStr);
		}
		g2d.setFont(bigFont);
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2 - ascent;
		g2d.setPaint(Color.LIGHT_GRAY);
		//g2d.drawString(gameTitleStr, strX, strY);
		g2d.drawImage(GraphicsManager.getNameImg(), this.getHeight()/9, this.getHeight()/4, null);

		g2d.setFont(originalFont);
		fm = g2d.getFontMetrics();
		String newGameStr = "Press <Space> to Start a New Game.";
		strWidth = fm.stringWidth(newGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = (this.getHeight() + fm.getAscent())/2 + ascent + 16;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(newGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String exitGameStr = "Press <Esc> to Exit the Game.";
		strWidth = fm.stringWidth(exitGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = strY + 16;
		g2d.drawString(exitGameStr, strX, strY);
	}

	/**
	 * Prepare screen for game over.
	 */
	public void doGameOver(){
		shipsValueLabel.setForeground(new Color(128, 0, 0));
	}
	public void doWon(){
		shipsValueLabel.setForeground(new Color(128, 0, 0));
	}

	/**
	 * Prepare screen for a new game.
	 */
	public void doNewGame(){		
		lastAsteroidTime = -NEW_ASTEROID_DELAY;
		lastShipTime = -NEW_SHIP_DELAY;

		bigFont = originalFont;
		biggestFont = null;

		// set labels' text
		shipsValueLabel.setForeground(Color.BLACK);
		shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));
		destroyedValueLabel.setText(Long.toString(status.getAsteroidsDestroyed()));
	}

	/**
	 * Sets the game graphics manager.
	 * @param graphicsMan the graphics manager
	 */
	public void setGraphicsMan(GraphicsManager graphicsMan) {
		this.graphicsMan = graphicsMan;
	}

	/**
	 * Sets the game logic handler
	 * @param gameLogic the game logic handler
	 */
	public void setGameLogic(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
		this.status = gameLogic.getStatus();
		this.soundMan = gameLogic.getSoundMan();
	}

	/**
	 * Sets the label that displays the value for asteroids destroyed.
	 * @param destroyedValueLabel the label to set
	 */
	public void setDestroyedValueLabel(JLabel destroyedValueLabel) {
		this.destroyedValueLabel = destroyedValueLabel;
	}

	/**
	 * Sets the label that displays the value for ship (lives) left
	 * @param shipsValueLabel the label to set
	 */
	public void setShipsValueLabel(JLabel shipsValueLabel) {
		this.shipsValueLabel = shipsValueLabel;
	}
	public static int getShieldCounter(){
		return shieldCounter;
	}
}
