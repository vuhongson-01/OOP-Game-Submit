package main;
import entity.Boss1;
import entity.Boss5;
import entity.Boss4;
import entity.Boss3;
import entity.Boss2;
import entity.Entity;
import entity.Monster1;
import entity.Monster2;

public class CreateMonster {
	
	Entity [][] listEntities = new Entity [5][50];
	
	public CreateMonster(GamePanel gp, int hard) {
		// TODO Auto-generated constructor stub
		//map1
		listEntities[0][0] = new Boss3(gp, hard, 20*48, 21*48);
		listEntities[0][1] = new Monster2(gp, hard, 7*48, 6*48);
		listEntities[0][2] = new Monster2(gp, hard, 20*48, 4*48);
		listEntities[0][3] = new Monster2(gp, hard, 4*48, 20*48);
		listEntities[0][4] = new Monster2(gp, hard, 18*48, 16*48);
		listEntities[0][5] = new Monster2(gp, hard, 10*48, 11*48);
		
		//map2
		listEntities[1][0] = new Boss2(gp, hard, 25*48, 4*48);
		listEntities[1][1] = new Monster1(gp, hard, 5*48, 10*48);
		listEntities[1][2] = new Monster1(gp, hard, 25*48, 10*48);
		listEntities[1][3] = new Monster1(gp, hard, 18*48, 6*48);
		listEntities[1][4] = new Monster1(gp, hard, 8*48, 18*48);
		listEntities[1][5] = new Monster1(gp, hard, 26*48, 18*48);
		
		//map3
		listEntities[2][0] = new Boss5(gp, hard, 26*48, 20*48);
		listEntities[2][1] = new Monster2(gp, hard, 4*48, 11*48);
		listEntities[2][2] = new Monster2(gp, hard, 27*48, 12*48);
		listEntities[2][3] = new Monster2(gp, hard, 12*48, 26*48);
		listEntities[2][4] = new Monster2(gp, hard, 26*48, 1*48);
		listEntities[2][5] = new Monster2(gp, hard, 15*48, 27*48);
		
		//map4
		listEntities[3][0] = new Boss4(gp, hard, 35*48, 6*48);
		listEntities[3][1] = new Boss1(gp, hard, 10*48, 33*48);
		listEntities[3][2] = new Monster1(gp, hard, 10*48, 5*48);
		listEntities[3][3] = new Monster1(gp, hard, 36*48, 27*48);
		listEntities[3][4] = new Monster1(gp, hard, 29*48, 30*48);
		listEntities[3][5] = new Monster1(gp, hard, 6*48, 28*48);
	}

}
