package com.github.documents_please;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.github.documents_please.resources.Assets;
import com.github.documents_please.screens.MenuScreen;
import com.github.documents_please.screens.WorkScreen;

public class MainGame extends Game {
    public SpriteBatch batch;
    private MenuScreen menuScreen;
    private WorkScreen gameScreen;
    private boolean isGameEnded;

    @Override
    public void create() {
        batch = new SpriteBatch();
        Assets.load();
        isGameEnded = true;
        gameScreen = new WorkScreen(this);
        menuScreen = new MenuScreen(this);
        setScreen(menuScreen);
    }

    public void changeScreenToMenu() {
        isGameEnded = true;
        setScreen(menuScreen);
        menuScreen.setInputProcessor();
    }

    public void changeScreenToGame() {
        if (isGameEnded) {
            gameScreen.unpause();
            isGameEnded = false;
        }
        setScreen(gameScreen);
        gameScreen.setInputProcessor();
    }

    public void setUpInputProcessor(Stage stage) {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        Assets.dispose();
        menuScreen.dispose();
    }
}
