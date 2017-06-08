package com.mygdx.greenz.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.greenz.scenes.Hud;
import com.mygdx.greenz.sprites.Hero;

/**
 * Clase que controlará los controles de nuestro personaje tanto de movil como de PC
 */
public class Controller {

    public Controller(Hud hud, Hero heroe) {

        //Control movil
        if (hud.botonDerecho.isPressed() && heroe.b2body.getLinearVelocity().x <= 2) {
            heroe.b2body.applyLinearImpulse(new Vector2(0.1f, 0), heroe.b2body.getWorldCenter(), true);
        }
        if (hud.botonIzquierdo.isPressed() && heroe.b2body.getLinearVelocity().x >= -2) {
            heroe.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), heroe.b2body.getWorldCenter(), true);
        }

        if (hud.botonArriba.isPressed() && heroe.puedeSaltar) {
            heroe.b2body.applyForce(0, 235, heroe.b2body.getPosition().x, heroe.b2body.getPosition().y, true);
            heroe.puedeSaltar = false;

            heroe.sonidoSalto.setVolume(heroe.sonidoSalto.play(), 0.2f);
        }

        //Control PC
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && heroe.b2body.getLinearVelocity().x <= 2) {
            heroe.b2body.applyLinearImpulse(new Vector2(0.1f, 0), heroe.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && heroe.b2body.getLinearVelocity().x >= -2) {
            heroe.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), heroe.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && heroe.puedeSaltar) {
            heroe.b2body.applyForce(0, 235, heroe.b2body.getPosition().x, heroe.b2body.getPosition().y, true);
            heroe.puedeSaltar = false;

            heroe.sonidoSalto.setVolume(heroe.sonidoSalto.play(), 0.2f);


        }
    }
}
