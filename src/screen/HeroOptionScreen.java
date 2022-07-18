package screen;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import main.GameInterface;
import main.UtilityTool;
import main.Sound;

public class HeroOptionScreen extends JPanel implements GameInterface{
    
    JButton hero1, hero2, hero3, backBtn, checkhero1, checkhero2, checkhero3, name1, name2, name3, title;
    Main main;
    JPanel bgr;
    ImageIcon icon;
    public static int hero = 1;
    
    public HeroOptionScreen(Main main) {
    	setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setDoubleBuffered(true);
		setFocusable(true);
		setLayout(null);
		
		this.main = main;
		hero1 = new JButton();
		hero2 = new JButton();
		hero3 = new JButton();
		
		name1 = new JButton("DarkNight");
		name2 = new JButton("Fighter");
		name3 = new JButton("SpearKnight");
		title = new JButton("Choose Hero");
		
		backBtn = new JButton();
		checkhero1 = new JButton();
		checkhero2 = new JButton();
		checkhero3 = new JButton();
		checkhero1.setVisible(true);
		checkhero2.setVisible(false);
		checkhero3.setVisible(false);

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
		
		add(hero1);
		add(hero2);
		add(title);
		add(name1);
		add(name2);
		add(name3);
		add(backBtn);
		add(checkhero1);
		add(checkhero2);
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
		button.setForeground(Color.decode("#331a00"));
		button.setFont(new Font("Courier", Font.BOLD, 18));
		button.setBounds(x, y, width, height);
    }
    
    void setup() {
    	bgr.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    	
    	setupButton(hero1, "res/avatar/hero1.png", TILE_SIZE*3, TILE_SIZE*3, SCREEN_WIDTH/2 - TILE_SIZE*11/2, TILE_SIZE*5);
    	
    	setupButton(hero2, "res/avatar/hero2.png", TILE_SIZE*3, TILE_SIZE*3, SCREEN_WIDTH/2 + TILE_SIZE*5/2, TILE_SIZE*5);
		
    	setupButton(backBtn, "res/button/backBtn.png", TILE_SIZE*2, TILE_SIZE, TILE_SIZE, TILE_SIZE);
    	
    	setupButton(title, "", TILE_SIZE*5, TILE_SIZE, SCREEN_WIDTH/2 - TILE_SIZE*5/2, TILE_SIZE*3);
    
    	setupButton(checkhero1, "res/avatar/check.png", TILE_SIZE, TILE_SIZE, SCREEN_WIDTH/2 - TILE_SIZE*9/2, TILE_SIZE*9);
    	
    	setupButton(checkhero2, "res/avatar/check.png", TILE_SIZE, TILE_SIZE, SCREEN_WIDTH/2 + TILE_SIZE*7/2, TILE_SIZE*9);
    	
    	setupButton(name1, "", TILE_SIZE*3, TILE_SIZE, SCREEN_WIDTH/2 - TILE_SIZE*11/2, TILE_SIZE*8);
    	
    	setupButton(name2, "", TILE_SIZE*3, TILE_SIZE, SCREEN_WIDTH/2 + TILE_SIZE*5/2, TILE_SIZE*8);
		
		hero1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound();
				main.hero = 1;
				checkhero1.setVisible(true);
				checkhero2.setVisible(false);
			}
		});
		
		
		hero2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				playSound();
				main.hero = 2;
				checkhero1.setVisible(false);
				checkhero2.setVisible(true);
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
