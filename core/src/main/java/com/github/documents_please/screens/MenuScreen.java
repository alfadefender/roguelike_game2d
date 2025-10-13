package com.github.documents_please.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.documents_please.MainGame;
import com.github.documents_please.resources.Assets;

public class MenuScreen implements Screen {
    private MainGame game;
    private TextButton startButton;
    private TextButton endButton;
    private Stage mainMenuButtonsStage;
    private Table buttonsTable;
    private Image background;

    public MenuScreen(MainGame game) {
        this.game = game;
        mainMenuButtonsStage = new Stage();

        startButton = new TextButton("Играть", Assets.buttonSkin, "default");
        endButton = new TextButton("Выход", Assets.buttonSkin, "default");

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreenToGame();
            }
        });
        endButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Vector2 buttonSize = Assets.buttonGeneralSize;
        Vector2 screenResolution = Assets.screenResolution;

        buttonsTable = new Table();
        buttonsTable.setPosition(buttonSize.x, screenResolution.y / 2);
        buttonsTable.add(startButton).width(buttonSize.x).height(buttonSize.y).pad(20).row();
        buttonsTable.add(endButton).width(buttonSize.x).height(buttonSize.y).pad(20).row();

        background = new Image(Assets.backgroundMainMenu);
        background.setPosition(0, 0);

        mainMenuButtonsStage.addActor(background);
        mainMenuButtonsStage.addActor(buttonsTable);
//        mainMenuButtonsStage.setDebugAll(true);

        setInputProcessor();
    }

    public void setInputProcessor() {
        Gdx.input.setInputProcessor(mainMenuButtonsStage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        mainMenuButtonsStage.act(v);
        mainMenuButtonsStage.draw();

        game.batch.end();
    }

    @Override
    public void resize(int i, int i1) {
        mainMenuButtonsStage.getViewport().update(i, i1);
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
        mainMenuButtonsStage.dispose();
    }
}
