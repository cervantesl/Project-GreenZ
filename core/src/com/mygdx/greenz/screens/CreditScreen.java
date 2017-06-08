package com.mygdx.greenz.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.greenz.MainGame;


/**
 * Clase para la pantalla de creditos donde mostraremos los creditos, fuentes, etc del juego
 */
public class CreditScreen extends BaseScreen {

    /** La salida de pantalla donde tenedremos una perspectiva para ver*/
    private Viewport viewport;

    /**
     * Escenario que utilizamos para añadir los elementos necesarios que queremos visualizar en pantalla
     */
    private Stage escenario;

    /** Con el Skin recogemos el Atlas con el que podremos pasarselo a los ButtonStyle */
    private Skin skin;

    /** Estilos de los botones, nos es necesario para añadir la imagen del Skin a nuestros botones */
    private TextButton.TextButtonStyle textButtonStyle;

    /** La fuente es necesaria para pasarselo a los ButtonStyle aunque estos no utilicen ninguna fuente */
    private BitmapFont font;

    /** Texto planto que podremos insertar a nuestro escrenario */
    private Label label;

    /** Botón para ir al menu */
    private TextButton botonMenu;

    /**
     *
     * @param mainGame el parametro mainGame es comun a todas las clases Screen que extiende de nuestra
     *                 clase BaseScreen. Lo utilizaremos a la hora de utilizar el batch o hacer un setScreen
     */
    public CreditScreen(final MainGame mainGame) {
        super(mainGame);

        /** Iniciamos el puerto de vista pasandole el ancho y el alto y una camara */
        viewport = new FitViewport(640, 360, new OrthographicCamera());

        /** Inicializamos el escenario pasandole el puerto de vista y el SpriteBatch del dibujado */
        escenario = new Stage(viewport, mainGame.batch);

        /** Inicializamos el skin que definiremos el atlas, con ello podremos darle al boton la interfaz
         * visual que deseemos (insertarle una imagen al botón) */
        skin = new Skin(new TextureAtlas("hud.pack"));

        /** Inicilizamos la fuente de los textos */
        font = new BitmapFont();

        /** Inicializamos los Estilos de texto de los botones*/
        textButtonStyle = new TextButton.TextButtonStyle();

        /** definimos la fuente y la imagen al estilo y se lo pasamos al constructor del boton */
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("arrow_button_active.");

        /** Creamos el texto de los creditos */
        String texto = "Este juego está hecho por Lucas Cervantes para el proyecto final del grado superior \n" +
                "Desarrollo de Aplicaciones Multiplataforma. Listo las fuentes del contenido utilizado en \n" +
                "este juego. Detallar que todo lo utilizado tiene licencia OpenSource: \n" +
                "\n" +
                "-Sprites del personaje Heroe: OpenGameArt.com \n" +
                "-Sprites del personaje Enemigo: OpenGameArt.com \n" +
                "-Sprites del Mapa: OpenGameArt.com \n" +
                "-Sonido del salto del personaje: FreeSound.org \n" +
                "-Sonido del gruñido del zombie: FreeSound.org \n" +
                "-Musica de fondo del menu: SoundCloud.com *Artista: COSTAPA \n" +
                "-Musica de fondo del nivel: SoundCloud.com *Artista: COSTAPA";

        /** Instanciamos el label pasandole el texto */
        label = new Label(texto, new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        /** Definimos la posición y tamaño del label */
        label.setBounds(20, 20, 200, 400);

        /** Inicializamos el boton para volver al menu */
        botonMenu = new TextButton("Menu", textButtonStyle);

        /** Añadimos el listener del botón creando una clase anonima */
        botonMenu.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                /** Si es pulsado creará un MenuScreen */
                mainGame.setScreen(new MenuScreen(mainGame));
            }
        });

        /** Definimos el tamaño y la posición del botón */
        botonMenu.setSize(160, 80);
        botonMenu.setPosition(40, 25);

        /** Añadimos el label y el menu al escenario */
        escenario.addActor(label);
        escenario.addActor(botonMenu);


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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /** Actualizamos y dibujamos el escenario */
        escenario.act();
        escenario.draw();
    }
}
