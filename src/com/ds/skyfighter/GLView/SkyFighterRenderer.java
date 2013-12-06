package com.ds.skyfighter.GLView;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

import com.ds.skyfighter.Engine.SFEngine;
import com.ds.skyfighter.GL.Textures;
import com.ds.skyfighter.Guy.Enemy;
import com.ds.skyfighter.Guy.Fighter;
import com.ds.skyfighter.Guy.Score;
import com.ds.skyfighter.Guy.Weapon;

public class SkyFighterRenderer implements Renderer {
	
	private float bgScroll1;
	private float bgScroll2;
	private SkyFighterBG skyFighterBG = new SkyFighterBG();
	private SkyFighterBG skyFighterBG2 = new SkyFighterBG();
	private Fighter fighter;

	private Enemy[] enemy = new Enemy[SFEngine.EnemyCounts];
	private Weapon weapon;
	private Score[] score = new Score[SFEngine.SCORE_LENGTH];
	
	private float bili;
	//private int width;
	//private int height;
	Random randomPos = new Random();
	
	private int flash = -1;
	private int flashcounts = 0;
	
	private long loopStart = 0;
	private long loopEnd = 0;
	private long loopRunTime = 0;
	
	private Textures textureLoader;
	private int[] spriteSheets = new int[4];
	
	private static int Score = 0;
	
	@Override
	public void onDrawFrame(GL10 gl) {
		loopStart = System.currentTimeMillis();
		try {
			if(loopRunTime < SFEngine.GAME_THREAD_FPS_SLEEP)
				Thread.sleep(SFEngine.GAME_THREAD_FPS_SLEEP - loopRunTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		scrollBackground1(gl);
		//scrollBackground2(gl);
		moveFighter(gl);
		moveWeapon(gl);
		moveEnemy(gl);
		detectCollisions();
		detectFighter();
		//drawScore(gl);
		caluSocre(gl);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
		loopEnd = System.currentTimeMillis();
		loopRunTime = (loopEnd - loopStart);
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		//this.width = width;
		//this.height = height;
		this.bili = (float)width/height;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0f, 1f, 0f, 1f, -1f, 1f);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
		initFighter();
		initEnemy();
		initWeapon();
		initScore();
		
		textureLoader = new Textures(gl);
		skyFighterBG.loadTexture(gl, SFEngine.GAME_BACKGROUND_ONE, SFEngine.context);
		skyFighterBG2.loadTexture(gl, SFEngine.GAME_BACKGROUND_TWO, SFEngine.context);
		spriteSheets = textureLoader.loadTexture(gl, SFEngine.GAME_FIGHTER, SFEngine.context, 1);
		spriteSheets = textureLoader.loadTexture(gl, SFEngine.WEAPONS_SHEET, SFEngine.context, 2);
		spriteSheets = textureLoader.loadTexture(gl, SFEngine.GAME_ENEMY, SFEngine.context, 3);
		spriteSheets = textureLoader.loadTexture(gl, SFEngine.NUMBERS, SFEngine.context, 4);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glEnable(GL10.GL_BLEND);
	}
	private void detectCollisions(){
		for(int i = 0; i < SFEngine.EnemyCounts; i++){
			if(!enemy[i].isDestroyed && SFEngine.Enemy_Y[i] < 9){
				if(weapon.posY >= SFEngine.Enemy_Y[i] - 1 &&
						weapon.posY <= SFEngine.Enemy_Y[i] &&
						weapon.posX <= SFEngine.Enemy_X[i] + 1 &&
						weapon.posX >= SFEngine.Enemy_X[i] - 1){
					enemy[i].applyDamage();
					weapon.posX = SFEngine.Fighter_X;
					weapon.posY = SFEngine.Fighter_Y + .5f;
				}
			}
		}
	}
	private void detectFighter(){
		for(int i = 0; i < SFEngine.EnemyCounts; i++){
			if(!fighter.isDestoryed && !enemy[i].isDestroyed && SFEngine.Enemy_Y[i] < 9){
				if(fighter.posY >= SFEngine.Enemy_Y[i] - .8f &&
						fighter.posY <= SFEngine.Enemy_Y[i] + .8f &&
						fighter.posX <= SFEngine.Enemy_X[i] + .8f &&
						fighter.posX >= SFEngine.Enemy_X[i] - .8f){
					fighter.applyDamage();
					enemy[i].applyDamage();
					Score = 0;
				}
			}
		}
	}
	private void moveFighter(GL10 gl){
		if(!fighter.isDestoryed){
			
			fighter.posX = SFEngine.Fighter_X;
			fighter.posY = SFEngine.Fighter_Y;
			
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glPushMatrix();
			gl.glScalef(.125f, .125f, 1f);
			gl.glTranslatef(fighter.posX, fighter.posY, 0f);
			gl.glMatrixMode(GL10.GL_TEXTURE);
			gl.glLoadIdentity();
			gl.glTranslatef(0.0f, 0.0f, .0f);
			fighter.draw(gl, spriteSheets[0]);
			gl.glPopMatrix();
			gl.glLoadIdentity();
		}else{
			flash++;
			SFEngine.Fighter_X = SFEngine.Fighter_Start_X;
			SFEngine.Fighter_Y = SFEngine.Fighter_Start_Y;
			if(flash <= SFEngine.FLASH_TIME){
				gl.glMatrixMode(GL10.GL_MODELVIEW);
				gl.glLoadIdentity();
				gl.glPushMatrix();
				gl.glScalef(.125f, .125f, 1f);
				gl.glTranslatef(SFEngine.Fighter_Start_X, SFEngine.Fighter_Start_Y, 0f);
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
				gl.glTranslatef(0.0f, 0.0f, 0.0f);
				fighter.draw(gl, spriteSheets[0]);
				gl.glPopMatrix();
			}else if(flash == (SFEngine.FLASH_TIME * 2)){
				if(flashcounts == 5){
					flashcounts = 0;
					fighter.isDestoryed = false;
					fighter.posX = SFEngine.Fighter_Start_X;
					fighter.posY= SFEngine.Fighter_Start_Y;
				}
				flash = -1;	
				flashcounts++;
			}
		}
	}
	private void moveEnemy(GL10 gl){
		for(int i = 0; i < SFEngine.EnemyCounts; i++){
			if(!enemy[i].isDestroyed)
			{
				if(SFEngine.Enemy_Y[i] >= 9){
					SFEngine.Enemy_X[i] = randomPos.nextFloat() * 7;
				}
				gl.glMatrixMode(GL10.GL_MODELVIEW);
				gl.glLoadIdentity();
				gl.glPushMatrix();
				gl.glScalef(.125f, .125f, 1f);
				gl.glTranslatef(SFEngine.Enemy_X[i], SFEngine.Enemy_Y[i], 0f);
				gl.glMatrixMode(GL10.GL_TEXTURE);
				gl.glLoadIdentity();
				
				if(i < SFEngine.ENEMYCOUNT_1){
					gl.glTranslatef(0.5f, 0.5f, .0f);
					if(i%4 == 0){
						SFEngine.Enemy_Y[i] -= SFEngine.SPEED_ENEMY_1_B;
						if(SFEngine.Enemy_Y[i] <= 6f){
							if(enemy[i].isLocked == false){
								enemy[i].TargetToX = ((SFEngine.Enemy_X[i]-fighter.posX) 
										/ (SFEngine.Enemy_Y[i]-fighter.posY))
										* SFEngine.SPEED_ENEMY_1_B;
								enemy[i].isLocked = true;
							}
						}
						SFEngine.Enemy_X[i] -= enemy[i].TargetToX;
						if(SFEngine.Enemy_Y[i] <= 0f){
							enemy[i].TargetToX = 0f;
							enemy[i].isLocked = false;
						}
					}else{
						SFEngine.Enemy_Y[i] -= SFEngine.SPEED_ENEMY_1;
					}
					
				}else if(i < (SFEngine.ENEMYCOUNT_1 + SFEngine.ENEMYCOUNT_2)){
					gl.glTranslatef(.25f, .5f, .0f);
					SFEngine.Enemy_Y[i] -= SFEngine.SPEED_ENEMY_2;
				}else{
					gl.glTranslatef(.0f, .5f, .0f);
					SFEngine.Enemy_Y[i] -= SFEngine.SPEED_ENEMY_3;
				}
				
				enemy[i].draw(gl, spriteSheets[2]);
				gl.glLoadIdentity();
				
				if(SFEngine.Enemy_Y[i] < -1f){
					SFEngine.Enemy_Y[i] = 9.0f + i * 1.5f;
				}
			}else{
				SFEngine.Enemy_Y[i] = (randomPos.nextFloat() + 1) * 8;
				enemy[i].isDestroyed = false;
			}
		}
	}
	private void moveWeapon(GL10 gl){
		if(weapon.shotFired){
			weapon.posY += SFEngine.SPEED_WEAPON_FIGHTER;
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glPushMatrix();
			gl.glScalef(.125f, .125f, 1f);
			gl.glTranslatef(weapon.posX, weapon.posY, 0f);
			gl.glMatrixMode(GL10.GL_TEXTURE);
			gl.glLoadIdentity();
			gl.glTranslatef(0.0f, 0.0f, 0.0f);
			weapon.draw(gl, spriteSheets[1]);
			gl.glPopMatrix();
			gl.glLoadIdentity();
			if(weapon.posY >= SFEngine.RANGE_WEAPON_FIGHTER){
				weapon.posX = SFEngine.Fighter_X;
				weapon.posY = SFEngine.Fighter_Y + .5f;
			}
		}
	}	
	private void initFighter(){
		fighter = new Fighter();
		fighter.posX = SFEngine.Fighter_Start_X;
		fighter.posY = SFEngine.Fighter_Start_Y;	
	}
	private void initEnemy(){
		for(int i = 0; i < SFEngine.EnemyCounts; i++){
			SFEngine.Enemy_X[i] = 0.0f;
			SFEngine.Enemy_Y[i] = 9.0f + i * 1.5f;
			if(i < SFEngine.ENEMYCOUNT_1){
				enemy[i] = new Enemy(SFEngine.DAMAGE_ENEMY_1);
			}else if(i < (SFEngine.ENEMYCOUNT_1 + SFEngine.ENEMYCOUNT_2)){
				enemy[i] = new Enemy(SFEngine.DAMAGE_ENEMY_2);
			}else{
				enemy[i] = new Enemy(SFEngine.DAMAGE_ENEMY_3);
			}
		}
	}
	private void initWeapon(){
		weapon = new Weapon();
		weapon.shotFired = true;
		weapon.posX = SFEngine.Fighter_X;
		weapon.posY = SFEngine.Fighter_Y + .5f;
	}
	private void initScore(){
		for(int i = 0; i < SFEngine.SCORE_LENGTH; i++){
			score[i] = new Score();
			score[i].posX = (float)i;
			score[i].poxY = 9f;
		}
	}
	private void caluSocre(GL10 gl){
		String str = Integer.toString(Score);
		
		int[] num = new int[str.length()];
		for(int i = 0; i < str.length(); i++){
			num[i] = Integer.parseInt(String.valueOf(str.charAt(i)));
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glPushMatrix();
			gl.glScalef(.1f, .1f, 1f);
			gl.glTranslatef(score[i].posX, score[i].poxY, 0f);
			gl.glMatrixMode(GL10.GL_TEXTURE);
			gl.glLoadIdentity();
			
			switch(num[i]){
			case 0:
				gl.glTranslatef(.5f, .25f, 0.0f);
				break;
			case 1:
				gl.glTranslatef(.75f, .75f, 0.0f);
				break;
			case 2:
				gl.glTranslatef(.5f, .75f, 0.0f);
				break;
			case 3:
				gl.glTranslatef(.25f, .75f, 0.0f);
				break;
			case 4:
				gl.glTranslatef(.0f, .75f, 0.0f);
				break;
			case 5:
				gl.glTranslatef(.75f, .5f, 0.0f);
				break;
			case 6:
				gl.glTranslatef(.5f, .5f, 0.0f);
				break;
			case 7:
				gl.glTranslatef(.25f, .5f, 0.0f);
				break;
			case 8:
				gl.glTranslatef(.0f, .5f, 0.0f);
				break;
			case 9:
				gl.glTranslatef(.75f, .25f, 0.0f);
				break;
			default:
				gl.glTranslatef(.5f, .25f, 0.0f);
				break;
			}			
			score[i].draw(gl, spriteSheets[3]);
			gl.glPopMatrix();
			gl.glLoadIdentity();
		}
	}	
 	private void scrollBackground1(GL10 gl){
		if(bgScroll1 == Float.MAX_VALUE){
			bgScroll1 = 0f;
		}
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glPushMatrix();
		gl.glScalef(1f, 1f, 1f);
		gl.glTranslatef(0f, 0f, 0f);
		
		gl.glMatrixMode(GL10.GL_TEXTURE);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, bgScroll1, 0.0f);
		
		skyFighterBG.draw(gl);
		gl.glPopMatrix();
		bgScroll1 += SFEngine.SCROLL_BACKGROUND_1;
		gl.glLoadIdentity();
	}
	private void scrollBackground2(GL10 gl){
		if(bgScroll2 == Float.MAX_VALUE){
			bgScroll2 = 0f;
		}
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glPushMatrix();
		gl.glScalef(1f/bili, 1f, 1f);
		gl.glTranslatef(0f, 0f, 0f);
		
		gl.glMatrixMode(GL10.GL_TEXTURE);
		gl.glLoadIdentity();
		
		gl.glTranslatef(0.0f, bgScroll2, 0.0f);
		gl.glScalef(2f, 2f, 1f);
		
		skyFighterBG2.draw(gl);
		gl.glPopMatrix();
		bgScroll2 += SFEngine.SCROLL_BACKGROUND_2;
		gl.glLoadIdentity();
	}
}
