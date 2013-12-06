package com.ds.skyfighter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.ds.skyfighter.Engine.SFEngine;

public class StartBGActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_start_bg);
		
		new Handler().postDelayed(new Thread(){

			@Override
			public void run() {
				Intent mainMenu = new Intent(StartBGActivity.this, StartLOGOActivity.class);
				StartBGActivity.this.startActivity(mainMenu);
				StartBGActivity.this.finish();
				overridePendingTransition(R.anim.fadein, R.anim.fadeout);
				
			}
			
		}, SFEngine.GAME_THREAD_DELAY);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return true;
	}
}
