package com.diegomeruoca.plaraforma;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.diegomeruoca.plaraforma.screens.PlayScreen;

public class Plataforma extends Game {
	public SpriteBatch batch; // Conatainer para armazenar todas imagens e textura
	public static final int V_WIDITH = 400; // Define a largura virtual e
	public static final int V_HEIGHT = 208; //a altura virtual para o jogo
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
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
