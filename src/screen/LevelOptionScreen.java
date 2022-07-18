package screen;
import java.awt.BorderLayout;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.PublicKey;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import main.Main;
import main.Sound;
import main.GameInterface;
import main.UtilityTool;

public class LevelOptionScreen extends JPanel implements GameInterface{
    
    JButton easyBtn, hardBtn, backBtn, checkEasyBtn, checkHardBtn;
    JLabel title;
    Main main;
    JPanel bgr;
    ImageIcon icon;
    public static int hard = 1;
    
    public LevelOptionScreen(Main main) {
    	setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setDoubleBuffered(true);
		setFocusable(true);
		setLayout(null);
		
		this.main = main;
		easyBtn = new JButton();

		hardBtn = new JButton();
		backBtn = new JButton();
		checkEasyBtn = new JButton();
		checkHardBtn = new JButton();
		checkEasyBtn.setVisible(true);
		checkHardBtn.setVisible(false);

		bgr = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				super.paintComponent(g);
				Graphics2D graphics2d = (Graphics2D) g;
				UtilityTool uTool = new UtilityTool();
				try {
					BufferedImage bgrImg = ImageIO.read(getClass().getResourceAsStream("/background/moreScreen.png"));
					bgrImg = uTool.scaleImage(bgrImg, SCREEN_WIDTH, SCREEN_HEIGHT);
					graphics2d.drawImage(bgrImg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
				} catch (IOException e) {
					e.printStackTrace();
				}
				graphics2d.dispose();
			}
		};
		
		setup();
		
		add(easyBtn);
		add(hardBtn);
		add(backBtn);
		add(checkEasyBtn);
		add(checkHardBtn);
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
    	
    	setupButton(easyBtn, "res/button/easyBtn.png", TILE_SIZE*4, TILE_SIZE*2, SCREEN_WIDTH/2 - TILE_SIZE*2, TILE_SIZE*5);
    	
    	setupButton(hardBtn, "res/button/hardBtn.png", TILE_SIZE*4, TILE_SIZE*2, SCREEN_WIDTH/2 - TILE_SIZE*2, TILE_SIZE*7);
		
    	setupButton(backBtn, "res/button/backBtn.png", TILE_SIZE*2, TILE_SIZE, TILE_SIZE, TILE_SIZE);
    	
    	setupButton(checkEasyBtn, "res/button/check.png", TILE_SIZE, TILE_SIZE, SCREEN_WIDTH/2 + TILE_SIZE*2, TILE_SIZE*5 + TILE_SIZE/2);
    	
    	setupButton(checkHardBtn, "res/button/check.png", TILE_SIZE, TILE_SIZE, SCREEN_WIDTH/2 + TILE_SIZE*2, TILE_SIZE*7 + TILE_SIZE/2);
		
		easyBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound();
				main.hard = 1;
				checkEasyBtn.setVisible(true);
				checkHardBtn.setVisible(false);
			}
		});
		
		
		hardBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound();
				main.hard = 2;
				checkEasyBtn.setVisible(false);
				checkHardBtn.setVisible(true);
			}
		});
		
		backBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound();
				main.backScreen();
			}
		});
		
    }
    
    void playSound() {
    	Sound music = new Sound(false);
    	music.setFile(16);
    	music.play();
    }
   
}
