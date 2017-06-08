package com.mygdx.greenz.sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.greenz.MainGame;
import com.mygdx.greenz.screens.GameScreen;

/**
 * Clase para definir al personaje que controlamos en el juego. En ella estableceremos sus atributos
 * y controlaremos el Sprite que se esta dibujando, su estado y su posición.
 */
public class Hero extends Sprite {

    /** Estados del heroe */
    public enum State{SALTANDO, ENPIE, CORRIENDO};

    /** Estado actual */
    public State estadoActual;

    /** Estado previo */
    public State estadoPrevio;

    /** Animacion de correr */
    private Animation heroRun;

    /** Animación de saltar */
    private Animation heroJump;

    /** Tiempo de estado de estado*/
    private float tiempoEstado;

    /** boolean para controlar si hay que hacer flipX a la imagen cuando corre a la izqueirda */
    public boolean corriendoDerecha = false;

    /** Clases de Box2D */
    public World world;
    public Body b2body;
    public Fixture fixture;

    /** Region de la textura */
    private TextureRegion region;

    /** Boolean para controlar que el personaje no pueda saltar varias veces en el aire */
    public boolean puedeSaltar = true;

    /** Sonido del salto del personaje */
    public Sound sonidoSalto;

    /**@param world
     * @param gameScreen*/
    public Hero(World world, GameScreen gameScreen) {
        super(gameScreen.getAtlas().findRegion("characters"));
        this.world = world;

        /** Definimos el los atributos del body del personaje */
        defineHero();

        /** Cargamos el sonido del salto */
        sonidoSalto = gameScreen.mainGame.getManager().get("jump.mp3");

        /** Obtenemos la region de la textura que queremos mostrar inicialmente */
        region = new TextureRegion(getTexture(), 0, 82, 32, 32);

        /** Definimos la posición y el tamaño del sprite */
        setBounds(0, 0, 32 / MainGame.PPM, 32 / MainGame.PPM);

        /** Definimos la región */
        setRegion(region);

        /** Definimos los estados inicialmente con ENPIE */
        estadoActual = State.ENPIE;
        estadoPrevio = State.ENPIE;
        tiempoEstado = 0;
        corriendoDerecha = true;

        /** Creamos array para almacenar los frames para las animaciones */
        Array<TextureRegion> frames = new Array<TextureRegion>();

        /** Obtenemos frames de la animación de correr */
        for(int i = 1; i < 8; i++) {
            frames.add(new TextureRegion(getTexture(), i * 32, 82, 32, 32));
        }
        heroRun = new Animation(0.2f, frames);
        frames.clear();

        /** Obtenemos frames de la animacion de saltar*/
        for(int i = 6; i < 8; i++) {
            frames.add(new TextureRegion(getTexture(), i * 32, 82, 32, 32));
        }
        heroJump = new Animation(0.2f, frames);
        frames.clear();

    }

    /**@param dt delta */
    public void update(float dt) {
        /** Definimos la posicion y el frame */
        setPosition(b2body.getPosition().x - getWidth() / 2, (b2body.getPosition().y - getHeight() / 2) + 0.08f);
        setRegion(getFrame(dt));
    }

    /** @return State Obtenemos el estado actual del personaje */
    public State getState() {
        if(b2body.getLinearVelocity().x >= 0.5 && b2body.getLinearVelocity().y == 0) {
            corriendoDerecha = true;
            return State.CORRIENDO;
        }
        else if(b2body.getLinearVelocity().x <= -0.5 && b2body.getLinearVelocity().y == 0) {
            corriendoDerecha = false;
            return State.CORRIENDO;
        }
        else if(b2body.getLinearVelocity().y > 0) {
            return State.SALTANDO;
        }
        else {
            return State.ENPIE;
        }
    }

    /**@param dt
     * @return TextureRegion obtebemos el frame actual
     */
    public TextureRegion getFrame(float dt) {
        estadoActual = getState();

        TextureRegion region = null;

        switch (estadoActual) {
            case CORRIENDO:
                region = (TextureRegion)heroRun.getKeyFrame(tiempoEstado, true);
                break;

            case ENPIE:
                region = this.region;
                break;

            case SALTANDO:
                region = (TextureRegion) heroJump.getKeyFrame(tiempoEstado, false);
                break;

            default:
                region = this.region;
        }

        /** Controlamos los frames con flipX (corriendo izquierda)*/
        if(b2body.getLinearVelocity().x <= -0.5 && !corriendoDerecha && !region.isFlipX()) {
            corriendoDerecha = false;
            region.flip(true, false);
        } else if(b2body.getLinearVelocity().x >= 0.5 && corriendoDerecha && region.isFlipX()) {
            region.flip(true, false);
            corriendoDerecha = true;
        }

        tiempoEstado = estadoActual == estadoPrevio ? tiempoEstado + dt : 0;
        estadoPrevio = estadoActual;

        return region;
    }

    /** Definimos el los atributos del body del personaje */
    private void defineHero() {
        BodyDef bodyDef = new BodyDef();

        bodyDef.position.set(64 / MainGame.PPM, 64 / MainGame.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(6 / MainGame.PPM, 6 / MainGame.PPM);

        fixtureDef.shape = shape;
        fixture = b2body.createFixture(fixtureDef);

        fixture.setUserData("player");
    }
}
