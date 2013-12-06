package com.ds.skyfighter.GLView;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class SkyFighterView extends GLSurfaceView {
	SkyFighterRenderer renderer;
	public SkyFighterView(Context context) {
		super(context);
		renderer = new SkyFighterRenderer();
		this.setRenderer(renderer);
	}
}
