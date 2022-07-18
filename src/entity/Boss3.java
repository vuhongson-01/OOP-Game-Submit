package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.GamePanel;

public class Boss3 extends Entity{
	String name;
	
	public Boss3(GamePanel gp, int hard, int firstLocX, int firstLocY) {
		this.gp = gp;		
		selfArea = new int[4];
		damageArea = new int[4];
		name = "Phoenix";
		setDefaultValue(hard, firstLocX, firstLocY);
		getboss3Image();
	}
	
	public void setDefaultValue(int hard, int firstLocX, int firstLocY) {
//		location for draw image
		initX = firstLocX;
		initY = firstLocY;
		x = initX;
		y = initY;	
		
		if(hard == 1) {
			defaultHP = 200;
			attack = 30;
			attackSpeed = 60;
			speed = 1;
		}
		else {
			defaultHP = 250;
			attack = 45;
			attackSpeed = 40;
			speed = 2;
		}
		attackRange = 3*TILE_SIZE / 2;
		hp = defaultHP;
//		action = "down";
	}
	
	public void getboss3Image() { 
		// scale x2 tileSize
		for (int i = 0; i < 4; i++) {
			right[i] = setup("/boss/boss3_right_" + (i+1), TILE_SIZE * 3, TILE_SIZE * 3);
			left[i] = setup("/boss/boss3_left_" + (i+1), TILE_SIZE * 3, TILE_SIZE * 3);
			up[i] = setup("/boss/boss3_up_" + (i+1), TILE_SIZE * 3, TILE_SIZE * 3);
			down[i] = setup("/boss/boss3_down_" + (i+1), TILE_SIZE * 3, TILE_SIZE * 3);
			rightAttack[i] = setup("/boss/boss3_right_attack_" + (i+1), (TILE_SIZE * 4), TILE_SIZE * 3);
			leftAttack[i] = setup("/boss/boss3_left_attack_" + (i+1), (TILE_SIZE * 4), TILE_SIZE * 3);
			gate[i] = setup("/boss/gate_" + (i+1), TILE_SIZE * 2, TILE_SIZE * 2);
		}
		die[1] = setup("/boss/boss3_died_1", (TILE_SIZE * 3), TILE_SIZE * 3);
		die[2] = setup("/boss/boss3_died_2", (TILE_SIZE * 3), TILE_SIZE * 3);
		die[3] = setup("/boss/boss3_died_3", (TILE_SIZE * 2), TILE_SIZE * 2);
	}
	
	public void update(int [][] map) {	
		if(hp > 0) {
			f++;
			selfArea[0] = x;
			selfArea[1] = y;
			selfArea[2] = x + 3*TILE_SIZE;
			selfArea[3] = y + 3*TILE_SIZE;
			if((initX - x)*(initX - x) + (initY - y)*(initY - y) < 100 * TILE_SIZE * TILE_SIZE) {
				if (!attacking) {
					if(!provoked) {
						if (f % 120 == 0) { // go around square after every 2s or blocked by topographic
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
		}else {
			f_die++;
			if(f_die == 40) gp.music.playSE(10);
			selfArea[0] = -1;
			selfArea[1] = -1;
			selfArea[2] = -1;
			selfArea[3] = -1;	
		}
	}

	public void draw(Graphics2D graphics2d) {
		if(hp > 0) {
			drawHealthBar(graphics2d);
	
			if (!attacking) {
				if (direction == 180) {
					if (f % 60 < 15) {
						graphics2d.drawImage(left[0], x-gp.worldx, y - gp.worldy, null);
					}else if (f % 60 < 30){
						graphics2d.drawImage(left[1], x-gp.worldx, y - gp.worldy, null);
					}else if (f % 60 < 45){
						graphics2d.drawImage(left[2], x-gp.worldx, y - gp.worldy, null);
					}else {
						graphics2d.drawImage(left[3], x-gp.worldx, y - gp.worldy, null);
					}
				}else if (direction == 0){
					if (f % 60 < 15) {
						graphics2d.drawImage(right[0], x-gp.worldx, y - gp.worldy, null);
					}else if (f % 60 < 23){
						graphics2d.drawImage(right[1], x-gp.worldx, y - gp.worldy, null);
					}else if (f % 60 < 45){
						graphics2d.drawImage(right[2], x-gp.worldx, y - gp.worldy, null);
					}else {
						graphics2d.drawImage(right[3], x-gp.worldx, y - gp.worldy, null);
					}
				}else if (direction == 90) {
					if (f % 60 < 15) {
						graphics2d.drawImage(up[0], x - gp.worldx, y - gp.worldy, null);
					}else if (f % 60 < 23){
						graphics2d.drawImage(up[1], x - gp.worldx, y - gp.worldy, null);
					}else if (f % 60 < 45){
						graphics2d.drawImage(up[2], x - gp.worldx, y - gp.worldy, null);
					}else {
						graphics2d.drawImage(up[3], x - gp.worldx, y - gp.worldy, null);
					}
				}else if (direction == 270) {
					if (f % 60 < 15) {
						graphics2d.drawImage(down[0], x - gp.worldx, y - gp.worldy, null);
					}else if (f % 60 < 23){
						graphics2d.drawImage(down[1], x - gp.worldx, y - gp.worldy, null);
					}else if (f % 60 < 45){
						graphics2d.drawImage(down[2], x - gp.worldx, y - gp.worldy, null);
					}else {
						graphics2d.drawImage(down[3], x - gp.worldx, y - gp.worldy, null);
					}
				}			
			}else {
				if (directionAttack == 0) {
					if (f_attack % attackSpeed < attackSpeed/4) {
						graphics2d.drawImage(rightAttack[0], x - gp.worldx, y - gp.worldy, null);
					}else if (f_attack % attackSpeed < attackSpeed/2){
						graphics2d.drawImage(rightAttack[1], x - gp.worldx, y - gp.worldy, null);
					}else if (f_attack % attackSpeed < attackSpeed*3/4){
						damageArea[0] = x + TILE_SIZE * 2;
						damageArea[1] = y;
						damageArea[2] = x + TILE_SIZE * 4;
						damageArea[3] = y + 3 * TILE_SIZE;	
						if (f_attack % attackSpeed == attackSpeed*2/3) gp.bossTakeAttack(damageArea, attack);
						graphics2d.drawImage(rightAttack[2], x - gp.worldx, y - gp.worldy, null);
					}else {			
						graphics2d.drawImage(rightAttack[3], x - gp.worldx, y - gp.worldy, null);
					}
				}else if (directionAttack == 180) {
					if (f_attack % attackSpeed < attackSpeed/4) {
						graphics2d.drawImage(leftAttack[0], x - gp.worldx , y - gp.worldy, null);
					}else if (f_attack % attackSpeed < attackSpeed/2){
						graphics2d.drawImage(leftAttack[1], x - gp.worldx , y - gp.worldy, null);
					}else if (f_attack % attackSpeed < attackSpeed*3/4){
						damageArea[0] = x;
						damageArea[1] = y;
						damageArea[2] = x + 2 * TILE_SIZE;
						damageArea[3] = y + 3 * TILE_SIZE;
						if (f_attack % attackSpeed == attackSpeed*2/3) gp.bossTakeAttack(damageArea, attack);
						graphics2d.drawImage(leftAttack[2], x - gp.worldx , y -gp.worldy, null);
					}else {
						graphics2d.drawImage(leftAttack[3], x - gp.worldx , y - gp.worldy, null);
					}
				}
			}
		}else {		
			if(f_die % 40 < 10) graphics2d.drawImage(gate[0], 25 * TILE_SIZE - gp.worldx, 21 * TILE_SIZE - gp.worldy, null);
			else if(f_die % 40 < 20) graphics2d.drawImage(gate[1], 25 * TILE_SIZE - gp.worldx, 21 * TILE_SIZE - gp.worldy, null);
			else if(f_die % 40 < 30) graphics2d.drawImage(gate[2], 25 * TILE_SIZE - gp.worldx, 21 * TILE_SIZE - gp.worldy, null);
			else graphics2d.drawImage(gate[3], 25 * TILE_SIZE - gp.worldx, 21 * TILE_SIZE - gp.worldy, null);
		
			if(f_die < 10) graphics2d.drawImage(die[1], x - gp.worldx, y - gp.worldy, null);
			else if(f_die < 20) graphics2d.drawImage(die[2],  x - gp.worldx,  y - gp.worldy,  null);
			else if(f_die < 30) graphics2d.drawImage(die[3],  x - gp.worldx,  y - gp.worldy,  null);
		}
	}

	private void drawHealthBar(Graphics2D graphics2d) {
		graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 12F));
		graphics2d.setColor(Color.white);
	
		graphics2d.drawString(name, x-gp.worldx, y-10-gp.worldy);
	
		graphics2d.fillRoundRect(x-22-gp.worldx, y-5-gp.worldy, gp.tileSize * 2 + 4, 10, 4, 4);
		graphics2d.setColor(Color.red);
		graphics2d.fillRoundRect(x-20-gp.worldx, y-4-gp.worldy, (int)(hp*1.0/defaultHP * gp.tileSize * 2), 8, 4, 4);
	}
}
