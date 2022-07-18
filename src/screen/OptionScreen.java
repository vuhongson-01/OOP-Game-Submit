package screen;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import main.Main;
import main.Sound;
import main.GameInterface;
import main.GamePanel;
import main.UtilityTool;

public class OptionScreen extends JPanel implements GameInterface{
    
    JButton playerOptionBtn, levelOptionBtn, backBtn, startBtn, settingBtn;
    JPanel bgr;
    Main main;
    ImageIcon icon;
    
    public OptionScreen(Main main) {
    	setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setDoubleBuffered(true);
		setFocusable(true);
		setLayout(null);
		
		this.main = main;
		playerOptionBtn = new JButton();
		levelOptionBtn = new JButton();
		backBtn = new JButton();
		startBtn = new JButton();
	
		bgr = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				super.paintComponent(g);
				Graphics2D graphics2d = (Graphics2D) g;
				UtilityTool uTool = new UtilityTool();
				try {
					BufferedImage bgrImg = ImageIO.read(getClass().getResourceAsStream("/background/screenBgr.png"));
					bgrImg = uTool.scaleImage(bgrImg, SCREEN_WIDTH, SCREEN_HEIGHT);
					graphics2d.drawImage(bgrImg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
				} catch (IOException e) {
					e.printStackTrace();
				}
				graphics2d.dispose();
			}
		};
		setup();
		
		add(playerOptionBtn);
		add(levelOptionBtn);
		add(startBtn);
		add(backBtn);
		add(bgr);
    }
    
    private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();  
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
        return new ImageIcon(resizedImage);
    }
    
    void setupButton(JButton button, String iconPath, int width, int height, int x, int y) {
    	icon = new ImageIcon(iconPath);
		button.setIcon(resizeIcon(icon, width, height));
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setBounds(x, y, width, height);
    }
    
    void setup() {
    	
    	bgr.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    	setupButton(playerOptionBtn, "res/button/playerOptionBtn.png", TILE_SIZE*4, TILE_SIZE*2, SCREEN_WIDTH/2 - TILE_SIZE*2, TILE_SIZE*5);
    	
    	setupButton(levelOptionBtn, "res/button/levelOptionBtn.png", TILE_SIZE*4, TILE_SIZE*2, SCREEN_WIDTH/2 - TILE_SIZE*2, TILE_SIZE*7);
		
    	setupButton(startBtn, "res/button/startBtn.png", TILE_SIZE*4, TILE_SIZE*2, SCREEN_WIDTH/2 - TILE_SIZE*2, TILE_SIZE*9);
    	
    	setupButton(backBtn, "res/button/backBtn.png", TILE_SIZE*4, TILE_SIZE*2, SCREEN_WIDTH/2 - TILE_SIZE*2, TILE_SIZE*11);
		
		playerOptionBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound();
				main.pushPlayerOptionScreen();
				main.nextScreen();
				
			}
		});
		
		levelOptionBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound();
				main.pushLevelOptionScreen();
				main.nextScreen();
			}
		});
		
		backBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound();
				main.backScreen();
			}
		});
		
		startBtn.addActionListener(new ActionListener() {
//			
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound();
				GamePanel gamePanel = new GamePanel(main);
				main.newGame(gamePanel);
				
				//reset setup
				main.hard = 1;
			}
		});
		
    }
    
    void playSound() {
    	Sound music = new Sound(false);
    	music.setFile(16);
    	music.play();
    }
   
}
