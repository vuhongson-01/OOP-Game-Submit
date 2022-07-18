package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.GamePanel;

public class Boss1 extends Entity{
	String name;
	
	public Boss1(GamePanel gp, int hard, int firstLocX, int firstLocY) {
		this.gp = gp;		
		selfArea = new int[4];
		damageArea = new int[4];
		name = "Warg";
		setDefaultValue(hard, firstLocX, firstLocY);
		getboss1Image();
	}
	
	public void setDefaultValue(int hard, int firstLocX, int firstLocY) {
//		location for draw image
		initX = firstLocX;
		initY = firstLocY;
		x = initX;
		y = initY;	
		
		if(hard == 1) {
			defaultHP = 200;
			attack = 15;
			speed = 1;
		}
		else {
			defaultHP = 250;
			attack = 20;
			speed = 2;
		}
		attackRange = 3*TILE_SIZE/2;
		hp = defaultHP;
		attackSpeed = 120;
//		action = "down";
	}
	
	public void getboss1Image() { 
		// scale x2 tileSize
		for(int i = 0; i < 4; i++) {
			right[i] = setup("/boss/boss1_right_"+(i+1), TILE_SIZE * 2, TILE_SIZE * 2);
			left[i] = setup("/boss/boss1_left_"+(i+1), TILE_SIZE * 2, TILE_SIZE * 2);
			up[i] = setup("/boss/boss1_up_"+(i+1), TILE_SIZE * 2, TILE_SIZE * 2);
			down[i] = setup("/boss/boss1_down_"+(i+1), TILE_SIZE * 2, TILE_SIZE * 2);
		}
		for(int i = 0; i < 5; i++) {
			rightAttack[i] = setup("/boss/boss1_right_attack_"+(i+1), TILE_SIZE * 4, TILE_SIZE * 2);
			leftAttack[i] = setup("/boss/boss1_left_attack_"+(i+1), TILE_SIZE * 4, TILE_SIZE * 2);
			die[i] = setup("/boss/boss1_die_"+(i+1), TILE_SIZE * 2, TILE_SIZE * 2);
		}
		gate[0] = setup("/boss/door1", TILE_SIZE*2, TILE_SIZE*2);
		gate[1] = setup("/boss/door2", TILE_SIZE*2, TILE_SIZE*2);
		gate[2] = setup("/boss/door3", TILE_SIZE*2, TILE_SIZE*2);
		
	}
	
	public void update(int [][] map) {
		if(hp > 0) {
			f++;
			selfArea[0] = x + 16;
			selfArea[1] = y + 10;
			selfArea[2] = x + 64;
			selfArea[3] = y + 76;
			if((initX - x)*(initX - x) + (initY - y)*(initY - y) < 100 * TILE_SIZE * TILE_SIZE) {
				if (!attacking) {
					if(!provoked) {
						if (f % 120 == 0 || isBlocked(map, x, y)) { // go around square after every 2s or blocked by topographic
							direction = (direction + 90) % 360;
							f = 0;
						}
						
						if (direction == 0) {
							x += speed;	
						}
						else if (direction == 90)
							y -= speed;
						else if (direction == 180)
							x -= speed;
						else y += speed;
					}
				}else {
					f_attack++;
				}
			}else {
				x = initX;
				y = initY;
			}
			
//			System.out.printf("monster: " + selfArea[0] + "-----" + selfArea[1] + "------" + selfArea[2] + "-----" + selfArea[3]);
			
		}else {
			f_die++;
			if(f_die == 121) gp.music.playSE(18);
		}
			

	}

	private boolean isBlocked(int[][] map, int x, int y) {
		
		if (direction == 180) {
			if (map[(y + TILE_SIZE / 2) / TILE_SIZE][(x - speed) / TILE_SIZE] != 1 &&
				map[(y + TILE_SIZE * 2) / TILE_SIZE][(x - speed) / TILE_SIZE] != 1) return true;
		}
		if (direction == 0){
			if (map[(y + TILE_SIZE/2) / TILE_SIZE][(x + TILE_SIZE * 2 + speed) / TILE_SIZE] != 1 &&
				map[(y + TILE_SIZE * 2) / TILE_SIZE][(x + TILE_SIZE * 2 + speed) / TILE_SIZE] != 1) return true;
		}
		if (direction == 90) {
			if (map[(y + TILE_SIZE / 2 - speed) / TILE_SIZE][x / TILE_SIZE] != 1 &&
				map[(y + TILE_SIZE / 2 - speed) / TILE_SIZE][(x + TILE_SIZE * 2) / TILE_SIZE] != 1) return true;
		}
		if (direction == 270) {
			if (map[(y + TILE_SIZE * 2 + speed) / TILE_SIZE][x / TILE_SIZE] != 1 &&
				map[(y + TILE_SIZE * 2 + speed) / TILE_SIZE][(x + TILE_SIZE * 2) / TILE_SIZE] != 1) return true;
		}
		return false;
	}
	
	public void draw(Graphics2D graphics2d) {
		if(hp > 0) {
			graphics2d.drawImage(gate[0], 29*TILE_SIZE - gp.worldx, 13*TILE_SIZE - gp.worldy, null);
			drawHealthBar(graphics2d);	
			if (!attacking) {
				if (direction == 180) {			
					if (f % 60 < 15) {
						graphics2d.drawImage(left[0], x-gp.worldx, y-gp.worldy, null);
					}
					else if (f % 60 < 23){
						graphics2d.drawImage(left[1], x-gp.worldx, y-gp.worldy, null);
					}	
					else if (f % 60 < 45){
						graphics2d.drawImage(left[2], x-gp.worldx, y-gp.worldy, null);
					}
					else {
						graphics2d.drawImage(left[3], x-gp.worldx, y-gp.worldy, null);
					}
				}
				else if (direction == 0){

					if (f % 60 < 15) {
						graphics2d.drawImage(right[0], x-gp.worldx, y-gp.worldy, null);
					}
					else if (f % 60 < 23){
						graphics2d.drawImage(right[1], x-gp.worldx, y-gp.worldy, null);
					}	
					else if (f % 60 < 45){
						graphics2d.drawImage(right[2], x-gp.worldx, y-gp.worldy, null);
					}
					else {
						graphics2d.drawImage(right[3], x-gp.worldx, y-gp.worldy, null);
					}
				}
				else if (direction == 90) {

					if (f % 60 < 15) {
						graphics2d.drawImage(up[0], x-gp.worldx, y-gp.worldy, null);
					}
					else if (f % 60 < 23){
						graphics2d.drawImage(up[1], x-gp.worldx, y-gp.worldy, null);
					}	
					else if (f % 60 < 45){
						graphics2d.drawImage(up[2], x-gp.worldx, y-gp.worldy, null);
					}
					else {
						graphics2d.drawImage(up[3], x-gp.worldx, y-gp.worldy, null);
					}
				}
				else if (direction == 270) {
					if (f % 60 < 15) {
						graphics2d.drawImage(down[0], x-gp.worldx, y-gp.worldy, null);
					}
					else if (f % 60 < 23){
						graphics2d.drawImage(down[1], x-gp.worldx, y-gp.worldy, null);
					}	
					else if (f % 60 < 45){
						graphics2d.drawImage(down[2], x-gp.worldx, y-gp.worldy, null);
					}
					else {
						graphics2d.drawImage(down[3], x-gp.worldx, y-gp.worldy, null);
					}
				}			
			}
			else {
				if (directionAttack == 0) {
					if (f_attack % attackSpeed < attackSpeed/6) {
						graphics2d.drawImage(rightAttack[0], x-gp.worldx, y-gp.worldy, null);
					}
					else if (f_attack % attackSpeed < attackSpeed/3){
						graphics2d.drawImage(rightAttack[1], x-gp.worldx, y-gp.worldy, null);
					}	
					else if (f_attack % attackSpeed < attackSpeed/2){
						graphics2d.drawImage(rightAttack[2], x-gp.worldx, y-gp.worldy, null);
					}
					else if (f_attack % attackSpeed < attackSpeed*2/3){
						damageArea[0] = x + TILE_SIZE * 2 - 10;
						damageArea[1] = y + TILE_SIZE - 10;
						damageArea[2] = x + TILE_SIZE * 2 + 30;
						damageArea[3] = y + TILE_SIZE + 30;
						if (f_attack % attackSpeed == attackSpeed*7/12) gp.bossTakeAttack(damageArea, attack);
						graphics2d.drawImage(rightAttack[3], x-gp.worldx, y-gp.worldy, null);
					}
					else {
						graphics2d.drawImage(rightAttack[4], x-gp.worldx, y-gp.worldy, null);
					}		
				}
				if (directionAttack == 180) {
					
					if (f_attack % attackSpeed < attackSpeed/6) {
						graphics2d.drawImage(leftAttack[0], x - TILE_SIZE * 2-gp.worldx, y-gp.worldy, null);
					}
					else if (f_attack % attackSpeed < attackSpeed/3){
						graphics2d.drawImage(leftAttack[1], x - TILE_SIZE * 2-gp.worldx, y-gp.worldy, null);
					}	
					else if (f_attack % attackSpeed < attackSpeed/2){
						graphics2d.drawImage(leftAttack[2], x - TILE_SIZE * 2-gp.worldx, y-gp.worldy, null);
					}
					else if (f_attack % attackSpeed < attackSpeed*2/3){
						damageArea[0] = x - TILE_SIZE * 2 + 10;
						damageArea[1] = y + TILE_SIZE - 10;
						damageArea[2] = x + 30;
						damageArea[3] = y + TILE_SIZE + 30;
						if (f_attack % attackSpeed == attackSpeed*7/12) gp.bossTakeAttack(damageArea, attack);
						graphics2d.drawImage(leftAttack[3], x - TILE_SIZE * 2-gp.worldx, y-gp.worldy, null);
					}
					else {
						graphics2d.drawImage(leftAttack[4], x - TILE_SIZE * 2-gp.worldx, y-gp.worldy, null);
					}	
				}
			}
		}else {
			if(f_die < 15) graphics2d.drawImage(die[0], x - gp.worldx,  y - gp.worldy,  null);
			else if(f_die < 30) graphics2d.drawImage(die[1], x - gp.worldx,  y - gp.worldy,  null);
			else if(f_die < 40) graphics2d.drawImage(die[2], x - gp.worldx,  y - gp.worldy,  null);
			else if(f_die < 50) graphics2d.drawImage(die[3], x - gp.worldx,  y - gp.worldy,  null);
			else if(f_die < 60) graphics2d.drawImage(die[4], x - gp.worldx,  y - gp.worldy,  null);
			else if(f_die < 120) graphics2d.drawImage(gate[0], 29*TILE_SIZE - gp.worldx, 13*TILE_SIZE - gp.worldy, null);
			else if(f_die < 150) {
				gp.music.playSE(18);
				graphics2d.drawImage(gate[1], 29*TILE_SIZE - gp.worldx, 13*TILE_SIZE - gp.worldy, null);
			}
			else graphics2d.drawImage(gate[2], 29*TILE_SIZE - gp.worldx, 13*TILE_SIZE - gp.worldy, null);
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
