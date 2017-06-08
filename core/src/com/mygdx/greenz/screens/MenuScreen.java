package com.mygdx.greenz.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.greenz.MainGame;

/**
 * Clase que mostrará la pantalla de inicio
 */
public class MenuScreen extends BaseScreen{

    /**Clases de Scene2D */
    private Stage escenario;

    private Skin skin;

    private Image logo;

    private TextButton botonPlay, botonCreditos;

    /** Estilos de los botones, nos es necesario para añadir la imagen del Skin a nuestros botones */
    private TextButton.TextButtonStyle textButtonStyle;

    /** La fuente es necesaria para pasarselo a los ButtonStyle aunque estos no utilicen ninguna fuente */
    private BitmapFont font;

    /** La salida de pantalla donde tenedremos una perspectiva para ver*/
    private Viewport viewport;

    /** Musica de fondo */
    private Music backgroundMusic;

    /**@param mainGame */
    public MenuScreen(final MainGame mainGame) {
        super(mainGame);

        viewport = new FitViewport(640, 360, new OrthographicCamera());

        escenario = new Stage(viewport, mainGame.batch);

        backgroundMusic = mainGame.getManager().get("br.mp3");

        skin = new Skin(new TextureAtlas("hud.pack"));

        font = new BitmapFont();

        textButtonStyle = new TextButton.TextButtonStyle();

        textButtonStyle.font = font;

        textButtonStyle.up = skin.getDrawable("arrow_button_active.");

        botonPlay = new TextButton("Play", textButtonStyle);
        botonCreditos = new TextButton("Credits", textButtonStyle);

        logo = new Image(mainGame.getManager().get("logo.png", Texture.class));

        botonPlay.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Take me to the game screen!
                mainGame.setScreen(new GameScreen(mainGame));

                backgroundMusic.stop();

            }
        });

        botonCreditos.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainGame.setScreen(new CreditScreen(mainGame));

                backgroundMusic.stop();

            }
        });


        logo.setPosition(440 - logo.getWidth() / 2, 320 - logo.getHeight());
        botonPlay.setSize(200, 80);
        botonCreditos.setSize(200, 80);
        botonPlay.setPosition(40, 140);
        botonCreditos.setPosition(40, 40);

        escenario.addActor(botonPlay);
        escenario.addActor(logo);
        escenario.addActor(botonCreditos);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(escenario);

        backgroundMusic.setVolume(10);
        backgroundMusic.play();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        // Dispose assets.
        escenario.dispose();
        skin.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.5f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        escenario.act();
        escenario.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
