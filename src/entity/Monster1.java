package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.GamePanel;
import main.GameInterface;


public class Monster1 extends Entity implements GameInterface{

	String name;
	private final int defaultHP = 100;
	private final int defaultMP = 100;
	public int direction = 0;
	
	public int f = 0;
	public int f_die = 0;
	public int f_attack = 0;
	
	public Monster1(GamePanel gp, int hard, int firstLocX, int firstLocY) {
		this.gp = gp;
		name = "slime LV1";
		selfArea = new int[4];
		damageArea = new int[4];
		setDefaultValue(hard, firstLocX, firstLocY);
		getMonsterImage();
	}
	
	public void setDefaultValue(int hard, int firstLocX, int firstLocY) {
//		location for draw image
		initX = firstLocX;
		initY = firstLocY;
		x = initX;
		y = initY;
		

		hp = defaultHP;
		mp = defaultMP;
		attack = 15;
		attackRange = TILE_SIZE*2/3;
		defense = 10;
		speed = 1;
		runSpeed = 2;
		
	}
	
	public void getMonsterImage() {
		for(int i = 0; i < 4; i++) {
			left[i] = setup("/monster/slime_left_"+(i+1), TILE_SIZE, TILE_SIZE);
			right[i] = setup("/monster/slime_right_"+(i+1), TILE_SIZE, TILE_SIZE);
		}
		for(int i = 0; i < 5; i++) {
			leftAttack[i] = setup("/monster/slime_attack_left_"+(i+1), TILE_SIZE, TILE_SIZE);
			rightAttack[i] = setup("/monster/slime_attack_right_"+(i+1), TILE_SIZE, TILE_SIZE);			
		}
		
		die[0] = setup("/monster/slime_die_1", TILE_SIZE, TILE_SIZE);
		die[1] = setup("/monster/slime_die_2", TILE_SIZE, TILE_SIZE);
		die[2] = setup("/monster/slime_die_3", TILE_SIZE, TILE_SIZE);
		
	}
	
	public void update(int [][] map) { // update player's position
		
		if(hp > 0) {
			f++;
			if((initX - x)*(initX - x) + (initY - y)*(initY - y) < 100 * TILE_SIZE * TILE_SIZE) {
				if(!attacking) {
					if(!provoked) {
						if (f % 180 == 0 || isBlocked(map, x, y)) {
							direction = (direction + 180) % 360;
							f = 0;
						}
					
						if(direction == 0) x += speed;
						else x -= speed;
						
					}
					
				}else {
					f_attack++;
					direction = directionAttack;
				}
			}else{
				x = initX;
				y = initY;
				hp = defaultHP;
			}
			selfArea[0] = x;
			selfArea[1] = y;
			selfArea[2] = x + TILE_SIZE;
			selfArea[3] = y + TILE_SIZE;		
		}else {
			f_die++;
			if(f_die >= 60) {
				x = -1;
				y = -1;
				selfArea[0] = -1;
				selfArea[1] = -1;
				selfArea[2] = -1;
				selfArea[3] = -1;
				
			}
		}
		
	}


	private boolean isBlocked(int[][] map, int x, int y) {
		
		if (direction == 180) {
			if (map[(y + TILE_SIZE/2) / TILE_SIZE][(x + speed) / TILE_SIZE] != 1 &&
				map[(y + TILE_SIZE) / TILE_SIZE][(x + speed) / TILE_SIZE] != 1) return true;
		}
		else {
			if (map[(y + TILE_SIZE/2) / TILE_SIZE][(x + TILE_SIZE + speed) / TILE_SIZE] != 1 &&
				map[(y + TILE_SIZE) / TILE_SIZE][(x + TILE_SIZE + speed) / TILE_SIZE] != 1) return true;
		}
		return false;
	}
	
	public void draw(Graphics2D graphics2d) {
		if(hp > 0) {
			drawHealthBar(graphics2d);			
			if(!attacking) {
				if(direction == 180) {
					if (f % 60 < 15) graphics2d.drawImage(left[0], x - gp.worldx, y - gp.worldy, null);
					else if (f % 60 < 30) graphics2d.drawImage(left[1], x - gp.worldx, y - gp.worldy, null);
					else if (f % 60 < 45) graphics2d.drawImage(left[2],x - gp.worldx, y - gp.worldy, null);
					else graphics2d.drawImage(left[3], x - gp.worldx, y - gp.worldy, null);
				}
				else if(direction == 0) {
					if(f % 60 < 15) graphics2d.drawImage(right[0], x - gp.worldx, y - gp.worldy, null);
					else if (f % 60 < 30) graphics2d.drawImage(right[1], x - gp.worldx, y - gp.worldy, null);
					else if (f % 60 < 45) graphics2d.drawImage(right[2], x - gp.worldx, y - gp.worldy, null);
					else graphics2d.drawImage(right[3], x - gp.worldx, y - gp.worldy, null);
				}
			}else {
				if (directionAttack == 180) {
					if (f_attack % 120 < 10) graphics2d.drawImage(leftAttack[0], x - gp.worldx, y - gp.worldy, null);
					else if (f_attack % 120 < 20) graphics2d.drawImage(leftAttack[1], x - gp.worldx, y - gp.worldy, null);
					else if (f_attack % 120 < 50) graphics2d.drawImage(leftAttack[2], x - gp.worldx, y - gp.worldy, null);
					else if (f_attack % 120 < 55) {
						graphics2d.drawImage(leftAttack[3], x - gp.worldx, y - gp.worldy, null);
						if(f_attack % 120 == 52) gp.bossTakeAttack(damageArea, attack);
						damageArea[0] = x;
						damageArea[1] = y;
						damageArea[2] = x + TILE_SIZE / 2;
						damageArea[3] = y + TILE_SIZE;
					}
					else if (f_attack % 120 < 60) graphics2d.drawImage(leftAttack[4], x - gp.worldx, y - gp.worldy, null);
					else {
						attacking = false;
						f_attack = 0;
					}
				
				}
				else if (directionAttack == 0) {
					if (f_attack % 120 < 10) graphics2d.drawImage(rightAttack[0], x - gp.worldx, y - gp.worldy, null);
					else if (f_attack % 120 < 20) graphics2d.drawImage(rightAttack[1], x - gp.worldx, y - gp.worldy, null);
					else if (f_attack % 120 < 50) graphics2d.drawImage(rightAttack[2], x - gp.worldx, y - gp.worldy, null);
					else if (f_attack % 120 < 55) {
						graphics2d.drawImage(rightAttack[3], x - gp.worldx, y - gp.worldy, null);
						if(f_attack % 120 == 52) gp.bossTakeAttack(damageArea, attack);
						damageArea[0] = x + TILE_SIZE / 2;
						damageArea[1] = y;
						damageArea[2] = x + TILE_SIZE;
						damageArea[3] = y + TILE_SIZE;
					}
					else if (f_attack % 120 < 60) graphics2d.drawImage(rightAttack[4], x - gp.worldx, y - gp.worldy, null);
					else {
						attacking = false;
						f_attack = 0;
					}
				}
			}
		
		}else {
			if(f_die < 20) graphics2d.drawImage(die[0],  x - gp.worldx,  y - gp.worldy,  null);
			else if(f_die < 40) graphics2d.drawImage(die[1],  x - gp.worldx,  y - gp.worldy,  null);
			else if(f_die < 60) graphics2d.drawImage(die[2],  x - gp.worldx,  y - gp.worldy,  null);
			else {

			}
		}
	}
	
	
	private void drawHealthBar(Graphics2D graphics2d) {
		graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 12F));
		graphics2d.setColor(Color.black);
		
		graphics2d.drawString(name, x-gp.worldx, y-10-gp.worldy);
		
		graphics2d.fillRoundRect(x-22-gp.worldx, y-5-gp.worldy, gp.tileSize * 2 + 4, 10, 4, 4);
		graphics2d.setColor(Color.red);
		graphics2d.fillRoundRect(x-20-gp.worldx, y-4-gp.worldy, (int)(hp*1.0/defaultHP * gp.tileSize * 2), 8, 4, 4);
		
	}
}
