package screen;

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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import main.Main;
import main.Sound;
import main.GameInterface;
import main.UtilityTool;

public class MoreScreen extends JPanel implements GameInterface{
    
    JButton backBtn;
    JLabel title;
    JTextArea textArea;
    Main main;
    JPanel bgr, board;
    String[] info = {"Press A, W, S, D to move, press F to attack",
    				"Press 1, 2 or 3 to use player's skill", 
    				"Press P to Pause/Resume", 
    				"Team 17",
    				"1. Vu Hong Son", "2. Pham Viet Hoang", "3. Chu Thien Long", "4. Nguyen Dang Khoa", "5. Nguyen Chi Hieu",};
    ImageIcon icon;
    
    public MoreScreen(Main main) {
    	setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setDoubleBuffered(true);
		setFocusable(true);
		setLayout(null);
		
		this.main = main;
		backBtn = new JButton();
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
					graphics2d.setFont(new Font("courier", Font.BOLD, 18));
					for (int i = 0; i < info.length; i++)
						graphics2d.drawString(info[i], TILE_SIZE*5, TILE_SIZE*(i+4));
				} catch (IOException e) {
					e.printStackTrace();
				}
				graphics2d.dispose();
			}
		};

		setup();
		
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

    	setupButton(backBtn, "res/button/backBtn.png", TILE_SIZE*2, TILE_SIZE, TILE_SIZE, TILE_SIZE);
		
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
