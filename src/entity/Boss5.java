package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.GamePanel;

public class Boss5 extends Entity{
	String name;
	
	public Boss5(GamePanel gp, int hard, int firstLocX, int firstLocY) {
		this.gp = gp;
		
		name = "Ghost";
		setDefaultValue(hard, firstLocX, firstLocY);
		selfArea = new int[4];
		damageArea = new int[4];
		getboss5Image();
	}
	
	public void setDefaultValue(int hard, int firstLocX, int firstLocY) {
//		location for draw image
		initX = firstLocX;
		initY = firstLocY;
		x = initX;
		y = initY;	
		
		if(hard == 1) {
			defaultHP = 500;
			attack = 50;
			attackSpeed = 120;
			speed = 1;
		}
		else {
			defaultHP = 600;
			attack = 65;
			attackSpeed = 60;
			speed = 2;
		}
		hp = defaultHP;
		attackRange = 2 * TILE_SIZE;
	
		runSpeed = 2;
//		action = "down";
	}

	public void getboss5Image() { 
		// scale x2 tileSize
		for (int i = 0; i < 4; i++){
			right[i] = setup("/boss/Boss5_right_"+(i+1),TILE_SIZE * 4,TILE_SIZE * 4);
			left[i] = setup("/boss/Boss5_left_" + (i+1),TILE_SIZE * 4,TILE_SIZE * 4);
			up[i] = setup("/boss/boss5_left_" + (i+1),TILE_SIZE * 4,TILE_SIZE * 4);
			down[i] = setup("/boss/boss5_right_" + (i+1),TILE_SIZE * 4,TILE_SIZE * 4);
			rightAttack[i] = setup("/boss/Boss5_attack_right_" + (i+1),TILE_SIZE * 4,TILE_SIZE * 4);
			leftAttack[i] = setup("/boss/Boss5_attack_left_" + (i+1),TILE_SIZE * 4,TILE_SIZE * 4);
		}
		
		for(int i = 0; i < 4; i++) 	gate[i] = setup("/boss/gate_" + (i+1), TILE_SIZE * 2, TILE_SIZE * 2);
		
		die[0] = setup("/boss/Boss5_died_1",TILE_SIZE * 4,TILE_SIZE * 4);
		die[1] = setup("/boss/Boss5_died_2",TILE_SIZE * 4,TILE_SIZE * 4);
		die[2] = setup("/boss/Boss5_died_3",TILE_SIZE * 4,TILE_SIZE * 4);
	}
	
	public void update(int [][] map) {
		if(hp > 0) {
			f++;
			selfArea[0] = x;
			selfArea[1] = y;
			selfArea[2] = x + 4 * TILE_SIZE;
			selfArea[3] = y + 4 * TILE_SIZE;
			if((initX - x)*(initX - x) + (initY - y)*(initY - y) < 100 * TILE_SIZE * TILE_SIZE) {
				if (!attacking) {
					if(!provoked) {
						if (f % 240 == 0 || isBlocked(map, x, y)) { // go around square after every 2s or blocked by topographic
							direction = (direction + 90) % 360;
							f = 0;
						}
						
						if (direction == 0) 
							x += speed;							
						else if (direction == 90)
							y -= speed;
						else if (direction == 180)
							x -= speed;
						else if (direction == 270) y += speed;
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
			if(f_die == 90) gp.music.playSE(10);
		}
	}

	private boolean isBlocked(int[][] map, int x, int y) {
		
		if (direction == 180) {
			if (map[(y +TILE_SIZE / 2) /TILE_SIZE][(x - speed) /TILE_SIZE] == 0 ||
				map[(y +TILE_SIZE * 4) /TILE_SIZE][(x - speed) /TILE_SIZE] == 0) return true;
		}
		if (direction == 0){
			if (map[(y +TILE_SIZE/2) /TILE_SIZE][(x + TILE_SIZE * 4 + speed) /TILE_SIZE] == 0 ||
				map[(y +TILE_SIZE * 4) /TILE_SIZE][(x +TILE_SIZE * 4 + speed) /TILE_SIZE] == 0) return true;
		}
		if (direction == 90) {
			if (map[(y +TILE_SIZE / 2 - speed) /TILE_SIZE][x /TILE_SIZE] == 0 ||
				map[(y +TILE_SIZE / 2 - speed) /TILE_SIZE][(x +TILE_SIZE * 4) /TILE_SIZE] == 0) return true;
		}
		if (direction == 270) {
			if (map[(y +TILE_SIZE * 4 + speed) /TILE_SIZE][x /TILE_SIZE] == 0 ||
				map[(y +TILE_SIZE * 4 + speed) /TILE_SIZE][(x +TILE_SIZE * 4) /TILE_SIZE] == 0) return true;
		}
		return false;

	}

	public void draw(Graphics2D graphics2d) {
		if(hp > 0) {
			drawHealthBar(graphics2d);
			if (!attacking) {
				if (direction == 180) {
					if (f % 60 < 15) {
						graphics2d.drawImage(left[0], x-gp.worldx, y-gp.worldy, null);
					}
					else if (f % 60 < 30){
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
					else if (f % 60 < 30 ){
						graphics2d.drawImage(right[1], x-gp.worldx, y-gp.worldy, null);
					}	
					else if (f % 60 < 45){
						graphics2d.drawImage(right[2], x-gp.worldx, y-gp.worldy, null);
					} 
					else {
						graphics2d.drawImage(right[3], x-gp.worldx, y-gp.worldy, null);
					}
				}
				else if(direction == 90) {
					if (f % 60 < 15) {
						graphics2d.drawImage(up[0], x-gp.worldx, y-gp.worldy, null);
					}
					else if (f % 60 < 30 ){
						graphics2d.drawImage(up[1], x-gp.worldx, y-gp.worldy, null);
					}	
					else if (f % 60 < 45){
						graphics2d.drawImage(up[2], x-gp.worldx, y-gp.worldy, null);
					} 
					else {
						graphics2d.drawImage(up[3], x-gp.worldx, y-gp.worldy, null);
					}
				}
				else if(direction == 270){
					if (f % 60 < 15) {
						graphics2d.drawImage(down[0], x-gp.worldx, y-gp.worldy, null);
					}
					else if (f % 60 < 30 ){
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
					if (f_attack % attackSpeed < attackSpeed/5) {
						graphics2d.drawImage(rightAttack[0], x-gp.worldx, y-gp.worldy, null);
					}
					else if (f_attack % attackSpeed < attackSpeed*2/5){
						graphics2d.drawImage(rightAttack[1], x-gp.worldx, y-gp.worldy, null);
					}	
					else if (f_attack % attackSpeed < attackSpeed*3/5){
						damageArea[0] = x + 2 * TILE_SIZE;
						damageArea[1] = y;
						damageArea[2] = x + 4 * TILE_SIZE;
						damageArea[3] = y + 4 * TILE_SIZE ;
						graphics2d.drawImage(rightAttack[2], x-gp.worldx, y-gp.worldy, null);
						if(f_attack % attackSpeed == attackSpeed*7/12) gp.bossTakeAttack(damageArea, attack);
					}
					else {
						graphics2d.drawImage(rightAttack[3], x-gp.worldx, y-gp.worldy, null);
					}	
				}
				if (directionAttack == 180) {
					if (f_attack % attackSpeed < attackSpeed/5) {
						graphics2d.drawImage(leftAttack[0], x-gp.worldx , y-gp.worldy, null);
					}
					else if (f_attack % attackSpeed < attackSpeed*2/5){
						graphics2d.drawImage(leftAttack[1], x-gp.worldx , y-gp.worldy, null);
					}	
					else if (f_attack % attackSpeed < attackSpeed*3/5){
						damageArea[0] = x;
						damageArea[1] = y;
						damageArea[2] = x + 2 * TILE_SIZE;
						damageArea[3] = y + 4 * TILE_SIZE ;
						graphics2d.drawImage(leftAttack[2], x-gp.worldx , y-gp.worldy, null);
						if(f_attack % attackSpeed == attackSpeed*7/12) gp.bossTakeAttack(damageArea, attack);
					}
					else{
						graphics2d.drawImage(leftAttack[3], x-gp.worldx , y-gp.worldy, null);
					}
				}
			}
		}else {
			if(f_die % 40 < 10) graphics2d.drawImage(gate[0], 24 * TILE_SIZE - gp.worldx, 22 * TILE_SIZE - gp.worldy, null);
			else if(f_die % 40 < 20) graphics2d.drawImage(gate[1], 24 * TILE_SIZE - gp.worldx, 22 * TILE_SIZE - gp.worldy, null);
			else if(f_die % 40 < 30) graphics2d.drawImage(gate[2], 24 * TILE_SIZE - gp.worldx, 22 * TILE_SIZE - gp.worldy, null);
			else graphics2d.drawImage(gate[3], 24 * TILE_SIZE - gp.worldx, 22 * TILE_SIZE - gp.worldy, null);
			
			if(f_die < 30) graphics2d.drawImage(die[0], x-gp.worldx , y-gp.worldy, null);
			else if(f_die < 60) graphics2d.drawImage(die[1], x-gp.worldx , y-gp.worldy, null);
			else if(f_die < 90) graphics2d.drawImage(die[2], x-gp.worldx , y-gp.worldy, null);
		}
	}
	public void drawHealthBar(Graphics2D graphics2d) {
		graphics2d.setFont(graphics2d.getFont().deriveFont(Font.PLAIN, 12F));
		graphics2d.setColor(Color.white);
		
		graphics2d.drawString(name, x-gp.worldx+35, y-gp.worldy-10);
		
		graphics2d.fillRoundRect(x-gp.worldx-2, y-gp.worldy-5,TILE_SIZE * 2 + 4, 10, 4, 4);
		graphics2d.setColor(Color.red);
		graphics2d.fillRoundRect(x-gp.worldx, y-gp.worldy-4, (int)(hp*1.0/defaultHP * TILE_SIZE * 2), 8, 4, 4);
		
	}
}
	
	
