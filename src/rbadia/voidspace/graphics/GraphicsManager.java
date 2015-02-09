package rbadia.voidspace.graphics;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import rbadia.voidspace.model.Asteroid;

import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyBullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.Ship;

/**
 * Manages and draws game graphics and images.
 */
public class GraphicsManager 
{
	public static BufferedImage shipImg;
	private static BufferedImage bulletImg;
	private static BufferedImage asteroidImg;
	private static BufferedImage asteroidImg2;
	private static BufferedImage asteroidExplosionImg;
	private static BufferedImage shipExplosionImg;
	private static BufferedImage eBulletImg;
	public static BufferedImage eShipImg;
	public static BufferedImage backGround;
	public static BufferedImage nameGame;
	public static BufferedImage battleField;
	public static BufferedImage thruster;

	/**
	 * Creates a new graphics manager and loads the game images.
	 */
	public GraphicsManager(){
		// load images
		try {
			shipImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/ship.png"));
			asteroidImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroid.png"));
			asteroidImg2 = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroid2.png"));
			asteroidExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroidExplosion.png"));
			shipExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/shipExplosion.png"));
			bulletImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bullet.png"));
			eBulletImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/Ebullet.png"));
			eShipImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/Eship.png"));
			backGround = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/light outer space stars planets earth cosmos star fox 1920x1080 wallpaper_www.wallpaperhi.com_84.png"));
			nameGame = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/GameName.png"));
			battleField = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/starfoxlylat.png"));
			thruster = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/thrusters.png"));

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "The graphic files are either corrupt or missing.",
					"VoidSpace - Fatal Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public static void drawBackGround( Graphics2D g2d) {
		g2d.drawImage(backGround, null, null );
	}
	public static void drawBattleField( Graphics2D g2d) {
		g2d.drawImage(battleField, null, null );
	}
	public static void drawThruster( Graphics2D g2d, Ship ship) {
		g2d.drawImage(thruster, null, ship.x+12, ship.y+25 );
	}
	
	/**
	 * Draws a ship image to the specified graphics canvas.
	 * @param ship the ship to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawShip(Ship ship, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(shipImg, ship.x, ship.y, observer);
	}
	/**
	 * Draws a ship image to the specified graphics canvas.
	 * @param enemy the enemy ship to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	
	
	public void drawEnemyShip(EnemyShip enemy, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(eShipImg, enemy.x, enemy.y, observer);
	}

	/**
	 * Draws a bullet image to the specified graphics canvas.
	 * @param bullet the bullet to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBullet(Bullet bullet, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bulletImg, bullet.x, bullet.y, observer);
	}
	/**
	 * Draws a bullet image to the specified graphics canvas.
	 * @param enemyBullet the enemy bullet to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawEnemyBullet(EnemyBullet enemyBullet, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(eBulletImg, enemyBullet.x, enemyBullet.y, observer);
	}


	/**
	 * Draws an asteroid image to the specified graphics canvas.
	 * @param asteroid the asteroid to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawAsteroid(Asteroid asteroid, Graphics2D g2d, ImageObserver observer) {
		if(asteroid.getBig()){
			g2d.drawImage(asteroidImg2, asteroid.x, asteroid.y, observer);
		}else{
			g2d.drawImage(asteroidImg, asteroid.x, asteroid.y, observer);
		}
		
	}
	
	

	/**
	 * Draws a ship explosion image to the specified graphics canvas.
	 * @param shipExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawShipExplosion(Rectangle shipExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(shipExplosionImg, shipExplosion.x, shipExplosion.y, observer);
	}
	/**
	 * Draws an enemy ship explosion image to the specified graphics canvas.
	 * @param enemyShipExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */

	public void drawEnemyShipExplosion(Rectangle enemyShipExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(shipExplosionImg, enemyShipExplosion.x, enemyShipExplosion.y, observer);
	}


	/**
	 * Draws an asteroid explosion image to the specified graphics canvas.
	 * @param asteroidExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawAsteroidExplosion(Rectangle asteroidExplosion, Graphics2D g2d, ImageObserver observer) {

		g2d.drawImage(asteroidExplosionImg, asteroidExplosion.x, asteroidExplosion.y, observer);

	}
	
	public static BufferedImage getBigAsteroidImg(){
		return asteroidImg2;
	}
	public static BufferedImage getSmallAsteroidImg(){
		return asteroidImg;
	}
	public static BufferedImage getAsteroidExplosionImg(){
		return asteroidExplosionImg;
	}
	public static BufferedImage getShipImg(){
		return shipImg;
	}
	public static BufferedImage getShipExplosionImg(){
		return shipExplosionImg;
	}
	public static BufferedImage getBulletImg(){
		return bulletImg;
	}
	public static BufferedImage getEshipImg(){
		return eShipImg;
	}
	public static BufferedImage getEBulletImg(){
		return eBulletImg;
	}
	public static BufferedImage getNameImg(){
		return nameGame;
	}
	public static BufferedImage getBFImg(){
		return battleField;
	}
	public static BufferedImage getThruster(){
		return thruster;
	}
	

}
