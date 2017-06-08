package com.mygdx.greenz.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.greenz.MainGame;

/**
 * Esta clase indicará que hemos perdido la partida, en ella habrá un botón con el que podremos
 * volver al juego desde cero.
 */
public class GameOverScreen extends BaseScreen {

    /**
     * Escenario que utilizamos para añadir los elementos necesarios que queremos visualizar en pantalla
     */
    private Stage escenario;

    /** Botón para volver al juego */
    private TextButton botonJuego;

    /** Estilos de los botones, nos es necesario para añadir la imagen del Skin a nuestros botones */
    private TextButton.TextButtonStyle textButtonStyle;

    /** Coleccion de Texturas en una sola imagen, en la cual podremos acceder facilmente a ellas */
    private TextureAtlas textureAtlas;

    /** Con el Skin recogemos el Atlas con el que podremos pasarselo a los ButtonStyle */
    private Skin skin;

    /** La fuente es necesaria para pasarselo a los ButtonStyle aunque estos no utilicen ninguna fuente */
    private BitmapFont font;

    /** La salida de pantalla donde tenedremos una perspectiva para ver*/
    private Viewport viewport;

    /** Sonido que se activa cuando entramos a esta pantalla para determinar que hemos muerto */
    private Sound sonidoMuerte;

    /**
     *
     * @param mainGame para establecer la screen que nos interese.
     */

    public GameOverScreen(final MainGame mainGame) {
        super(mainGame);

        /** Iniciamos el puerto de vista pasandole el ancho y el alto y una camara */
        viewport = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, new OrthographicCamera());

        /** Inicializamos el escenario pasandole el puerto de vista y el SpriteBatch del dibujado */
        escenario = new Stage(viewport, mainGame.batch);

        /** Inicializamos el sonido recogiendo el sonido */
        sonidoMuerte = mainGame.getManager().get("gover.mp3");

        /** Iniciamos el sonido */
        sonidoMuerte.play();

        /** Definimos el estilo del Label para los textos a utilizar en esta pantalla */
        Label.LabelStyle fontL = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        /** Creamos una tabla para añadir los label que creemos */
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        /** Creamos los label */
        Label gameOverLabel = new Label("GAME OVER", fontL);
        Label playAgainLabel = new Label("Click para Jugar de Nuevo", fontL);

        /** Los añadimos a la tabla los label*/
        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);

        /** Añadimos la tabla al escenario */
        escenario.addActor(table);

        /** Inicializamos la coleccion de Sprites del HUD*/
        textureAtlas = new TextureAtlas("hud.pack");

        /** Inicilizamos la fuente de los textos */
        font = new BitmapFont();

        /** Inicializamos el skin que definiremos el atlas, con ello podremos darle al boton la interfaz
         * visual que deseemos (insertarle una imagen al botón) */
        skin = new Skin(textureAtlas);

        /** Inicializamos los Estilos de texto de los botones*/
        textButtonStyle = new TextButton.TextButtonStyle();

        /** definimos la fuente y la imagen al estilo y se lo pasamos al constructor del boton */
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("arrow_button_active.");
        botonJuego = new TextButton("", textButtonStyle);

        /** Añadimos el listener del botón creando una clase anonima */
        botonJuego.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                /** Si es pulsado creará un GameScreen */
                mainGame.setScreen(new GameScreen(mainGame));
            }
        });

        /** Definimos la posicion y tamaño del boton */
        botonJuego.setBounds(64,50 ,40,40);

        /** Añadimos el boton al escenario */
        escenario.addActor(botonJuego);

    }

    @Override
    public void show() {
        /** defininmos el inputProcessor al escenario para que detecte los toques de pantalla en los
         * botones
         */
        Gdx.input.setInputProcessor(escenario);
    }

    @Override
    public void render(float delta) {
        /** Limpiamos la pantalla */
        Gdx.gl.glClearColor(0, 0.5f, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /** Actualizamos y dibujamos el escenario */
        escenario.act();
        escenario.draw();
    }
}
