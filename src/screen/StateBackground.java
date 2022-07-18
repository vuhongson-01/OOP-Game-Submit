package screen;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;
import map.Map;

public class StateBackground {

	GamePanel gp;
	BufferedImage [] backgroundMap = new BufferedImage [5];
	int level = 0;
	public int scaleX = 0;
	public int scaleY = 0;
	public int [][] mapDemo;
	public StateBackground(GamePanel gamePanel) {
		this.gp = gamePanel;
	}
	
	
	public void setup(int level, int scaleX, int scaleY) {
		Map map = new Map();
		mapDemo = map.world0[level];
		

		this.level = level;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		UtilityTool uTool = new UtilityTool();
		try {
			backgroundMap[level] = ImageIO.read(getClass().getResourceAsStream("/background/state" + level + ".png"));
			backgroundMap[level] = uTool.scaleImage(backgroundMap[level], gp.tileSize*scaleX, gp.tileSize*scaleY);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D graphics2d, int level, int x, int y) {

		graphics2d.drawImage(backgroundMap[level], x, y, gp.tileSize * scaleX, gp.tileSize * scaleY, null);		
		
//		graphics2d.setColor(Color.black);
//		for (int i = 0; i < mapDemo.length; i++) {
//			for (int j = 0; j < mapDemo[i].length; j++) {
//				if (mapDemo[i][j] == 1) {
//					graphics2d.drawString("XXX", j*48 + x + 20, y + i*48 + 20);
//				}
//			}
//		}
	}

}
