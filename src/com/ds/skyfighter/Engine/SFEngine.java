package com.ds.skyfighter.Engine;

import android.content.Context;

import com.ds.skyfighter.R;


public class SFEngine {
	public static final int GAME_THREAD_DELAY = 3000;  //游戏线程的延迟
	public static final int GAME_THREAD_FPS_SLEEP = (1000/60); //游戏FPS的调整 
	
	//游戏图片资源的读取
	public static Context context;
	public static final int GAME_BACKGROUND_ONE = R.drawable.backgroundstars;
	public static final int GAME_BACKGROUND_TWO = R.drawable.star;
	public static final int GAME_FIGHTER = R.drawable.good_sprite;
	public static final int GAME_ENEMY = R.drawable.character_sprite;
	public static final int WEAPONS_SHEET = R.drawable.destruction;
	public static final int NUMBERS = R.drawable.numbers;
	
	//各种敌机的数量
	public static final int ENEMYCOUNT_1 = 12;
	public static final int ENEMYCOUNT_2 = 2;
	public static final int ENEMYCOUNT_3 = 1;
	
	//敌机的总数量
	public static final int EnemyCounts = 
			  ENEMYCOUNT_1
			+ ENEMYCOUNT_2 
			+ ENEMYCOUNT_3;  
	

	//玩家飞机的初始坐标
	public static final float Fighter_Start_X = 3.5f;
	public static final float Fighter_Start_Y = 0f;
	
	//玩家飞机的实时坐标
	public static float Fighter_X = Fighter_Start_X;
	public static float Fighter_Y = Fighter_Start_Y;
	
	
	//敌机的坐标数组
	public static float[] Enemy_X = new float[EnemyCounts];
	public static float[] Enemy_Y = new float[EnemyCounts];
	
	//背景滚动的速度
	public static float SCROLL_BACKGROUND_1 = .002f;
	public static float SCROLL_BACKGROUND_2 = .007f;
	
	//敌机的速度
	public static final float SPEED_ENEMY_1 = .05f;
	public static final float SPEED_ENEMY_1_B = .08f;
	public static final float SPEED_ENEMY_2 = .06f;
	public static final float SPEED_ENEMY_3 = .04f;
	
	public static final float SPEED_WEAPON_FIGHTER = .75f;  //玩家发射子弹的速度 
	public static final float RANGE_WEAPON_FIGHTER = 7.0f;  //玩家发射子弹的射程
	
	public static final int DAMAGE_FIGHTER = 1;             //玩家飞机最多能承受的伤害
	
	//敌机最多能承受的伤害
	public static final int DAMAGE_ENEMY_1 = 3;
	public static final int DAMAGE_ENEMY_2 = 10;
	public static final int DAMAGE_ENEMY_3 = 20;
	
	public static final int FLASH_TIME = 9;
	
	public static final float BEZIER_X_1 = 0f;
	public static final float BEZIER_X_2 = 1f;
	public static final float BEZIER_X_3 = 2.5f;
	public static final float BEZIER_X_4 = 3f;
	public static final float BEZIER_Y_1 = 0f;
	public static final float BEZIER_Y_2 = 2.4f;
	public static final float BEZIER_Y_3 = 1.5f;
	public static final float BEZIER_Y_4 = 2.6f;
	
	public static final int ATTACK_NULL = 0;
	public static final int ATTACK_LEFT = 1;
	public static final int ATTACK_RIGHT = 2;
	
	//击落敌机分数
	public static final int SCORE_ENEMY1 = 1;
	public static final int SCORE_ENEMY2 = 3;
	public static final int SCORE_ENEMY3 = 6;
	
	
	public static int SCORE = 0;
	public static int SCORE_LENGTH = 8;
}
