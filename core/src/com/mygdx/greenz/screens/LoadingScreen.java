package com.mygdx.greenz.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.greenz.MainGame;

/**
 * Clase para la pantalla de carga automatizada que controlará que haya acabado de cargar los recursos
 * desde la clase MainGame
 */
public class LoadingScreen extends BaseScreen {

    /** Definimos un escenario */
    private Stage stage;

    public LoadingScreen(MainGame mainGame) {
        super(mainGame);

        /** Inicializamos el escenario */
        stage = new Stage(new FitViewport(640, 360));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /** Controlamos la carga de los assets */
        if (mainGame.getManager().update()) {
            mainGame.finishLoading();
        }

        /** Actualizaoms y dibujamos el escenario */
        stage.act();
        stage.draw();
    }
}
