package com.mygdx.greenz.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.greenz.MainGame;

/**
 * Esta clase mostrará el panel de comandos para el control del jugador. Se compone principalmente
 * por tres botones: Izquierda, Arriba y Derecha. Accede a un Altas para poder cambiar la imagen
 * de los botones, al igual que sus fuentes (que no se utiliza en este caso).
 * Estos botones se añade a un Escenario con el que podremos llamar para dibujar en el GameScreen
 */

public class Hud {

    /**
     * Escenario que utilizamos para añadir los elementos necesarios que queremos visualizar en pantalla
     */
    public Stage escenario;

    /** La salida de pantalla donde tenedremos una perspectiva para ver*/
    public FitViewport viewPort;

    /** Botones de control del jugador */
    public TextButton botonIzquierdo;
    public TextButton botonDerecho;
    public TextButton botonArriba;

    /** Estilos de los botones, nos es necesario para añadir la imagen del Skin a nuestros botones */
    private TextButton.TextButtonStyle textButtonStyle;
    private TextButton.TextButtonStyle textButtonStyle2;
    private TextButton.TextButtonStyle textButtonStyle3;

    /** Con el Skin recogemos el Atlas con el que podremos pasarselo a los ButtonStyle */
    private Skin skin;

    /** La fuente es necesaria para pasarselo a los ButtonStyle aunque estos no utilicen ninguna fuente */
    private BitmapFont font;

    /** Coleccion de Texturas en una sola imagen, en la cual podremos acceder facilmente a ellas */
    private TextureAtlas textureAtlas;

    /** Tablas para añadir los botones y estos al Escenario */
    private Table table;
    private Table table2;


    public Hud(SpriteBatch sb) {

        /** Iniciamos el puerto de vista pasandole el ancho y el alto y una camara */
        viewPort = new FitViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, new OrthographicCamera());

        /** Inicializamos el escenario pasandole el puerto de vista y el SpriteBatch del dibujado */
        escenario = new Stage(viewPort, sb);

        /** Inicializamos los Estilos de texto de los botones*/
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle2 = new TextButton.TextButtonStyle();
        textButtonStyle3 = new TextButton.TextButtonStyle();

        /** Inicializamos la coleccion de Sprites del HUD*/
        textureAtlas = new TextureAtlas("hud2.pack");

        /** Inicilizamos la fuente de los textos */
        font = new BitmapFont();

        /** Inicializamos el skin que definiremos el atlas, con ello podremos darle al boton la interfaz
         * visual que deseemos (insertarle una imagen al botón) */
        skin = new Skin(textureAtlas);

        /** definimos la fuente y la imagen al estilo y se lo pasamos al constructor del boton */
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("arrow_button_active.");
        botonIzquierdo = new TextButton("", textButtonStyle);

        textButtonStyle2.font = font;
        textButtonStyle2.up = skin.getDrawable("arrow_button_idle.");
        botonDerecho = new TextButton("", textButtonStyle2);

        textButtonStyle3.font = font;
        textButtonStyle3.up = skin.getDrawable("Magic_button_green");
        botonArriba = new TextButton("", textButtonStyle3);

        /** Inicializamos las dos tablas pasandole la skin */
        table = new Table(skin);
        table2 = new Table(skin);

        /** Definimos las positiones de las tablas */
        table.setPosition(40, 20);
        table2.setPosition(400, 20);

        /** Añadimos los botones y definimos sus posiciones en la tabla */
        table.add(botonIzquierdo).width(40).height(40);
        table.add(botonDerecho).width(40).height(40);
        table2.add(botonArriba).width(40).height(40);

        /** Añadimos al escenario los dos actores (tablas) */
        escenario.addActor(table);
        escenario.addActor(table2);

        /** defininmos el inputProcessor al escenario para que detecte los toques de pantalla en los
         * botones
         */
        Gdx.input.setInputProcessor(escenario);
    }
}
