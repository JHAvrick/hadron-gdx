package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import aurelienribon.tweenengine.Tween;
import base.state.GameMaster;
import state.FilterTestState;
import state.MainGame;
import state.MainMenu;

public class Hadron extends GameMaster {

	public MainMenu menu;
	public MainGame main;

	@Override
	public void create () {
		batch = new SpriteBatch();

		Tween.setCombinedAttributesLimit(4);

		menu = new MainMenu(this, 720, 1280);
		//main = new MainGame(this, 720, 1280);
		//filterTests = new FilterTestState(this, 720, 1280);


		setScreen(menu);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
