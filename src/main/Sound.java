package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	Clip clip;
	URL soundURL[] = new URL[30];
	boolean isMute = false;
	
	public Sound(boolean isMute) {
		soundURL[0] = getClass().getResource("/sound/main_interface.wav");
		soundURL[1] = getClass().getResource("/sound/Monster_attack.wav");
		soundURL[2] = getClass().getResource("/sound/SpearKnight_skill_3.wav");
		soundURL[3] = getClass().getResource("/sound/DarkKnight_skill_2.wav");
		soundURL[4] = getClass().getResource("/sound/DarkKnight_skill_3.wav");
		soundURL[5] = getClass().getResource("/sound/Playing_bg_sound.wav");
		soundURL[6] = getClass().getResource("/sound/DarkKnight_attack.wav");
		soundURL[7] = getClass().getResource("/sound/gameover.wav");
		soundURL[8] = getClass().getResource("/sound/victory.wav");
		soundURL[9] = getClass().getResource("/sound/boss3.wav");
		soundURL[10] = getClass().getResource("/sound/Win_level.wav");
		soundURL[11] = getClass().getResource("/sound/SpearKnight_attack.wav");
		soundURL[12] = getClass().getResource("/sound/SpearKnight_skill_2_1.wav");
		soundURL[13] = getClass().getResource("/sound/SpearKnight_skill_2_2.wav");
		soundURL[14] = getClass().getResource("/sound/dragon_die.wav");
		soundURL[15] = getClass().getResource("/sound/SpearKnight_skill_1.wav");
		soundURL[16] = getClass().getResource("/sound/click.wav");
		soundURL[17] = getClass().getResource("/sound/DarkKnight_skill_1.wav");
		soundURL[18] = getClass().getResource("/sound/door_openning.wav");
		
		this.isMute = isMute;
		
	}
	
	public void setMute(boolean mute) {
		this.isMute = mute;
	}
	
	public void setFile(int i) {
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		}catch(Exception e) {
			
		}
	}
	
	public void play() {
		clip.stop();
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}
	public void playMusic(int i) {		
		if (!isMute) {
			setFile(i);
			play();
			loop();
		}
	}
	public void stopMusic() {
		stop();
	}
	public void playSE(int i) {
		if (!isMute) {
			setFile(i);
			play();
		}
	}
}

