package com.ds.skyfighter;

import com.ds.skyfighter.Engine.SFEngine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class StartLOGOActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_start_logo);
		
		new Handler().postDelayed(new Thread(){
			@Override
			public void run() {
				Intent mainMenu = new Intent(StartLOGOActivity.this, MenuActivity.class);
				StartLOGOActivity.this.startActivity(mainMenu);
				StartLOGOActivity.this.finish();
				overridePendingTransition(R.anim.fadein, R.anim.fadeout);
			}
			
		}, SFEngine.GAME_THREAD_DELAY);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return true;
	}
	
	
}
	
