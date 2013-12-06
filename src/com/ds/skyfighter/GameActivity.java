package com.ds.skyfighter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.ds.skyfighter.Engine.SFEngine;
import com.ds.skyfighter.GLView.SkyFighterView;

public class GameActivity extends Activity {
	
	SkyFighterView skyfighterView;  //游戏界面
	int width;                      
	int height;
	float tempX;
	float tempY;
	float tempFighterX;
	float tempFighterY;
	
	float ResumeFighterX;
	float ResumeFighterY;
	float[] tempEnemiesX = new float[SFEngine.EnemyCounts];
	float[] tempEnemiesY = new float[SFEngine.EnemyCounts];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		skyfighterView = new SkyFighterView(this);
		setContentView(skyfighterView);
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		height = dm.heightPixels;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			tempX = event.getX();
			tempY = event.getY();
			tempFighterX = SFEngine.Fighter_X;
			tempFighterY = SFEngine.Fighter_Y;
			break;
		case MotionEvent.ACTION_MOVE:
			SFEngine.Fighter_X = tempFighterX + ((event.getX() - tempX)/width*8);
			SFEngine.Fighter_Y = tempFighterY + ((-(event.getY() - tempY)/height)*8);
			if(SFEngine.Fighter_X < 0){
				SFEngine.Fighter_X = 0;
			}
			if(SFEngine.Fighter_X > 7){
				SFEngine.Fighter_X = 7;
			}
			if(SFEngine.Fighter_Y < 0){
				SFEngine.Fighter_Y = 0;
			}
			if(SFEngine.Fighter_Y > 7){
				SFEngine.Fighter_Y = 7;
			}
			break;
		default:
			break;	
		}
		//if(event.getAction() == MotionEvent.ACTION_DOWN){}
		//if(event.getAction() == MotionEvent.ACTION_MOVE){}
		return true;
	}
	
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			
			ResumeFighterX = SFEngine.Fighter_X;
			ResumeFighterY = SFEngine.Fighter_Y;
			
			for(int i = 0; i < SFEngine.EnemyCounts; i++){
				tempEnemiesX[i] = SFEngine.Enemy_X[i];
				tempEnemiesY[i] = SFEngine.Enemy_Y[i];
			}
			
			skyfighterView.onPause();
			String[] items = new String[]{"继续游戏", "重新开始","退出游戏"};
			Builder builder = new AlertDialog.Builder(GameActivity.this);
			builder.setTitle("系统提示：");
			builder.setItems(items, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch(which){
					case 0:
						skyfighterView.onResume();
						SFEngine.Fighter_X = ResumeFighterX;
						SFEngine.Fighter_Y = ResumeFighterY;
						for(int i = 0; i < SFEngine.EnemyCounts; i++){
							SFEngine.Enemy_X[i] = tempEnemiesX[i];
							SFEngine.Enemy_Y[i] = tempEnemiesY[i];
						}
						break;
					case 1:
						skyfighterView.onResume();
						break;
					case 2:
						Intent munuIntent = new Intent(GameActivity.this, MenuActivity.class);
						GameActivity.this.startActivity(munuIntent);
						GameActivity.this.finish();
						break;
					}
				}
			});
			builder.create().show();
		}
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		skyfighterView.onPause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		skyfighterView.onResume();
	}
	
}
