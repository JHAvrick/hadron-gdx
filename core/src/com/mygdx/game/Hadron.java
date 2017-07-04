package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import base.GameMaster;
import state.MainGame;
import state.MainMenu;

public class Hadron extends GameMaster {

	public MainMenu menu;
	public MainGame main;

	@Override
	public void create () {
		batch = new SpriteBatch();

		menu = new MainMenu(this, 720, 1280);
		//main = new MainGame(this, 720, 1280);

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
