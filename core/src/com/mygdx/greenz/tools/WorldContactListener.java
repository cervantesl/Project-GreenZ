package com.mygdx.greenz.tools;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.greenz.MainGame;
import com.mygdx.greenz.screens.GameOverScreen;
import com.mygdx.greenz.sprites.Enemy;
import com.mygdx.greenz.sprites.Hero;

import java.util.ArrayList;

/**
 * Clase que controlará los contactos de cuerpos en nuestro juego
 */
public class WorldContactListener implements ContactListener {

    /** Clase que implementará los delimitadores de los cuerpos de nuestro mapa creado en Tiled */
    private B2WorldCreator b2WorldCreator;

    /** Clase Hero */
    private Hero heroe;

    /** Musica de fondo */
    private Music backgroundMusic;

    /** Sonido zombie */
    private Sound sonidoZombie;

    private MainGame mainGame;

    /** lista de enemigos */
    private ArrayList<Enemy> enemigos;

    public WorldContactListener(B2WorldCreator b2WorldCreator, Hero heroe, Music backgroundMusic, MainGame mainGame,
                                ArrayList<Enemy> enemmigos, Sound sonidoZombie) {
        this.b2WorldCreator = b2WorldCreator;

        this.heroe = heroe;

        this.backgroundMusic = backgroundMusic;

        this.mainGame = mainGame;

        this.enemigos = enemmigos;

        this.sonidoZombie = sonidoZombie;

    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        /** Contacto entre el heroe y el suelo: ponemos a true la variable puedeSaltar.
         * Con ello conseguimos controlar que el personaje no pueda saltar en el aire
         * o hacer saltos raros y continuos
         */
        for (int i = 0; i < b2WorldCreator.fixture.length;i++) {
            if(fixtureA == heroe.fixture && fixtureB == b2WorldCreator.fixture[i]) {
                heroe.puedeSaltar = true;
            }
            if(fixtureB == heroe.fixture && fixtureA == b2WorldCreator.fixture[i]) {
                heroe.puedeSaltar = true;
            }
        }

        //CONTACTO AGUA Y HERO
        /** Contacto entre el heroe y el agua*/
        for (int i = 0; i < b2WorldCreator.fixtureMuerte.length;i++) {
            if(fixtureA == heroe.fixture && fixtureB == b2WorldCreator.fixtureMuerte[i]) {
                backgroundMusic.stop();

                mainGame.setScreen(new GameOverScreen(mainGame));
            }
            if(fixtureB == heroe.fixture && fixtureA == b2WorldCreator.fixtureMuerte[i]) {
                backgroundMusic.stop();

                mainGame.setScreen(new GameOverScreen(mainGame));
            }
        }

        //CONTACTO CABEZA ENEMIGO Y HERO
        for (Enemy enemy : enemigos) {
            if(fixtureA == heroe.fixture && fixtureB == enemy.fixture) {
                enemy.muerto = true;
                System.out.println("contacto cabeza");
                sonidoZombie.play();
            }
            if(fixtureB == heroe.fixture && fixtureA == enemy.fixture) {
                enemy.muerto = true;
                System.out.println("contacto cabeza");
                sonidoZombie.play();
            }

            //CONTACTO ENEMIGO Y HERO
            if(!enemy.muerto) {
                if(fixtureA == heroe.fixture && fixtureB == enemy.fixtureBody) {
                    System.out.println("contacto enemigo");

                    backgroundMusic.stop();

                    mainGame.setScreen(new GameOverScreen(mainGame));

                }
                if(fixtureB == heroe.fixture && fixtureA == enemy.fixtureBody) {
                    System.out.println("contacto enemigo");

                    backgroundMusic.stop();

                    mainGame.setScreen(new GameOverScreen(mainGame));
                }
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        //CONTACTO SUELO Y HERO
        for (int i = 0; i < b2WorldCreator.fixture.length;i++) {
            if(fixtureA == heroe.fixture && fixtureB == b2WorldCreator.fixture[i]) {
                heroe.puedeSaltar = false;
            }
            if(fixtureB == heroe.fixture && fixtureA == b2WorldCreator.fixture[i]) {
                heroe.puedeSaltar = false;
            }
        }

        //CONTACTO CABEZA ENEMIGO Y HERO
        for (Enemy enemy : enemigos) {
            if(fixtureA == heroe.fixture && fixtureB == enemy.fixture) {
                enemy.muerto = true;
            }
            if(fixtureB == heroe.fixture && fixtureA == enemy.fixture) {
                enemy.muerto = true;
            }

            if(!enemy.muerto) {
                //CONTACTO ENEMIGO Y HERO
                if(fixtureA == heroe.fixture && fixtureB == enemy.fixtureBody) {

                }
                if(fixtureB == heroe.fixture && fixtureA == enemy.fixtureBody) {
                }
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
