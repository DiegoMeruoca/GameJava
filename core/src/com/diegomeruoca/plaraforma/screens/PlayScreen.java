package com.diegomeruoca.plaraforma.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.diegomeruoca.plaraforma.Plataforma;
import com.diegomeruoca.plaraforma.scenes.Hud;

public class PlayScreen implements Screen {
    private Plataforma game; // Instacia um objeto do tipo Paltaforma
    private Hud hud;
    private OrthographicCamera camera;
    private Viewport viewport;

    public PlayScreen(Plataforma game) {
        this.game = game;
        camera = new OrthographicCamera(); // Cria um objeto do tipo camera
        viewport = new FitViewport(Plataforma.V_WIDITH, Plataforma.V_HEIGHT, camera); // Cria um ajanela de exibição
        /*StrechViewport -  vai esticar o conteudo pra ajustar ao tamanho da janela, distorcendo o conteúdo dependendo
        da proporção.
        FitViewport -  vai aumentar o tamanho do conteudo acompanhando a janela, mas caso o tamanho não seja
        proporcional, adiciona barras pretas nas lateria ou em cima e embaixo.**/
        hud = new Hud(game.batch);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1); //Define a cor do fundo de preto
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//PInta o fundo
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined); //Seta a matriz de de projeção, pro batch reconhecer o que e a camera "vê"
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height); //Atualiza a visualização qnd redimensionar a janela
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
