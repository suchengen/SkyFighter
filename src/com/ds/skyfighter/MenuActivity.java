package com.ds.skyfighter;

import com.ds.skyfighter.Engine.SFEngine;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_menu);
		SFEngine.context = getApplicationContext();
		
		ImageButton startbtn = (ImageButton)findViewById(R.id.btnStart);
		ImageButton exitbtn = (ImageButton)findViewById(R.id.btnexit);
		startbtn.getBackground().setAlpha(0);
		startbtn.setHapticFeedbackEnabled(true);
		exitbtn.getBackground().setAlpha(0);
		exitbtn.setHapticFeedbackEnabled(true);
		
		startbtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent gameintent = new Intent(MenuActivity.this, GameActivity.class);
				MenuActivity.this.startActivity(gameintent);
				MenuActivity.this.finish();
			}});
		
		exitbtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				AlertDialog alert = new AlertDialog.Builder(MenuActivity.this)
					.setTitle("游戏提示：")
					.setMessage("是否退出游戏？")
					.create();
				alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
				alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						android.os.Process.killProcess(android.os.Process.myPid());
					}
				});
				alert.show();
			}});
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			AlertDialog alert = new AlertDialog.Builder(MenuActivity.this)
				.setTitle("游戏提示：")
				.setMessage("是否退出游戏？")
				.create();
			alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			});
			alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			});
			alert.show();
			return true;
		}
		return false;
	}
	
	
}
