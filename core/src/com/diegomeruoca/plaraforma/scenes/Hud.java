package com.diegomeruoca.plaraforma.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.diegomeruoca.plaraforma.Plataforma;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import java.awt.*;

public class Hud implements Disposable {
    public Stage stage; //Armazenará a tela
    public Viewport viewport; //Janela de exibição exclusiva do hud

    private Integer cronometroMapa; //Controar o tempo no mata
    private float timeCount; //Contador de tempo
    private Integer score; //Marcar a pontuação

    Label countDownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label personagemLabel;

    public Hud(SpriteBatch sb){
        cronometroMapa = 300; //Definir valores iniciais do tempo do mapa, contador de tempo e pontuação
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(Plataforma.V_WIDITH, Plataforma.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);


        Table table = new Table(); //Cria um atabela para armazenar os Widgets do hud
        table.top(); //Os itens serão alinhas no top da tabela
        table.setFillParent(true); //A tabela se alinhara ao tamanho so Stage

        //Adicionar o valor das variaveis nos labels
        countDownLabel = new Label(String.format("%03d", cronometroMapa)
                , new Label.LabelStyle(new BitmapFont(), Color.WHITE)); // Define o estilo de label pra bitmap font branca
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME" , new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1" , new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD" , new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        personagemLabel = new Label("PERSONAGEM" , new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //Adicionando os labels na tabela
        table.add(personagemLabel).expandX().padTop(10); /*Adicionou o nome do personagem se epandido na horizontal (se
        tiverem oureos itens na mesma linha, dividirão a linha) com 10 pixels de distancia do topo*/
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row(); //Adiciona uma nova linha na tabela
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countDownLabel).expandX();

        //Adicionando a tabela ao nosso stage
        stage.addActor(table);

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
