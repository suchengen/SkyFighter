package com.ds.skyfighter.Guy;

import com.ds.skyfighter.Engine.SFEngine;
import com.ds.skyfighter.GL.Vertices;

public class Enemy extends Vertices{
	
	public boolean isDestroyed = false;
	private int type;
	
	public boolean isLocked = false;
	public float TargetToX = 0f;
	public float posT = 0;
	public int Direction = 0;
	
	public Enemy(int type){
		this.type = type;
	}
	
	private int damage = 0;
	public void applyDamage(){
		damage++;
		switch(type){
		case SFEngine.DAMAGE_ENEMY_1:
			if(damage == SFEngine.DAMAGE_ENEMY_1){
				isDestroyed = true;
				damage = 0;
			}
			break;
		case SFEngine.DAMAGE_ENEMY_2:
			if(damage == SFEngine.DAMAGE_ENEMY_2){
				isDestroyed = true;
				damage = 0;
			}
			break;
		case SFEngine.DAMAGE_ENEMY_3:
			if(damage == SFEngine.DAMAGE_ENEMY_3){
				isDestroyed = true;
				damage = 0;
			}
			break;
		default:
			break;
		}
	}
	
	public float getNextScoutX(){
		switch(Direction){
		case SFEngine.ATTACK_LEFT:
			return (float)(SFEngine.BEZIER_X_1*((1-posT)*(1-posT)*(1-posT)))
					+ (3*SFEngine.BEZIER_X_2*posT*((1-posT)*(1-posT)))
					+ (3*SFEngine.BEZIER_X_3*posT*posT*((1-posT)))
					+ (SFEngine.BEZIER_X_4*posT*posT*posT);
		case SFEngine.ATTACK_RIGHT:
			return (float)(SFEngine.BEZIER_X_4*((1-posT)*(1-posT)*(1-posT)))
					+ (3*SFEngine.BEZIER_X_3*posT*((1-posT)*(1-posT)))
					+ (3*SFEngine.BEZIER_X_2*posT*posT*((1-posT)))
					+ (SFEngine.BEZIER_X_1*posT*posT*posT);
		default:
			return 0f;
		}
	}
	public float getNextScoutY(){
		return (float)((SFEngine.BEZIER_Y_4 * ((1-posT)*(1-posT)*(1-posT)))
				+ (3*SFEngine.BEZIER_Y_3*posT*((1-posT)*(1-posT)))
				+ (3*SFEngine.BEZIER_Y_2*posT*posT*(1-posT))
				+ (SFEngine.BEZIER_Y_1*posT*posT*posT));
	}
	
	
}
