package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.GamePanel;
import main.KeyHandler;
import main.GameInterface;

public class SpearKnight extends Entity implements GameInterface{
	KeyHandler keyHandler;
	private final int defaultMP = 100;
	BufferedImage pointboard, skillbar, impossible;
	
	private int skill1CD = 0; //pro-active skill - 10s CD
	private int skill1EffectiveTime = 5*60; //during 5s * 60FPS effective
	private int skill1MP = 40; //skill1 need 40 MP
	public int defaultShield = 100;
	
	private int skill2CD = 0; //passive skill - 7s CD
	private int skill2EffectiveTime = 2*60; //during 3s effective
	private int skill2MP = 40; //skill2 need 40 MP
	private int momentX, momentY;
	
	private int skill3CD = 0;  //passive skill - 12s CD
	private int skill3EffectiveTime = 2*60; //during 5s * 60FPS effective
	private int skill3MP = 50; //skill3 need 50 MP
	
	public SpearKnight(GamePanel gp, KeyHandler keyHandler, int firstLocationX, int firstLocationY) {
		this.gp = gp;
		this.keyHandler = keyHandler;
		
		setDefaultValue(firstLocationX, firstLocationY);
		getPlayerImage();
	}
	
	
	public void setDefaultValue(int firstLocationX, int firstLocationY) {
//		location for draw image
		x = firstLocationX;
		y = firstLocationY;

		selfArea = new int [4];
		damageArea = new int [4];
		
		selfArea[0] = x + gp.worldx;
		selfArea[1] = y + gp.worldy;
		selfArea[2] = x + gp.worldx + TILE_SIZE;
		selfArea[3] = y + gp.worldy + TILE_SIZE;
				
		hp = defaultHP;
		mp = defaultMP;
		shield = 0;
		attack = 35;
		attackSpeed = 60;
		defense = 10;
		speed = 3;
		
		action = "down";
	}

	
	public void getPlayerImage() { 
		for(int i = 0; i < 4; i++) {
			up[i] = setup("/SpearKnight/up" + (i+1), TILE_SIZE, TILE_SIZE);
			down[i] = setup("/SpearKnight/down" + (i+1), TILE_SIZE , TILE_SIZE);
			left[i] = setup("/SpearKnight/left" + (i+1), TILE_SIZE, TILE_SIZE);
			right[i]= setup("/SpearKnight/right" + (i+1), TILE_SIZE , TILE_SIZE);
		}
		
		for(int i = 0; i < 8; i++) {
			upAttack[i] = setup("/SpearKnight/attack_up" + (i+1), TILE_SIZE , TILE_SIZE);
			downAttack[i] = setup("/SpearKnight/attack_down" + (i+1), TILE_SIZE , TILE_SIZE);
			leftAttack[i] = setup("/SpearKnight/attack_left" + (i+1), TILE_SIZE , TILE_SIZE);
			rightAttack[i] = setup("/SpearKnight/attack_right" + (i+1), TILE_SIZE , TILE_SIZE);
		}
		upAttack[5] = setup("/SpearKnight/attack_up6", TILE_SIZE , TILE_SIZE * 2);
		downAttack[5] = setup("/SpearKnight/attack_down6", TILE_SIZE , TILE_SIZE * 2);
		leftAttack[5] = setup("/SpearKnight/attack_left6", TILE_SIZE * 2, TILE_SIZE);
		rightAttack[5] = setup("/SpearKnight/attack_right6", TILE_SIZE * 2, TILE_SIZE);
			
		skill1[0] = setup("/SpearKnight/skill1", TILE_SIZE, TILE_SIZE);	
		
		skill2[0] = setup("/SpearKnight/skill2", TILE_SIZE, TILE_SIZE * 4);
		
		skill3[0] = setup("/SpearKnight/skill3_1", TILE_SIZE * 4, TILE_SIZE * 4);
		skill3[1] = setup("/SpearKnight/skill3_2", TILE_SIZE * 4, TILE_SIZE * 4);
		skill3[2] = setup("/SpearKnight/skill3_3", TILE_SIZE * 4, TILE_SIZE * 4);
		skill3[4] = setup("/SpearKnight/skill3_3", TILE_SIZE, TILE_SIZE);
		pointboard = setup("/player/pointboard", TILE_SIZE * 4, TILE_SIZE);
		
		impossible = setup("/player/impossible", TILE_SIZE, TILE_SIZE);
		
		skillbar = setup("/player/skillbar", TILE_SIZE* 3 , TILE_SIZE); 
	}
	

	public void update(int [][] map) { // update player's position
		
		System.out.println(map.length + " " + map[0].length);
		if (hp <= 0) {
			selfArea[0] = -1;
			selfArea[1] = -1;
			selfArea[2] = -1;
			selfArea[3] = -1;
			gp.isPause = true;
			gp.endGame();
		}else {
			f++;	
			if (skill1EffectiveTime > 0 && shield > 0) shieldIsOn = true;
			else shieldIsOn = false;
			if (f % 50 == 0) {
				if (mp < 100)
					mp++;		
					f = 0;
				}
			if (skill2EffectiveTime > 118) {
				momentX = enemyX;
				momentY = enemyY;
			}
			
			if (attacking == true) {
				attacking();
			}
			else {

				if (keyHandler.attack== true) {
					attacking = true;	
				}			
				if (keyHandler.upPressed == true) {
					action = "up";
					spriteCounter++;
					preAction = action;
//					*notice: 4 vertexes
					if (map[(gp.worldy + y + TILE_SIZE/3 - speed) / TILE_SIZE][(x + gp.worldx)/ TILE_SIZE] != 0 
						&& map[(gp.worldy + y + TILE_SIZE/3 - speed) / TILE_SIZE][(gp.worldx + x + TILE_SIZE) / TILE_SIZE] != 0) {
						if (gp.worldy > 0 && y == TILE_SIZE * 4) gp.worldy -= speed;
						else {
							if (gp.worldy == 0) y -= speed;
							else {
								if (y > TILE_SIZE*4) y -= speed;
							}
						}
					}	
				}
				if (keyHandler.downPressed == true) {
					action = "down";
					preAction = action;
					spriteCounter++;
//					*notice: 4 vertexes
					if (map[(gp.worldy + y + speed + TILE_SIZE) / TILE_SIZE][(gp.worldx + x) / TILE_SIZE] != 0 
						&& map[(gp.worldy + y + speed + TILE_SIZE) / TILE_SIZE][(gp.worldx + x + TILE_SIZE) / TILE_SIZE] != 0) {
						
						if (y == SCREEN_HEIGHT - TILE_SIZE*4 && gp.worldy < TILE_SIZE*map.length - SCREEN_HEIGHT) gp.worldy += speed;
						else {
							if (gp.worldy == TILE_SIZE*map.length - SCREEN_HEIGHT) y += speed;
							else {
								if (y < SCREEN_HEIGHT - TILE_SIZE*4) {
									y += speed;
								}						
							}
						}
					}
				}
				if (keyHandler.leftPressed == true) {
					action = "left";
					preAction = action;
					spriteCounter++;
//					*notice: 4 vertexes
					if (map[(gp.worldy + y + TILE_SIZE/3) / TILE_SIZE][(gp.worldx + x - speed) / TILE_SIZE] != 0  
						&& map[(gp.worldy + y + TILE_SIZE)/TILE_SIZE][(gp.worldx + x - speed) / TILE_SIZE] != 0) {
						
						if(x == TILE_SIZE*4 && gp.worldx > 0) gp.worldx -= speed; 
						else {
							if (gp.worldx == 0) x -= speed;
							else {
								if (x > TILE_SIZE*4) x -= speed; 
							}
						}
					}
				}
				if (keyHandler.rightPressed == true) {
					action = "right";
					preAction = action;
					spriteCounter++;
//					*notice: 4 vertexes
					if (map[(gp.worldy + y + TILE_SIZE/3) / TILE_SIZE][(gp.worldx + x + speed + TILE_SIZE) / TILE_SIZE] != 0 
						&& map[(gp.worldy + y + TILE_SIZE) / TILE_SIZE][(gp.worldx + x + speed + TILE_SIZE) / TILE_SIZE] != 0) {
						
						if (gp.worldx < TILE_SIZE*map[0].length - SCREEN_WIDTH  && x == SCREEN_WIDTH - 4*TILE_SIZE) gp.worldx += speed;
						else {
							if (gp.worldx == TILE_SIZE*map[0].length - SCREEN_WIDTH) x += speed;
							else {
								if (x < SCREEN_WIDTH - 4*TILE_SIZE) x += speed;
							}
						}
					}
					
				}
				selfArea[0] = x + gp.worldx;
				selfArea[1] = y + gp.worldy;
				selfArea[2] = x + gp.worldx + TILE_SIZE;
				selfArea[3] = y + gp.worldy + TILE_SIZE;

				if (spriteCounter > 15) {   //15 or any number if liking
					if (spriteNum < 4) {
						spriteNum++;
					}
					else {
						spriteNum = 1;
					}
					spriteCounter = 0;
				}		
			}
		}
	}
	
	public void attacking() {	
		
		f_attack++;
		if(f_attack == attackSpeed*6/8) {
			gp.music.playSE(11);
			gp.heroTakeMeleeAttack(damageArea, attack);
		}
		if(f_attack % attackSpeed < attackSpeed/8) spriteNum = 1;
		else if(f_attack % attackSpeed < attackSpeed*2/8) spriteNum = 2;
		else if(f_attack % attackSpeed < attackSpeed*3/8) spriteNum = 3;
		else if(f_attack % attackSpeed < attackSpeed*4/8) spriteNum = 4;
		else if(f_attack % attackSpeed < attackSpeed*5/8) spriteNum = 5;
		else if(f_attack % attackSpeed < attackSpeed*6/8) spriteNum = 6;
		else if(f_attack % attackSpeed < attackSpeed*7/8) spriteNum = 7;
		else spriteNum = 8;
		if(f_attack > attackSpeed) {
			attacking = false;
			f_attack = 0;
			spriteCounter = 0;
			spriteNum = 1;
		}
		
	}
	
	public void draw(Graphics2D graphics2d) {	
		BufferedImage image = null;
		drawPoint(graphics2d);
		drawSkill(graphics2d);
		
		switch (action) {
		case "up": {
			if (attacking == false) {
				image = up[spriteNum-1];
			}
			if (attacking == true) {				
				damageArea[0] = x + gp.worldx;
				damageArea[1] = y + gp.worldy - TILE_SIZE;
				damageArea[2] = x + gp.worldx + TILE_SIZE;
				damageArea[3] = y + gp.worldy + TILE_SIZE;
				image = upAttack[spriteNum-1];
			}
			break;
		}
		case "down": {
			if (attacking == false) {
				image = down[spriteNum-1];
			}
			if (attacking == true) {
				damageArea[0] = x + gp.worldx;
				damageArea[1] = y + gp.worldy;
				damageArea[2] = x + gp.worldx + TILE_SIZE;
				damageArea[3] = y + gp.worldy + 2 * TILE_SIZE;			
				image = downAttack[spriteNum-1];
			}
			break;
		}
		case "left": {
			if (attacking == false) {
				image = left[spriteNum-1];
			}
			if (attacking == true) {			
				damageArea[0] = x + gp.worldx - TILE_SIZE;
				damageArea[1] = y + gp.worldy;
				damageArea[2] = x + gp.worldx + TILE_SIZE;
				damageArea[3] = y + gp.worldy + TILE_SIZE;
				image = leftAttack[spriteNum-1];
			}
			break;
		}
		case "right": {
			if (attacking == false) {
				image = right[spriteNum-1];
			}
			if (attacking == true) {		
				damageArea[0] = x + gp.worldx;
				damageArea[1] = y + gp.worldy;
				damageArea[2] = x + gp.worldx + 2 * TILE_SIZE;
				damageArea[3] = y + gp.worldy + TILE_SIZE;
				image = rightAttack[spriteNum-1];
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: ");
		}
		if(spriteNum == 6) {		
			if(action == "left") graphics2d.drawImage(image, x - TILE_SIZE, y, null);
			else if(action == "up") graphics2d.drawImage(image, x, y - TILE_SIZE, null);
			else graphics2d.drawImage(image, x, y, null);		
		}
		else graphics2d.drawImage(image, x, y, null);
	}


	private void drawSkill(Graphics2D graphics2d) {
		
		graphics2d.setColor(Color.black);
		graphics2d.drawImage(skillbar, 0, SCREEN_HEIGHT - TILE_SIZE, null);

//************************************	skill1 **************************************
		graphics2d.drawImage(skill1[0], 0, SCREEN_HEIGHT - TILE_SIZE, null);
		if (mp < skill1MP || skill1CD != 0) {
			graphics2d.drawImage(impossible, 0, SCREEN_HEIGHT - TILE_SIZE, null);
		}	
		if (mp > skill1MP && keyHandler.Askill1IsActive && skill1CD == 0) {
			keyHandler.Askill1IsActive = false;
			mp -= skill1MP;
			skill1CD = 10 * 60;
			skill1EffectiveTime = 5*60;
			shield = defaultShield;
			gp.music.playSE(15);
		}
		
		if (skill1CD != 0) {
			skill1CD--;
			if(shield > 0) {
				skill1EffectiveTime--;	
				if (skill1EffectiveTime >= 0) {
					graphics2d.drawImage(skill1[0], x, y, null);
					drawShieldBar(graphics2d);						
				}
			graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 16F));
			graphics2d.setColor(Color.white);
			
			int length = (int)graphics2d.getFontMetrics().getStringBounds(Integer.toString(skill1CD/60), graphics2d).getWidth();
			graphics2d.drawString(Integer.toString(skill1CD/60), TILE_SIZE/2 - length/2, SCREEN_HEIGHT - TILE_SIZE/2);
			}
		}

//*********************	skill 2  ********************************		
		graphics2d.drawImage(skill2[0], TILE_SIZE, SCREEN_HEIGHT - TILE_SIZE, null);
		if (mp < skill2MP || skill2CD != 0) {
			graphics2d.drawImage(impossible, TILE_SIZE, SCREEN_HEIGHT - TILE_SIZE, null);
		}
		
		if (mp > skill2MP && keyHandler.Askill2IsActive && skill2CD == 0) {
			keyHandler.Askill2IsActive = false;
			skill2CD = 7*60;
			skill2EffectiveTime = 2 * 60;
			mp -= skill2MP;
			gp.music.playSE(12);
		}

		if (skill2CD != 0) {
			
			skill2EffectiveTime--;
			skill2CD--;
			if (skill2EffectiveTime >= 0) {
				if(skill2EffectiveTime > 118) {}
				else if(skill2EffectiveTime > 70) graphics2d.drawImage(skill2[0], momentX - gp.worldx, momentY - 3 * TILE_SIZE - gp.worldy,  null);
				else if(skill2EffectiveTime > 50){
					graphics2d.drawImage(skill2[0], momentX - gp.worldx, momentY - 2 * TILE_SIZE + (2 * TILE_SIZE / skill2EffectiveTime) - gp.worldy,  null);
				}
				else {
					graphics2d.drawImage(skill2[0], momentX - gp.worldx, momentY - TILE_SIZE - gp.worldy,  null);
					damageArea[0] = momentX;
					damageArea[1] = momentY - 2 * TILE_SIZE;
					damageArea[2] = momentX + TILE_SIZE;
					damageArea[3] = momentY + 2 * TILE_SIZE;
					if(skill2EffectiveTime == 40) {
						gp.music.playSE(13);
						gp.heroTakeMeleeAttack(damageArea, 100);
					}
					
				}
				
			}
			
			graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 16F));
			graphics2d.setColor(Color.white);
			
			int length = (int)graphics2d.getFontMetrics().getStringBounds(Integer.toString(skill2CD/60), graphics2d).getWidth();
			graphics2d.drawString(Integer.toString(skill2CD/60), TILE_SIZE + TILE_SIZE/2 - length/2, SCREEN_HEIGHT - TILE_SIZE/2);
		}
		
		
//****************************	skill 3	**************************************	
		graphics2d.drawImage(skill3[4], TILE_SIZE*2, SCREEN_HEIGHT - TILE_SIZE, null);
		if (mp < skill3MP || skill3CD != 0) {
			graphics2d.drawImage(impossible, TILE_SIZE*2, SCREEN_HEIGHT - TILE_SIZE, null);
		}
		
		if (mp > skill3MP && keyHandler.Askill3IsActive && skill3CD == 0) {
			keyHandler.Askill3IsActive = false;
			skill3EffectiveTime = 2*60;
			skill3CD = 10 * 60;
			mp -= skill3MP;
			gp.music.playSE(2);
		}
		
		if (skill3CD != 0) {
			
			if (skill3EffectiveTime >= 0) {
				if(skill3EffectiveTime > 100) graphics2d.drawImage(skill3[0], x - TILE_SIZE * 3/2, y - TILE_SIZE * 3/2, null);
				else if(skill3EffectiveTime > 80) graphics2d.drawImage(skill3[1], x - TILE_SIZE * 3/2, y - TILE_SIZE * 3/2, null);
				else if(skill3EffectiveTime > 40){
					damageArea[0] = x - TILE_SIZE * 3/2 + gp.worldx;
					damageArea[1] = y - TILE_SIZE * 3/2 + gp.worldy;
					damageArea[2] = x + TILE_SIZE * 5/2 + gp.worldx;
					damageArea[3] = y + TILE_SIZE * 5/2 + gp.worldy;
					if(skill3EffectiveTime == 60) {
						gp.music.playSE(2);
						gp.heroTakeMeleeAttack(damageArea, 100);
					}
					graphics2d.drawImage(skill3[2], x - TILE_SIZE * 3/2, y - TILE_SIZE * 3/2, null);
				}else if(skill3EffectiveTime > 20) graphics2d.drawImage(skill3[1], x - TILE_SIZE * 3/2, y - TILE_SIZE * 3/2, null);
				else graphics2d.drawImage(skill3[0], x - TILE_SIZE * 3/2, y - TILE_SIZE * 3/2, null);
				
			}
			skill3EffectiveTime--;
			skill3CD--;

			graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 16F));
			graphics2d.setColor(Color.white);
			
			int length = (int)graphics2d.getFontMetrics().getStringBounds(Integer.toString(skill3CD/60), graphics2d).getWidth();
			graphics2d.drawString(Integer.toString(skill3CD/60), TILE_SIZE*2 + TILE_SIZE/2 - length/2, SCREEN_HEIGHT - TILE_SIZE/2);
		}
	}

	private void drawShieldBar(Graphics2D graphics2d) {
		graphics2d.setFont(graphics2d.getFont().deriveFont(Font.PLAIN, 12F));
		graphics2d.setColor(Color.black);
		
		graphics2d.fillRoundRect(x-2, y-5, TILE_SIZE + 4, 10, 4, 4);
		graphics2d.setColor(Color.white);
		graphics2d.fillRoundRect(x, y-4, (int)(shield*1.0/defaultShield * TILE_SIZE), 8, 4, 4);
		
	}

	public void drawPoint(Graphics2D graphics2d) {
		graphics2d.drawImage(pointboard, 0, 0, null);
		String text = "HP";
		graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 16F));
		graphics2d.setColor(Color.white);
		graphics2d.drawString(text, 10, 20);
		
		text = "MP";
		graphics2d.drawString(text, 10, 40);
		
		graphics2d.setColor(Color.gray);
		graphics2d.fillRoundRect(TILE_SIZE-2, 6, 120 + 4, 16, 4, 4);
		graphics2d.setColor(Color.red);
		graphics2d.fillRoundRect(TILE_SIZE, 8, (int)(hp * 1.0/defaultHP * 120), 12, 4, 4);
		
		graphics2d.setColor(Color.gray);
		graphics2d.fillRoundRect(TILE_SIZE- 2, 26, 100 + 4, 16, 4, 4);
		graphics2d.setColor(Color.blue);
		graphics2d.fillRoundRect(TILE_SIZE, 28, (int)(mp/100.0 * 100), 12, 4, 4);
		
		
		graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 10F));
		graphics2d.setColor(Color.white);
		text = Integer.toString(hp);
		graphics2d.drawString(text, 60, 18);
		text = Integer.toString(mp);
		graphics2d.drawString(text, 60, 38);
	}

}
