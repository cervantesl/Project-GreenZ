package com.mygdx.greenz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.greenz.screens.GameScreen;
import com.mygdx.greenz.screens.LoadingScreen;
import com.mygdx.greenz.screens.MenuScreen;

/**
 * Esta clase extiende de Game que esta extiende de AplicationListener pero con la ventaja de
 * tener un metodo muy util que lo utilizaremos mucho que es: setScreen.
 *
 * @author Lucas Cervantes Leonez
 */

public class MainGame extends Game {

	/** Agrupa todas las imagenes que se quiera dibujar en el juego */
	public SpriteBatch batch;

	/** Atributos de dimension al mapa y camara */
	public static final float PPM = 100;

	public static final int V_WIDTH = 420; //450
	public static final int V_HEIGHT = 205; //220

	/** Clase para la carga de recursos del juego */
	private AssetManager manager;

	/**Clase controladora de la carga de recursos del juego */
	private LoadingScreen loadingScreen;

	/** Clase de la pantalla Menu */
	private MenuScreen menuScreen;


	@Override
	public void create () {
		batch = new SpriteBatch();

		manager = new AssetManager();

		manager.load("logo.png", Texture.class);
		manager.load("br.mp3", Music.class);
		manager.load("tr.mp3", Music.class);
		manager.load("jump.mp3", Sound.class);
		manager.load("gover.mp3", Sound.class);
		manager.load("zomb.mp3", Sound.class);


		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
	}

	/**
	 *
	 * @return manager
     */
	public AssetManager getManager() {
		return manager;
	}

	/** meotdo para controlar la carga de la pantalla menu */
	public void finishLoading() {
		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
