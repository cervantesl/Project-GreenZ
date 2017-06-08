package com.mygdx.greenz.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.greenz.MainGame;
import com.mygdx.greenz.scenes.Hud;
import com.mygdx.greenz.sprites.Enemy;
import com.mygdx.greenz.sprites.Hero;
import com.mygdx.greenz.tools.B2WorldCreator;
import com.mygdx.greenz.tools.Controller;
import com.mygdx.greenz.tools.WorldContactListener;

import java.util.ArrayList;

/**
 * Está será la clase más importante del proyecto, ya que en ella estará el motor del juego
 * que dibujaremos la pantalla de juego principal.
 */
public class GameScreen extends com.mygdx.greenz.screens.BaseScreen {

    /** */
    private OrthographicCamera camera;

    /** La salida de pantalla donde tenedremos una perspectiva para ver*/
    private FitViewport gamePort;

    /** Nuestra clase Hud */
    private Hud hud;

    /** Nuestra clase Hero*/
    private Hero heroe;

    /** Lista de enemigos*/
    private ArrayList<Enemy> enemigos;

    /** Atlas bara obtener datos de nuestra hoja de sprites*/
    private TextureAtlas atlas;

    /** Clases de Tiled */
    private TmxMapLoader cargadorMapa;
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer dibujadoMapa;

    /** Clases de Box2D */
    private Box2DDebugRenderer box2DDebugRenderer;
    private World world;
    private B2WorldCreator b2WorldCreator;

    /** Musica y sonido */
    private Music backgroundMusic;
    private Sound sonidoZombie;

    public MainGame mainGame;

    /**
     *
     * @param mainGame el parametro mainGame es comun a todas las clases Screen que extiende de nuestra
     *                 clase BaseScreen. Lo utilizaremos a la hora de utilizar el batch o hacer un setScreen
     */
    public GameScreen(MainGame mainGame) {
        super(mainGame);

        this.mainGame = mainGame;

    }

    @Override
    public void show() {

        /** Incializamos la camara */
        camera = new OrthographicCamera();

        /** Incializamos el puerto de vista con las medidas y camara de juego */
        gamePort = new FitViewport(MainGame.V_WIDTH / MainGame.PPM, MainGame.V_HEIGHT / MainGame.PPM, camera);

        /** Definimos la posición de la camara */
        camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        /** Inicializamos y cargamos sonido y musica*/
        backgroundMusic = mainGame.getManager().get("tr.mp3");
        sonidoZombie = mainGame.getManager().get("zomb.mp3");

        /** Inicializamos el mapa, el cargador y el dibujado del mapa Tiled*/
        mapa = new TiledMap();
        cargadorMapa = new TmxMapLoader();

        mapa = cargadorMapa.load("level1_p2.tmx");

        dibujadoMapa = new OrthogonalTiledMapRenderer(mapa, 1 / MainGame.PPM);

        /** Inicializamos el dibujado "debug" para poder ver las delimitaciones de los cuerpos */
        box2DDebugRenderer = new Box2DDebugRenderer();

        /** Inicializamos el mundo pasandole un Vector2 donde definimos la gravedad en el eje
         * X e Y. El boolean hace refencia a que si queremos que cuando un cuerpo este en reposo
         * deje de hacer calculos y libere espacio de procesamiento
         */
        world = new World(new Vector2(0, -10), true);

        /** Iniciamos nuestra clase creada con la que definiremos los cuerpos que compone nuestro mapa */
        b2WorldCreator = new B2WorldCreator(world, mapa);

        /** Iniciamos el HUD (botones de controles del personaje), pasandole nuestro batch de dibujado */
        hud = new Hud(mainGame.batch);

        /** Inicializamos la coleccion de Sprites del personaje */
        atlas = new TextureAtlas("personajes.pack");

        /** Inicializamos nuestra clase Heroe pasandole el mundo y el GameScreen*/
        heroe = new Hero(world, this);

        /** Inicializamos el array de enemigos y añadimos los enemigos al array definiendole
         * el mundo, el MainGame, y la posición en el eje X e Y
         */
        enemigos = new ArrayList<Enemy>();

        enemigos.add(new Enemy(world, this, 3, .64f));
        enemigos.add(new Enemy(world, this, 6.2f, .80f));
        enemigos.add(new Enemy(world, this, 10, .64f));
        enemigos.add(new Enemy(world, this, 30, .64f));
        enemigos.add(new Enemy(world, this, 33, .64f));
        enemigos.add(new Enemy(world, this, 36, .64f));


        /** Instanciamos e inicializamos nuestra clase WorldContactListener para indentificar colisiones
         * en el mapa y se lo definimos al mundo.
         */
        WorldContactListener wcl = new WorldContactListener(b2WorldCreator, heroe, backgroundMusic, mainGame, enemigos, sonidoZombie);

        world.setContactListener(wcl);


        /** Definimos el volumen de la musica de fondo y lo iniciamos*/
        backgroundMusic.setVolume(10);
        backgroundMusic.play();
    }

    /** getter */
    public TextureAtlas getAtlas() {
        return atlas;
    }

    /**@param delta parametro de delta (tiempo entre la ultima actualización de juego */
    public void update(float delta) {
        /** Definimos la actualización del mundo*/
        world.step(1 / 60f, 6, 2);

        /** Actualización de la posición y textura del heroe */
        heroe.update(delta);

        /** Recorrer array de enemigos para actualizar su posición y textura */
        for (Enemy enemy : enemigos) {
            enemy.update(delta);
        }

        /** Control de la camara para que no se vea lo que hay antes y despues del mapa */
        if(heroe.b2body.getPosition().x > 2.4f && heroe.b2body.getPosition().x < 35) {
            camera.position.x = heroe.b2body.getPosition().x;
        }

        /** Control de que el jugador a llegado al final del mapa */
        if(heroe.b2body.getPosition().x >= 37) {
            backgroundMusic.stop();
            mainGame.setScreen(new MenuScreen(mainGame));
        }

        /** Clase de control del HUD de juego */
        new Controller(hud, heroe);

        /** Actualización del mapa */
        camera.update();

        /** Definiiendo la camara del juego al dibujado del mapa tiled */
        dibujadoMapa.setView(camera);

        /** Actualización del HUD de juego */
        hud.escenario.act(delta);
    }

    @Override
    public void render(float delta) {
        /** Limpiamos la pantalla */
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /** Mostramos los FPS en el marco de la ventana */
        Gdx.graphics.setTitle("FPS: " + Gdx.graphics.getFramesPerSecond());

        /** Llamamos al metodo update pasandole el delta*/
        update(delta);

        /** Dibujamos el mapa de Tiled */
        dibujadoMapa.render();

        /** Dibujamos los cuerpos de Box2D */
        //box2DDebugRenderer.render(world, camera.combined);

        /** Establecemos la matriz de proyección de la camara*/
        mainGame.batch.setProjectionMatrix(camera.combined);

        /** Bloque de dibujado de Scene2D */
        mainGame.batch.begin();
        heroe.draw(mainGame.batch);

        for(Enemy enemy : enemigos) {
            enemy.draw(mainGame.batch);
        }

        mainGame.batch.end();
        /** Fin de bloque de dibujado de Scene2D */

        /** Definiendo el escenario en la matriz de proyección */
        mainGame.batch.setProjectionMatrix(hud.escenario.getCamera().combined);

        /** Dbijando HUD */
        hud.escenario.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        hud.viewPort.update(width, height);
        camera.update();
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
        dibujadoMapa.dispose();
        mapa.dispose();
        world.dispose();
        box2DDebugRenderer.dispose();
    }

}
