package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.GamePanel;
import main.KeyHandler;

public class DarkKnight extends Entity{

	GamePanel gp;
	KeyHandler keyHandler;
	public final int defaultMP = 100;
	
	public BufferedImage bmr_right, bmr_left, bmr_up, bmr_down;
	public BufferedImage image1 = null;
	public BufferedImage image2 = null;
	
	public BufferedImage pointboard, skillbar, impossible;
	public int attackCD = 60; // attack once per second
	public int shield = 0;
	
	boolean deleteAttack = false;
	boolean skill1Activating = false;
	boolean skill2Activating = false;
	boolean skill3Activating = false;
	boolean isDied = false;
	
	private int skill1CD = 0; //pro-active skill - 10s CD
	private int skill1EffectiveTime = 6*60; //during 1s * attackSpeedFPS effective
	private int skill1MP = 40; //skill1 need attackSpeed MP
	
	private int skill2CD = 0; //pro-active skill - 10s CD
	private int skill2EffectiveTime = 1*60; //during 3s * attackSpeedFPS effective
	private int skill2MP = 20; //skill1 need attackSpeed MP
	int momentX = -1, momentY = -1;
	
	private int skill3CD = 0; //pro-active skill - 10s CD
	private int skill3EffectiveTime = 6*60; //during 6s * attackSpeedFPS effective
	private int skill3MP = 50; //skill1 need attackSpeed MP
	
	public DarkKnight(GamePanel gp, KeyHandler keyHandler, int firstLocationX, int firstLocationY) {
		this.gp = gp;
		this.keyHandler = keyHandler;
		
		setDefaultValue(firstLocationX, firstLocationY);
		getPlayerImage();
	}
	
	
	public void setDefaultValue(int firstLocationX, int firstLocationY) {
//		location for draw image
		x = firstLocationX;
		y = firstLocationY;

//		this.map = map;
		selfArea = new int [4];
		damageArea = new int [4];
		
		selfArea[0] = x;
		selfArea[1] = y;
		selfArea[2] = x+ TILE_SIZE;
		selfArea[3] = y + TILE_SIZE;
				
		hp = defaultHP;
		mp = defaultMP;
		attack = 25;
		attackSpeed = 60;
		defense = 10;
		speed = 3;
		
		action = "down";
	}
	
	public void getPlayerImage() {
// Get player image		
		for (int i = 0; i < 5; i++) {
			up[i] = setup("/darkknight/up" + (i+1), TILE_SIZE, TILE_SIZE);
			down[i] = setup("/darkknight/down" + (i+1), TILE_SIZE, TILE_SIZE);
			right[i] = setup("/darkknight/right" + (i+1), TILE_SIZE, TILE_SIZE);
			left[i] = setup("/darkknight/left" + (i+1),  TILE_SIZE, TILE_SIZE);
		}

		die[0] = setup("/darkknight/rip",  TILE_SIZE, TILE_SIZE);
		leftAttack[0] = setup("/darkknight/atk_left",  TILE_SIZE, TILE_SIZE);
		rightAttack[0] = setup("/darkknight/atk_right",  TILE_SIZE, TILE_SIZE);
		upAttack[0] = setup("/darkknight/atk_up",  TILE_SIZE, TILE_SIZE);
		
		for (int i = 0; i < 6; i++) {
			downAttack[i] = setup("/darkknight/atk_down" + (i+1),  TILE_SIZE, TILE_SIZE);
		}
// Get boomerang image	
		bmr_left = setup("/darkknight/boomerang_left", TILE_SIZE / 2, TILE_SIZE);
		bmr_right = setup("/darkknight/boomerang_right", TILE_SIZE / 2, TILE_SIZE);
		bmr_up = setup("/darkknight/boomerang_up", TILE_SIZE, TILE_SIZE / 2);
		bmr_down = setup("/darkknight/boomerang_down", TILE_SIZE, TILE_SIZE / 2);
	
//Get skill image
		pointboard =  setup("/darkknight/pointboard", TILE_SIZE * 4, TILE_SIZE);
		skill1[0] = setup("/darkknight/skill1_1", TILE_SIZE, TILE_SIZE * 2);
		skill1[1] = setup("/darkknight/skill1_2", TILE_SIZE, TILE_SIZE * 2);
		
		skill2[0] = setup("/darkknight/skill2_1_left", TILE_SIZE, TILE_SIZE);
		skill2[1] = setup("/darkknight/skill2_2_left", TILE_SIZE, TILE_SIZE);
		skill2[2] = setup("/darkknight/skill2_3_left", TILE_SIZE, TILE_SIZE);
		
		skill2[3] = setup("/darkknight/skill2_1_right", TILE_SIZE, TILE_SIZE);
		skill2[4] = setup("/darkknight/skill2_2_right", TILE_SIZE, TILE_SIZE);
		skill2[5] = setup("/darkknight/skill2_3_right", TILE_SIZE, TILE_SIZE);
		
		skill2[6] = setup("/darkknight/skill2_1_up", TILE_SIZE, TILE_SIZE);
		skill2[7] = setup("/darkknight/skill2_2_up", TILE_SIZE, TILE_SIZE);
		skill2[8] = setup("/darkknight/skill2_3_up", TILE_SIZE, TILE_SIZE);
		
		skill2[9] = setup("/darkknight/skill2_1_down", TILE_SIZE, TILE_SIZE);
		skill2[10] = setup("/darkknight/skill2_2_down", TILE_SIZE, TILE_SIZE);
		skill2[11] = setup("/darkknight/skill2_3_down", TILE_SIZE, TILE_SIZE);
		
		for(int i = 0; i < 8; i++) {
			skill3[i] = setup("/darkknight/skill3_"+(i+1), TILE_SIZE * 3, TILE_SIZE * 3);
		}
		skill3[8] = setup("/darkknight/skill3_1", TILE_SIZE, TILE_SIZE);
		impossible = setup("/player/impossible", TILE_SIZE, TILE_SIZE);
		skillbar = setup("/player/skillbar", TILE_SIZE* 3 , TILE_SIZE); 
			
	}
	

	public void update(int [][] map) { // update player's position
		
//		System.out.println(map.length + " " + map[0].length);,
		
		if (hp <= 0) {
			selfArea[0] = -1;
			selfArea[1] = -1;
			selfArea[2] = -1;
			selfArea[3] = -1;
			gp.isPause = true;
			gp.endGame();
		}
		else {
			
			f++;
			if(attackCD > 0) attackCD--;
			if(skill1EffectiveTime > 0 && skill1EffectiveTime < 6*60) {
				attack = 30;
				speed = 4;
				attackSpeed = 36;
			}
			else {
				attack = 25;
				speed = 3;
				attackSpeed = 60;
			}
			
			if(skill2EffectiveTime > 58) {
				momentX = x;
				momentY = y;
			}
			
			if (f % 50 == 0) {
				if (mp < 100)
					mp++;		
					f = 0;
				}
			
			if (attacking == true) {
				attacking();
			}
			else {
	
				if (keyHandler.attack== true && attackCD == 0) {
					gp.music.playSE(3);
					attacking = true;	
					attackCD = 60;
				}			
				if (keyHandler.upPressed == true) {
					action = "up";
					spriteCounter++;
	//				*notice: 4 vertexes
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
					spriteCounter++;
	//				*notice: 4 vertexes
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
					spriteCounter++;
	//				*notice: 4 vertexes
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
					spriteCounter++;
	//				*notice: 4 vertexes
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
				
				if (spriteCounter > 15) {   
					if (spriteNum < 5) {
						spriteNum++;
					}
					else{
						spriteNum = 1;
					}
					spriteCounter = 0;
				}	
				
			}
		}
//		if ((gp.worldx + x) / TILE_SIZE == 10 && (gp.worldy+y)/TILE_SIZE == 10) {
//			gp.nextGameState();
//		}
	}
	
	public void stopAttack() {
		attacking = false;
		f_attack = 0;
		spriteCounter = 0;
		spriteNum = 1;
		damageArea[0] = -1;
		damageArea[1] = -1;
		damageArea[2] = -1;
		damageArea[3] = -1;
	}
	
	public void attacking() {	
		if (f_attack != 0) deleteAttack =  gp.heroTakeRangeAttack(damageArea, attack);		
		if (!deleteAttack) {
			f_attack++;
			if(f_attack % attackSpeed < attackSpeed/6) spriteNum = 1;
			else if(f_attack % attackSpeed < attackSpeed*2/6) spriteNum = 2;
			else if(f_attack % attackSpeed < attackSpeed*3/6) spriteNum = 3;
			else if(f_attack % attackSpeed < attackSpeed*4/6) spriteNum = 4;
			else if(f_attack % attackSpeed < attackSpeed*5/6) spriteNum = 5;
			else stopAttack();
		}
		else {
			stopAttack();
			deleteAttack = false;			
		}
	}

	public void draw(Graphics2D graphics2d) {	
		drawPoint(graphics2d);
		drawSkill(graphics2d);
		
		if (hp > 0) {
			switch (action) {
			case "up": {
				if (attacking == false) {
					image2 = null;
					image1 = up[spriteNum-1];
				}
				if (attacking == true) {
					image2 = bmr_up;
					
					damageArea[0] = x + gp.worldx;
					damageArea[1] = y + gp.worldy - spriteNum * TILE_SIZE;
					damageArea[2] = x + gp.worldx + TILE_SIZE;
					damageArea[3] = y + gp.worldy - spriteNum * TILE_SIZE + TILE_SIZE;
					image1 = upAttack[0];
				}
				break;
			}
			case "down": {
				if (attacking == false) {
					image2 = null;
					image1 = down[spriteNum-1];
				}
				if (attacking == true) {
					image2 = bmr_down;
	
					damageArea[0] = x + gp.worldx;
					damageArea[1] = y + gp.worldy + spriteNum * TILE_SIZE;
					damageArea[2] = x + gp.worldx + TILE_SIZE;
					damageArea[3] = y + gp.worldy + spriteNum * TILE_SIZE + TILE_SIZE;
					
					image1 = downAttack[spriteNum-1];
				}
				break;
			}
			case "left": {
				if (attacking == false) {
					image2 = null;
					image1 = left[spriteNum-1];
				}
				if (attacking == true) {
					image2 = bmr_left;
				
					damageArea[0] = x + gp.worldx - spriteNum * TILE_SIZE;
					damageArea[1] = y + gp.worldy;
					damageArea[2] = x + gp.worldx - spriteNum * TILE_SIZE + TILE_SIZE;
					damageArea[3] = y + gp.worldy + TILE_SIZE;
					image1 = leftAttack[0];
				}
				break;
			}
			case "right": {
				if (attacking == false) {
					image2 = null;
					image1 = right[spriteNum-1];
				}
				if (attacking == true) {
					image2 = bmr_right;
					
					damageArea[0] = x + gp.worldx + spriteNum * TILE_SIZE;
					damageArea[1] = y + gp.worldy;
					damageArea[2] = x + gp.worldx + spriteNum * TILE_SIZE + TILE_SIZE;
					damageArea[3] = y + gp.worldy + TILE_SIZE;
					image1 = rightAttack[0];
				}
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: ");
			}
			
	//		selfCenterX = x + TILE_SIZE/2;
	//		selfCenterY = y + TILE_SIZE/2;
		
			graphics2d.drawImage(image2, damageArea[0] - gp.worldx, damageArea[1] - gp.worldy, null);
			graphics2d.drawImage(image1, x, y, null);
		}else {
			graphics2d.drawImage(die[0], x, y, null);
		}
		
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
		graphics2d.fillRoundRect(TILE_SIZE, 8, (int)(hp*1.0/defaultHP  * 120), 12, 4, 4);
		
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
	
	private void drawSkill(Graphics2D graphics2d) {
		
		graphics2d.setColor(Color.black);
		graphics2d.drawImage(skillbar, 0, SCREEN_HEIGHT - TILE_SIZE, null);
		graphics2d.drawImage(skill1[0], 0, SCREEN_HEIGHT - TILE_SIZE * 2, null);
		graphics2d.drawImage(skill2[5], TILE_SIZE, SCREEN_HEIGHT - TILE_SIZE, null);
		graphics2d.drawImage(skill3[8], TILE_SIZE * 2, SCREEN_HEIGHT - TILE_SIZE, null);
		
//************	skill 1 **************
		if (mp > skill1MP && keyHandler.Askill1IsActive && skill1CD == 0) {
			keyHandler.Askill1IsActive = false;
			mp -= skill1MP;
			skill1CD = 15 * 60;
			skill1EffectiveTime = 6*60;
		}
		
		if (skill1CD != 0) {			
			skill1EffectiveTime--;
			skill1CD--;
			
			if (skill1EffectiveTime >= 0) {
				if(skill1EffectiveTime % 60 == 50) gp.music.playSE(17);
				if(skill1EffectiveTime % 40 < 20) graphics2d.drawImage(skill1[0],x,y - TILE_SIZE,  null);
				else graphics2d.drawImage(skill1[1],x ,y - TILE_SIZE,  null);			
			}
			graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 16F));
			graphics2d.setColor(Color.white);
			
			if (mp < skill1MP || skill1CD != 0) {
				graphics2d.drawImage(impossible, 0, SCREEN_HEIGHT-TILE_SIZE, null);
			}
			
			int length = (int)graphics2d.getFontMetrics().getStringBounds(Integer.toString(skill1CD/60), graphics2d).getWidth();
			graphics2d.drawString(Integer.toString(skill1CD/60), TILE_SIZE/2 - length/2, SCREEN_HEIGHT - TILE_SIZE/3);
			
		}
//************* Skill 2 **************
		if (mp > skill2MP && keyHandler.Askill2IsActive && skill2CD == 0) {
			keyHandler.Askill2IsActive = false;
			mp -= skill2MP;
			skill2CD = 3*60;
			skill2EffectiveTime = 1*60;
			gp.music.playSE(3);
		}
		
		if (skill2CD != 0) {		
			skill2EffectiveTime--;
			skill2CD--;
			
			if (skill2EffectiveTime >= 0) {
				if(skill2EffectiveTime > 58) preAction = action;
				else {
					if(preAction == "up") {
						if(skill2EffectiveTime > 56) {}
						else if(skill2EffectiveTime > 50) graphics2d.drawImage(skill2[6], momentX , momentY - TILE_SIZE, null);
						else if(skill2EffectiveTime > 45) graphics2d.drawImage(skill2[7], momentX , momentY - TILE_SIZE, null);
						else {
							graphics2d.drawImage(skill2[8], momentX, momentY - (5*TILE_SIZE - skill2EffectiveTime * 5), null);
							damageArea[0] = momentX + gp.worldx;
							damageArea[1] = momentY + gp.worldy - (5*TILE_SIZE - skill2EffectiveTime * 5);
							damageArea[2] = momentX + gp.worldx + TILE_SIZE;
							damageArea[3] = momentY + gp.worldy - (5*TILE_SIZE - skill2EffectiveTime * 5) + TILE_SIZE;
							gp.heroTakeMeleeAttack(damageArea, 2);
						}
					}else if(preAction == "down") {
						if(skill2EffectiveTime > 56) {}
						else if(skill2EffectiveTime > 50) graphics2d.drawImage(skill2[9], momentX, momentY + TILE_SIZE, null);
						else if(skill2EffectiveTime > 45) graphics2d.drawImage(skill2[10], momentX, momentY + TILE_SIZE, null);
						else {
							graphics2d.drawImage(skill2[11], momentX, momentY + (5*TILE_SIZE - skill2EffectiveTime * 5), null);
							damageArea[0] = momentX + gp.worldx;
							damageArea[1] = momentY + gp.worldy + (5*TILE_SIZE - skill2EffectiveTime * 5);
							damageArea[2] = momentX + gp.worldx + TILE_SIZE;
							damageArea[3] = momentY + gp.worldy + (5*TILE_SIZE - skill2EffectiveTime * 5) + TILE_SIZE;
							gp.heroTakeMeleeAttack(damageArea, 2);
						}
					}else if(preAction == "left") {
						if(skill2EffectiveTime > 56) {}
						else if(skill2EffectiveTime > 50) graphics2d.drawImage(skill2[0], momentX - TILE_SIZE, momentY, null);
						else if(skill2EffectiveTime > 45) graphics2d.drawImage(skill2[1], momentX - TILE_SIZE, momentY, null);
						else {
							graphics2d.drawImage(skill2[2], momentX - (5*TILE_SIZE - skill2EffectiveTime * 5), momentY, null);
							damageArea[0] = momentX + gp.worldx - (5*TILE_SIZE - skill2EffectiveTime * 5);
							damageArea[1] = momentY + gp.worldy;
							damageArea[2] = momentX + gp.worldx - (5*TILE_SIZE - skill2EffectiveTime * 5) + TILE_SIZE;
							damageArea[3] = momentY + gp.worldy + TILE_SIZE;
							gp.heroTakeMeleeAttack(damageArea, 2);
						}
					}else if(preAction == "right") {
						if(skill2EffectiveTime > 56) {}
						else if(skill2EffectiveTime > 50) graphics2d.drawImage(skill2[3], momentX + TILE_SIZE, momentY, null);
						else if(skill2EffectiveTime > 45) graphics2d.drawImage(skill2[4], momentX + TILE_SIZE, momentY, null);
						else {
							graphics2d.drawImage(skill2[5], momentX + (5*TILE_SIZE - skill2EffectiveTime * 5), momentY, null);
							damageArea[0] = momentX + gp.worldx + (5*TILE_SIZE - skill2EffectiveTime * 5);
							damageArea[1] = momentY + gp.worldy;
							damageArea[2] = momentX + gp.worldx + (5*TILE_SIZE - skill2EffectiveTime * 5) + TILE_SIZE;
							damageArea[3] = momentY + gp.worldy + TILE_SIZE;
							gp.heroTakeMeleeAttack(damageArea, 2);
						}
					}
				}
			}
			graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 16F));
			graphics2d.setColor(Color.white);
			
			if (mp < skill2MP || skill2CD != 0) {
				graphics2d.drawImage(impossible, TILE_SIZE, SCREEN_HEIGHT-TILE_SIZE, null);
			}
			
			int length = (int)graphics2d.getFontMetrics().getStringBounds(Integer.toString(skill2CD/60), graphics2d).getWidth();
			graphics2d.drawString(Integer.toString(skill2CD/60), TILE_SIZE/2 - length/2 + TILE_SIZE, SCREEN_HEIGHT - TILE_SIZE/3);
			
		}
		
// ******************* Skill 3 **************************
		if (mp > skill3MP && keyHandler.Askill3IsActive && skill3CD == 0) {
			keyHandler.Askill3IsActive = false;
			mp -= skill3MP;
			skill3EffectiveTime = 5*60;
			skill3CD = 15 * 60;
			
		}
		
		if (skill3CD != 0) {			
			skill3EffectiveTime--;
			skill3CD--;
			if (skill3EffectiveTime >= 0) {
				int cnt = (skill3EffectiveTime % 40) / 5;
				graphics2d.drawImage(skill3[7-cnt], x - TILE_SIZE, y - TILE_SIZE, null);
				if(skill3EffectiveTime % 40 == 30) {
					damageArea[0] = x + gp.worldx - TILE_SIZE;
					damageArea[1] = y + gp.worldy - TILE_SIZE;
					damageArea[2] = x + gp.worldx + 2 * TILE_SIZE;
					damageArea[3] = y + gp.worldy + 2 * TILE_SIZE;
					gp.heroTakeMeleeAttack(damageArea, 50);
					gp.music.playSE(4);
				}
			}
			graphics2d.setFont(graphics2d.getFont().deriveFont(Font.BOLD, 16F));
			graphics2d.setColor(Color.white);
			
			if (mp < skill3MP || skill3CD != 0) {
				graphics2d.drawImage(impossible, 2 * TILE_SIZE, SCREEN_HEIGHT-TILE_SIZE, null);
			}
			
			int length = (int)graphics2d.getFontMetrics().getStringBounds(Integer.toString(skill3CD/60), graphics2d).getWidth();
			graphics2d.drawString(Integer.toString(skill3CD/60), TILE_SIZE/2 - length/2 + 2 * TILE_SIZE, SCREEN_HEIGHT - TILE_SIZE/3);
			
		}
	}
}

