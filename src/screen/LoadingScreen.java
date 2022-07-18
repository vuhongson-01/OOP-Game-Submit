package screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.GameInterface;
import main.UtilityTool;

public class LoadingScreen extends JPanel implements GameInterface{

	public LoadingScreen() {
		setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setDoubleBuffered(true);
		setFocusable(true);
		setLayout(null);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
		Graphics2D graphics2d = (Graphics2D) g;
		UtilityTool uTool = new UtilityTool();
		try {
			BufferedImage bgrImg = ImageIO.read(getClass().getResourceAsStream("/background/loadingScreen.png"));
			bgrImg = uTool.scaleImage(bgrImg, SCREEN_WIDTH, SCREEN_HEIGHT);
			graphics2d.drawImage(bgrImg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
			
			String loadingText = "Loading...";
			
//			graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 48F));
			graphics2d.setFont(new Font("courier", Font.BOLD, 48));
			graphics2d.setColor(Color.white);
			int lengthText = (int)graphics2d.getFontMetrics().getStringBounds(loadingText, graphics2d).getWidth();
			int x = SCREEN_WIDTH/2 - lengthText/2;
			
			graphics2d.drawString(loadingText, x, SCREEN_HEIGHT/2);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		graphics2d.dispose();
	}

}
