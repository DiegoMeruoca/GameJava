package com.diegomeruoca.plaraforma.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.diegomeruoca.plaraforma.Plataforma;
import com.diegomeruoca.plaraforma.scenes.Hud;

public class PlayScreen implements Screen {
    private Plataforma game; // Instacia um objeto do tipo Paltaforma
    private Hud hud;
    private OrthographicCamera gamecam;
    private Viewport gameport;

    private TmxMapLoader maploader; // Para carregar nosso mapa
    private TiledMap map; // Para armazenar nosso mapa
    private OrthogonalTiledMapRenderer renderer; // Pra renderizar o mapa pra tela

    public PlayScreen(Plataforma game) {
        this.game = game;
        gamecam = new OrthographicCamera(); // Cria um objeto do tipo camera
        gameport = new FitViewport(Plataforma.V_WIDITH, Plataforma.V_HEIGHT, gamecam); // Cria um ajanela de exibição
        /*StrechViewport -  vai esticar o conteudo pra ajustar ao tamanho da janela, distorcendo o conteúdo dependendo
        da proporção.
        FitViewport -  vai aumentar o tamanho do conteudo acompanhando a janela, mas caso o tamanho não seja
        proporcional, adiciona barras pretas nas lateria ou em cima e embaixo.**/
        hud = new Hud(game.batch);
        maploader = new TmxMapLoader();
        map = maploader.load("mapa.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);
    }

    @Override
    public void show() {

    }

    private void handleInput(float dt) {
        if (Gdx.input.isTouched())
            gamecam.position.x += 100 * dt;
    }

    public void update(float dt){
        handleInput(dt);
        gamecam.update();
        renderer.setView(gamecam);
    }



    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1); //Define a cor do fundo de preto
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//PInta o fundo
        renderer.render();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined); //Seta a matriz de de projeção, pro batch reconhecer o que e a camera "vê"
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width, height); //Atualiza a visualização qnd redimensionar a janela
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
