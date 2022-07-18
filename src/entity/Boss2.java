package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.GamePanel;

public class Boss2 extends Entity{
	String name;
	
	public Boss2(GamePanel gp, int hard, int firstLocX, int firstLocY) {
		this.gp = gp;
		name = "Mountain Orge";
		selfArea = new int[4];
		damageArea = new int[4];
		setDefaultValue(hard, firstLocX, firstLocY);
		getboss2Image();
		
	}
	
	public void setDefaultValue(int hard, int firstLocX, int firstLocY) {
//		location for draw image
		initX = firstLocX;
		initY = firstLocY;
		x = initX;
		y = initY;	
		
		if(hard == 1) {
			defaultHP = 400;
			attack = 30;
			speed = 1;
		}
		else {
			defaultHP = 450;
			attack = 45;
			speed = 2;
		}
		hp = defaultHP;
		attackRange = 2 * TILE_SIZE;
		attackSpeed = 60;
//		action = "down";
	}
	
	public void getboss2Image() { 
		// scale x2 tileSize
		for(int i = 0; i < 4; i++) {
			right[i] = setup("/boss/boss2_right_"+(i+1), TILE_SIZE * 4, TILE_SIZE * 4);
			left[i] = setup("/boss/boss2_left_"+(i+1), TILE_SIZE * 4, TILE_SIZE * 4);
		}
		for(int i = 0; i < 3; i++) {
			rightAttack[i] = setup("/boss/boss2_attack_right_"+(i+1), TILE_SIZE * 4, TILE_SIZE * 4);
			leftAttack[i] = setup("/boss/boss2_attack_left_"+(i+1), TILE_SIZE * 4, TILE_SIZE * 4);
		}
		for(int i = 0; i < 4; i++) 	gate[i] = setup("/boss/gate_" + (i+1), TILE_SIZE * 2, TILE_SIZE * 2);
		
		die[0] = setup("/boss/boss2_die_left", TILE_SIZE * 4, TILE_SIZE * 4);
		die[1] = setup("/boss/boss2_die_right", TILE_SIZE * 4, TILE_SIZE * 4);
	
	}
	
	public void update(int [][] map) {
		if(hp > 0) {
			f++;
			selfArea[0] = x;
			selfArea[1] = y;
			selfArea[2] = x + 3 * TILE_SIZE;
			selfArea[3] = y + 4 * TILE_SIZE;
			if((initX - x)*(initX - x) + (initY - y)*(initY - y) < 100 * TILE_SIZE * TILE_SIZE) {
				if (!attacking) {
					if(!provoked) {
						if (f % 240 == 0 || isBlocked(map, x, y)) { // go around square after every 4s or blocked by topographic
							direction = (direction + 180) % 360;
							f = 0;
						}				
						if (direction == 0) {
							x += speed;	
						}else if (direction == 180)
							x -= speed;
					}
				}else {
					f_attack++;
				}
			}else {
				x = initX;
				y = initY;
			}
			
		}else {
			f_die++;
			if(f_die == 40) gp.music.playSE(10);
		}

	}

private boolean isBlocked(int[][] map, int x, int y) {
		
		if (direction == 180) {
			if (map[(y + TILE_SIZE * 2) / TILE_SIZE][(x - speed) / TILE_SIZE] != 1 &&
				map[(y + TILE_SIZE * 4) / TILE_SIZE][(x - speed) / TILE_SIZE] != 1) return true;
		}
		if (direction == 0){
			if (map[(y + TILE_SIZE * 2) / TILE_SIZE][(x + TILE_SIZE * 4 + speed) / TILE_SIZE] != 1 &&
				map[(y + TILE_SIZE * 4) / TILE_SIZE][(x + TILE_SIZE * 4 + speed) / TILE_SIZE] != 1) return true;
		}
		return false;
	}
	
	
	public void draw(Graphics2D graphics2d) {
		if(hp > 0) {
			drawHealthBar(graphics2d);		
			if (!attacking) {
				if (direction == 180) {
					if (f % 60 < 15) {
						graphics2d.drawImage(left[0], x - gp.worldx, y - gp.worldy, null);
					}
					else if (f % 60 < 30){
						graphics2d.drawImage(left[1], x - gp.worldx, y - gp.worldy, null);
					}	
					else if(f % 60 < 45 ) {
						graphics2d.drawImage(left[2], x - gp.worldx, y - gp.worldy, null);
					}
					else {
						graphics2d.drawImage(left[3], x - gp.worldx, y - gp.worldy, null);
					}
				}
				else if (direction == 0){
					if (f % 60 < 15) {
						graphics2d.drawImage(right[0], x - gp.worldx, y - gp.worldy, null);
					}
					else if (f % 60 < 30){
						graphics2d.drawImage(right[1], x - gp.worldx, y - gp.worldy, null);
					}	
					else if (f % 60 < 45) {
						graphics2d.drawImage(right[2], x - gp.worldx, y - gp.worldy, null);
					}
					else {
						graphics2d.drawImage(right[3], x - gp.worldx, y - gp.worldy, null);
					}
				}
			}else {
				if (directionAttack == 0) {
					if (f_attack % attackSpeed < attackSpeed/3)	graphics2d.drawImage(rightAttack[0], x - gp.worldx, y - gp.worldy, null);
					else if (f_attack % attackSpeed < attackSpeed*2/3){
						damageArea[0] = x;
						damageArea[1] = y;
						damageArea[2] = x + 5 * TILE_SIZE;
						damageArea[3] = y + 4 * TILE_SIZE ;
						graphics2d.drawImage(rightAttack[1], x - gp.worldx, y - gp.worldy, null);
						if(f_attack % attackSpeed == attackSpeed*7/12) gp.bossTakeAttack(damageArea, attack);
					}	
					else graphics2d.drawImage(rightAttack[2], x - gp.worldx, y - gp.worldy, null);				
				}
				else if (directionAttack == 180) {
					if (f_attack % attackSpeed < attackSpeed/3)	graphics2d.drawImage(leftAttack[0], x - gp.worldx, y - gp.worldy, null);
					else if (f_attack % attackSpeed < attackSpeed*2/3){
						damageArea[0] = x - TILE_SIZE;
						damageArea[1] = y;
						damageArea[2] = x + 4 * TILE_SIZE;
						damageArea[3] = y + 4 * TILE_SIZE ;
						graphics2d.drawImage(leftAttack[1], x - gp.worldx, y - gp.worldy, null);
						if(f_attack % attackSpeed == attackSpeed*7/12) gp.bossTakeAttack(damageArea, attack);	
					}	
					else graphics2d.drawImage(leftAttack[2], x - gp.worldx, y - gp.worldy, null);
				}
			}
		}else {
			if(f_die % 40 < 10) graphics2d.drawImage(gate[0], 27 * TILE_SIZE - gp.worldx, 1 * TILE_SIZE - gp.worldy, null);
			else if(f_die % 40 < 20) graphics2d.drawImage(gate[1], 27 * TILE_SIZE - gp.worldx, 1 * TILE_SIZE - gp.worldy, null);
			else if(f_die % 40 < 30) graphics2d.drawImage(gate[2], 27 * TILE_SIZE - gp.worldx, 1 * TILE_SIZE - gp.worldy, null);
			else graphics2d.drawImage(gate[3], 27 * TILE_SIZE - gp.worldx, 1 * TILE_SIZE - gp.worldy, null);
			if(f_die < 20) {
				if(direction == 0) graphics2d.drawImage(die[1], x - gp.worldx, y - gp.worldy, null);
				else if(direction == 180) graphics2d.drawImage(die[0], x - gp.worldx, y - gp.worldy, null);
			}
			else if(f_die > 30 && f_die < 40) {
				if(direction == 0) graphics2d.drawImage(die[1], x - gp.worldx, y - gp.worldy, null);
				else if(direction == 180) graphics2d.drawImage(die[0], x - gp.worldx, y - gp.worldy, null);
			}
		}	
	}


	private void drawHealthBar(Graphics2D graphics2d) {
		graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 12F));
		graphics2d.setColor(Color.black);
		
		graphics2d.drawString(name, x+20-gp.worldx, y-10-gp.worldy);
		
		graphics2d.fillRoundRect(x+20-gp.worldx, y-5-gp.worldy, gp.tileSize * 2 + 4, 10, 4, 4);
		graphics2d.setColor(Color.red);
		graphics2d.fillRoundRect(x+22-gp.worldx, y-4-gp.worldy, (int)(hp*1.0/defaultHP * gp.tileSize * 2), 8, 4, 4);
		
	}
}