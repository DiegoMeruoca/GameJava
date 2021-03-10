package com.diegomeruoca.plaraforma;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.diegomeruoca.plaraforma.screens.PlayScreen;

public class Plataforma extends Game {
	public SpriteBatch batch; // Conatainer para armazenar todas imagens e textura
	public static final int V_WIDITH = 400; // Define a largura virtual e
	public static final int V_HEIGHT = 208; //a altura virtual para o
	public static final float PPM = 100; //Pixels por metro

	//Criando filtros para colisão - Usando logica binaria
	public static final short DEFAULT_BIT = 1;
	public static final short MARIO_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short PIPE_BIT = 32;

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
