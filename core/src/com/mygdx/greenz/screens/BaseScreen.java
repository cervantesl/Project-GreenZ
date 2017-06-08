package com.mygdx.greenz.screens;

import com.badlogic.gdx.Screen;
import com.mygdx.greenz.MainGame;

/**
 * Clase abstracta que será una plantilla para todas las pantallas de la aplicación.
 */
public class BaseScreen implements Screen {

    /** MainGame comun para todas las pantallas de la aplicación*/
    protected MainGame mainGame;

    /**
     *
     * @param mainGame el parametro mainGame es comun a todas las clases Screen que extiende de nuestra
     *                 clase BaseScreen. Lo utilizaremos a la hora de utilizar el batch o hacer un setScreen
     */
    public BaseScreen(MainGame mainGame) {
        this.mainGame = mainGame;
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

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
        mainGame.dispose();
    }
}
