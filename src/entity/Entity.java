package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GameInterface;
import main.GamePanel;
import main.UtilityTool;

public class Entity implements GameInterface {

	public int x,y;

	public int enemyX, enemyY; // save position of enemy on screen
	public int initX, initY; 
	public int speed;
	public int runSpeed = 3;
	public int direction = 0;
	public int f_attack = 0;
		
	public BufferedImage up[] = new BufferedImage[10];
	public BufferedImage down[] = new BufferedImage[10];
	public BufferedImage left[] = new BufferedImage[10];
	public BufferedImage right[] = new BufferedImage[10];
	public BufferedImage die[] = new BufferedImage[10];
	public BufferedImage gate[] = new BufferedImage[10];
	
	public BufferedImage leftAttack[] = new BufferedImage[10];
	public BufferedImage rightAttack[] = new BufferedImage[10];
	public BufferedImage upAttack[] = new BufferedImage[10];
	public BufferedImage downAttack[] = new BufferedImage[10];
	public BufferedImage skill1[] = new BufferedImage[10];
	public BufferedImage skill2[] = new BufferedImage[15];
	public BufferedImage skill3[] = new BufferedImage[10];
	
	
	public String action, preAction;
	public boolean shieldIsOn = false;
	public boolean provoked = false;
	public boolean attacking = false;
	
	int f = 0;
	public int f_die=0;
	
	public int defaultHP = 500;
	public int hp, mp, attack, defense, attackRange;
	public int attackSpeed;
	
//	~ f
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
//	area that can give damage, example: boundary of sword; -----------
	
	public int selfArea[];          // instead of 4 variables ^
	public int damageArea[];		// instead of 4 variables ^
	
	GamePanel gp;
	public int directionAttack;

	public int shield;
	
	public Entity() {
		
	}
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	public boolean isInNextStateArea(int [][] map) {
		if (map[selfArea[1]/TILE_SIZE][selfArea[0]/TILE_SIZE] == 3 && map[selfArea[3]/TILE_SIZE][selfArea[0]/TILE_SIZE] == 3 && map[selfArea[1]/TILE_SIZE][selfArea[2]/TILE_SIZE] == 3 && map[selfArea[3]/TILE_SIZE][selfArea[2]/TILE_SIZE] == 3) return true;
		else return false;
	}
	
	public boolean isInWinArea(int [][] map) {
		if (map[selfArea[1]/TILE_SIZE][selfArea[0]/TILE_SIZE] == 4 && map[selfArea[3]/TILE_SIZE][selfArea[0]/TILE_SIZE] == 4 && map[selfArea[1]/TILE_SIZE][selfArea[2]/TILE_SIZE] == 4 && map[selfArea[3]/TILE_SIZE][selfArea[2]/TILE_SIZE] == 4) return true;
		else return false;
	}

	public BufferedImage setup(String imageName, int width, int height) {
//		System.out.println(1);
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imageName + ".png"));
			image = uTool.scaleImage(image, width, height);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
	
	public void decreHP(int attack) {
		// TODO Auto-generated method stub
		hp -= attack;
	}
	
	public void draw(Graphics2D graphics2d) {
	}

	public void update(int[][] mapDemo) {
		// TODO Auto-generated method stub
		
	}

	public void setDefaultValue(int tileSize, int i) {
		// TODO Auto-generated method stub
		
	}
	
	public void drawNotify(Graphics2D graphics2d) {
		graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 12F));
		graphics2d.setColor(Color.black);
		
		graphics2d.drawString("THE BOSS DOOR IS OPENED!!!", x-gp.worldx, y-30-gp.worldy);
	}
	
}
