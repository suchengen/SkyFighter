package com.ds.skyfighter.Guy;


import com.ds.skyfighter.Engine.SFEngine;
import com.ds.skyfighter.GL.Vertices;

public class Fighter extends Vertices{
	
	public boolean isDestoryed = false;
	private int damage = 0;
	public float posX;
	public float posY;
	
	public void applyDamage(){
		damage++;
		if(damage == SFEngine.DAMAGE_FIGHTER){
			isDestoryed = true;
			damage = 0;
		}
		
	}
}
