package rbadia.voidspace.main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * The game's main frame. Contains all the game's labels, file menu, and game screen.
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private GameScreen gameScreen = null;
	
	private JLabel destroyedLabel;
	private JLabel destroyedValueLabel;

	private JLabel shipsLabel;
	private JLabel shipsValueLabel;
	
	
	
	public static Color color;
	public static String stringColor = "";
	public static String healthLenght = ("                       ");	
	private JLabel healthLabel;
	private static JLabel healthValueLabel = new JLabel(healthLenght);

	
	
	/**
	 * This is the default constructor
	 */
	public MainFrame() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(502, 440);
		this.setContentPane(getJContentPane());
		this.setTitle("Void Space");
//		this.setResizable(false);
		
		Dimension dim = this.getToolkit().getScreenSize();
		Rectangle bounds = this.getBounds();
		this.setLocation(
			(dim.width - bounds.width) / 2,
			(dim.height - bounds.height) / 2);
	}

	
	
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	
	
	private  JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints6.gridy = 1;
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.weightx = 1.0D;
			gridBagConstraints6.gridx = 5;			
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints5.gridy = 1;
			gridBagConstraints5.anchor = GridBagConstraints.EAST;
			gridBagConstraints5.weightx = 1.0D;
			gridBagConstraints5.gridx = 4;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints4.gridy = 1;
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.weightx = 1.0D;
			gridBagConstraints4.gridx = 3;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.anchor = GridBagConstraints.EAST;
			gridBagConstraints3.weightx = 1.0D;
			gridBagConstraints3.gridx = 2;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.weightx = 1.0D;
			gridBagConstraints2.gridx = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.anchor = GridBagConstraints.EAST;
			gridBagConstraints1.weightx = 1.0D;
			gridBagConstraints1.gridx = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.fill = GridBagConstraints.NONE;
			gridBagConstraints.gridwidth = 0;
			shipsLabel = new JLabel("Ships Left: ");
			shipsValueLabel = new JLabel("3");
			destroyedLabel = new JLabel("Asteroids Destroyed: ");
			destroyedValueLabel = new JLabel("0");
			healthLabel = new JLabel("Shields: ");				
			healthValueLabel.setOpaque(true);
			healthValueLabel.setBackground(Color.GREEN);

			
				
		

			

			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(getGameScreen(), gridBagConstraints);
			jContentPane.add(shipsLabel, gridBagConstraints1);
			jContentPane.add(shipsValueLabel, gridBagConstraints2);			
			jContentPane.add(destroyedLabel, gridBagConstraints3);
			jContentPane.add(destroyedValueLabel, gridBagConstraints4);
			jContentPane.add(healthLabel, gridBagConstraints5);
			jContentPane.add(healthValueLabel, gridBagConstraints6);
		}
		return jContentPane;
	}

	/**
	 * This method initializes gameScreen	
	 * 	
	 * @return GameScreen
	 */
	public GameScreen getGameScreen() {
		if (gameScreen == null) {
			gameScreen = new GameScreen();
			gameScreen.setShipsValueLabel(shipsValueLabel);
			gameScreen.setDestroyedValueLabel(destroyedValueLabel);
		}
		return gameScreen;
	}
	
	public static void setHealth(){
		if(GameScreen.getShieldCounter() == 0||GameScreen.getShieldCounter() == 3 ){
			
			healthValueLabel.setText("                       ");
			healthValueLabel.setBackground(Color.GREEN);

		}
		else if(GameScreen.getShieldCounter()  == 1){			
			healthValueLabel.setText("              ");
			healthValueLabel.setBackground(Color.YELLOW);

		}
		if(GameScreen.getShieldCounter()  == 2){			
			healthValueLabel.setText("   ");
			healthValueLabel.setBackground(Color.RED);

		}
	}
	
	

}  //  @jve:decl-index=0:visual-constraint="10,10"

