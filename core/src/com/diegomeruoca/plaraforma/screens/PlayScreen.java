package com.diegomeruoca.plaraforma.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.diegomeruoca.plaraforma.Plataforma;
import com.diegomeruoca.plaraforma.scenes.Hud;
import com.diegomeruoca.plaraforma.sprites.Mario;
import com.diegomeruoca.plaraforma.tools.B2WoldCreator;

public class PlayScreen implements Screen {
    private Plataforma game; // Instacia um objeto do tipo
    private TextureAtlas atlas;

    //Playsreen variables
    private Hud hud;
    private OrthographicCamera gamecam;
    private Viewport gameport;

    //Tiled map variables
    private TmxMapLoader maploader; // Para carregar nosso mapa
    private TiledMap map; // Para armazenar nosso mapa
    private OrthogonalTiledMapRenderer renderer; // Pra renderizar o mapa pra tela

    //Box2d variables
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer; //Representação grafica de fixtures e bodies

    private Mario player;

    public PlayScreen(Plataforma game) {
        this.game = game;

        atlas = new TextureAtlas("Mario_and_Enemies.pack");

        gamecam = new OrthographicCamera(); // Cria um objeto do tipo camera
        gameport = new FitViewport(Plataforma.V_WIDITH / Plataforma.PPM, Plataforma.V_HEIGHT / Plataforma.PPM, gamecam); // Cria um ajanela de exibição
        /*StrechViewport -  vai esticar o conteudo pra ajustar ao tamanho da janela, distorcendo o conteúdo dependendo
        da proporção.
        FitViewport -  vai aumentar o tamanho do conteudo acompanhando a janela, mas caso o tamanho não seja
        proporcional, adiciona barras pretas nas lateria ou em cima e embaixo.**/
        hud = new Hud(game.batch); //Vria o Hud do game para pontuação tempo e etc
        //Carregar o mapa e configurar a redenderização dele
        maploader = new TmxMapLoader();
        map = maploader.load("mapa.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Plataforma.PPM);
        //Configurção da camera
        gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0,-10), true); //Vector2 com 0 0 esta sem gravidade, do Sleep True, evita calcular a disica de objetos que estão dormindo
        box2DDebugRenderer = new Box2DDebugRenderer();

        new B2WoldCreator(world, map);

        player = new Mario(world, this); //Criando o mario no mapa
    }

    @Override
    public void show() {

    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    private void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) { //Se pressiona a seta pra cima
            //Sera aplicado um impulso linear vertical no centro co corpo, se estiver dormindo sera acordado
            player.b2dBody.applyLinearImpulse(new Vector2(0, 4f), player.b2dBody.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2dBody.getLinearVelocity().x <= 2) { //Se pressiona a seta pra cima
            //Sera aplicado um impulso linear vertical no centro co corpo, se estiver dormindo sera acordado
            player.b2dBody.applyLinearImpulse(new Vector2(0.1f, 0), player.b2dBody.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2dBody.getLinearVelocity().x >= -2) { //Se pressiona a seta pra cima
            //Sera aplicado um impulso linear vertical no centro co corpo, se estiver dormindo sera acordado
            player.b2dBody.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2dBody.getWorldCenter(), true);
        }
    }

    public void update(float dt){
        handleInput(dt);
        world.step(1/60f, 6, 2);
        player.update(dt);
        gamecam.position.x = player.b2dBody.getPosition().x;
        gamecam.update();
        renderer.setView(gamecam);

    }



    @Override
    public void render(float delta) {
        update(delta); //Caha a atualização

        Gdx.gl.glClearColor(0,0,0,1); //Define a cor do fundo de preto
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//PInta o fundo

        renderer.render(); // renderiza o mapa

        box2DDebugRenderer.render(world, gamecam.combined);//Renderizar box2dDebugline (linhas verdes em volta dos elementos

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

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
        map.dispose();
        renderer.dispose();
        world.dispose();
        box2DDebugRenderer.dispose();
        hud.dispose();

    }
}
