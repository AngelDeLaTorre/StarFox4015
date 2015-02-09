package rbadia.voidspace.sounds;

import java.applet.Applet;
import java.applet.AudioClip;

import rbadia.voidspace.main.GameScreen;

/**
 * Manages and plays the game's sounds.
 */
public class SoundManager {
	private static final boolean SOUND_ON = true;

    private AudioClip shipExplosionSound = Applet.newAudioClip(GameScreen.class.getResource(
    "/rbadia/voidspace/sounds/shipExplosion.wav"));
    private AudioClip bulletSound = Applet.newAudioClip(GameScreen.class.getResource(
    "/rbadia/voidspace/sounds/laser.wav"));
    private AudioClip background = Applet.newAudioClip(GameScreen.class.getResource(
    	    "/rbadia/voidspace/sounds/corneria.wav"));
    private AudioClip gameOver1 = Applet.newAudioClip(GameScreen.class.getResource(
    	    "/rbadia/voidspace/sounds/fox-ahhh.wav"));
    private AudioClip newShip = Applet.newAudioClip(GameScreen.class.getResource(
    	    "/rbadia/voidspace/sounds/peproll1.wav"));
    private AudioClip finalLevel = Applet.newAudioClip(GameScreen.class.getResource(
    	    "/rbadia/voidspace/sounds/fox-allrep.wav"));
    
    /**
     * Plays sound for bullets fired by the ship.
     */
    public void playBulletSound(){
    	if(SOUND_ON){
    		new Thread(new Runnable(){
    			public void run() {
    				bulletSound.play();
    			}
    		}).start();
    	}
    }
    
    /**
     * Plays sound for ship explosions.
     */
    public void playShipExplosionSound(){
    	if(SOUND_ON){
    		new Thread(new Runnable(){
    			public void run() {
    				shipExplosionSound.play();
    			}
    		}).start();
    	}
    }
    
    /**
     * Plays sound for asteroid explosions.
     */
    public void playAsteroidExplosionSound(){
		// play sound for asteroid explosions
    	if(SOUND_ON){
    		new Thread(new Runnable(){
    			public void run() {
    				shipExplosionSound.play();
    			}
    		}).start();
    	}
    		
    	}
    public void playBackground(){
    	if(SOUND_ON){
    		new Thread(new Runnable(){
    			public void run() {
    				background.play();
    			}
    		}).start();
    	}
    }
    
    public void playGameOver1(){
    	if(SOUND_ON){
    		new Thread(new Runnable(){
    			public void run() {
    				gameOver1.play();
    			}
    		}).start();

    	}
    }
    
    public void playbarrelRoll(){
    	if(SOUND_ON){
    		new Thread(new Runnable(){
    			public void run() {
    				newShip.play();
    			}
    		}).start();
    	}
    }
    
    public void playFinalLevel(){
    	if(SOUND_ON){
    		new Thread(new Runnable(){
    			public void run() {
    				finalLevel.play();
    			}
    		}).start();
    	}
    }
    
    }

