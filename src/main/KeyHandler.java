package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardEndHandler;

public class KeyHandler implements KeyListener{
	
	public boolean upPressed, downPressed, leftPressed, rightPressed, attack;
	GamePanel gp;
	
	public boolean Askill1IsActive = false;
	public boolean Askill2IsActive = false;
	public boolean Askill3IsActive = false;
	
	public KeyHandler(){
		// TODO Auto-generated constructor stub
	}

	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	@Override
	public void keyTyped(KeyEvent e) { 
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		
		int code = e.getKeyCode();
		
//		GAME STATE
			if (code == KeyEvent.VK_P) {
				if (gp.gameState == gp.pauseState) gp.gameState = gp.playState;
				else gp.gameState = gp.pauseState;
			}
			if (code == KeyEvent.VK_W) {
					upPressed = true;
				}
			if (code == KeyEvent.VK_S) {
					downPressed = true;
				}
			if (code == KeyEvent.VK_A) {
					leftPressed = true;
				}
			if (code == KeyEvent.VK_D) {
					rightPressed = true;
				}
			if (code == KeyEvent.VK_F) {
					attack = true;
				}
			if (code == KeyEvent.VK_1) {
					Askill1IsActive = true;
				}
			if (code == KeyEvent.VK_2) {
					Askill2IsActive = true;
				}
			if (code == KeyEvent.VK_3) {
					Askill3IsActive = true;
				}	
			if (code == KeyEvent.VK_P) {
				gp.isPause = !gp.isPause;
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if (code == KeyEvent.VK_F) {
			attack = false;
		}
		if (code == KeyEvent.VK_1) {
			Askill1IsActive = false;
		}
		if (code == KeyEvent.VK_2) {
			Askill2IsActive = false;
		}
		if (code == KeyEvent.VK_3) {
			Askill3IsActive = false;
		}
	}
}
