package com.github.documents_please.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.documents_please.MainGame;
import com.github.documents_please.documents.Document;
import com.github.documents_please.documents.DocumentText;
import com.github.documents_please.entities.GuideBook;
import com.github.documents_please.entities.Person;
import com.github.documents_please.entities.PersonFactory;
import com.github.documents_please.history.History;
import com.github.documents_please.resources.Assets;

public class WorkScreen implements Screen {
    private final MainGame game;
    private final Stage gameStage;
    private final Stage pauseStage;
    private boolean isPaused;

    private Person currentPerson;
    private Person previuosPerson;
    private PersonFactory personFactory;
    private GuideBook guideBook;
    private DocumentText date;
    private DocumentText records;
    private String recordsText;
    private DocumentText reasonToDecline;
    private String reasonToDeclineText;
    private History history;

    private Table menuButtonsTable;
    private TextButton pauseGameButton;
    private Table escapeButtonsTable;

    private int day;
    private int month;
    private int year;
    private int money;

    public WorkScreen(MainGame game) {
        this.game = game;

        gameStage = new Stage();
        pauseStage = new Stage();
        isPaused = false;
        Vector2 buttonGeneralSize = Assets.buttonGeneralSize;
        Vector2 buttonSmallSize = Assets.buttonSmallSize;
        Vector2 screenResolution = Assets.screenResolution;

        day = 12;
        month = 4;
        year = 2020;
        money = 0;

        personFactory = new PersonFactory(day, month, year);
        previuosPerson = null;
        currentPerson = personFactory.getNewPerson(2);

        guideBook = new GuideBook();
        date = new DocumentText("Дата: " + day + "-" + month + "-" + year,
            new Label.LabelStyle(Assets.mainFont, new Color(0, 0, 0, 1)), 100, 500);

        recordsText = "Очки: " + money + "\nРекорд: " + Assets.record;
        records = new DocumentText(recordsText,
            new Label.LabelStyle(Assets.mainFont, new Color(1, 1, 0, 1)), 100, 300);

        reasonToDeclineText = "";
        reasonToDecline = new DocumentText(reasonToDeclineText,
            new Label.LabelStyle(Assets.mainFont, new Color(1, 1, 0, 1)), 100, 260);

        history = new History(Assets.historyTextureIcon,Assets.historyTexture, 1750, 400, 500, 0);

        // собираем меню паузы
        setUpPauseMenu(buttonGeneralSize, buttonSmallSize, screenResolution);

        // собираем меню игры
        setUpGameMenu(buttonGeneralSize, buttonSmallSize, screenResolution);

        setInputProcessor();
    }

    protected void setUpPauseMenu(Vector2 buttonGeneralSize, Vector2 buttonSmallSize, Vector2 screenResolution) {
        TextButton continueButton = new TextButton("Продолжить", Assets.buttonSkin, "default");
        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = false;
                setInputProcessor();
            }
        });

        TextButton exitButton = new TextButton("Выйти в главное меню", Assets.buttonSkin, "default");
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreenToMenu();
            }
        });

        escapeButtonsTable = new Table();
        escapeButtonsTable.setPosition(screenResolution.x / 2, screenResolution.y / 2);
        escapeButtonsTable.add(continueButton).width(buttonGeneralSize.x).height(buttonGeneralSize.y).pad(20).row();
        escapeButtonsTable.add(exitButton).width(buttonGeneralSize.x).height(buttonGeneralSize.y).pad(20).row();

        pauseStage.addActor(escapeButtonsTable);
//        pauseStage.setDebugAll(true);
    }

    protected void setUpGameMenu(Vector2 buttonGeneralSize, Vector2 buttonSmallSize, Vector2 screenResolution) {
        pauseGameButton = new TextButton("Пауза", Assets.buttonSkin, "default");
        pauseGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = true;
                setInputProcessor();
            }
        });
        TextButton approvedButton = new TextButton("Одобрить", Assets.buttonSkin, "default");
        approvedButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (previuosPerson != currentPerson) {
                    if (currentPerson.isValid()) {
                        int moneyDelta = MathUtils.random(4, 7);
                        history.addNewPerson(true,"",moneyDelta);
                        money += moneyDelta;
                        reasonToDecline.setText("");
                    }
                    else {
                        int moneyDelta = MathUtils.random(8, 12);
                        money -= moneyDelta;
                        reasonToDeclineText = currentPerson.getReasonToDecline();
                        history.addNewPerson(true,reasonToDeclineText,-moneyDelta);
                        reasonToDecline.setText(reasonToDeclineText);
                    }
                    if (currentPerson != null) currentPerson.setState(1);

                    previuosPerson = currentPerson;

                    if (money < 0) {
                        money = 0;
                    }

                    if (Assets.record < money) {
                        Assets.record = money;
                    }
                    recordsText = "Очки: " + money + "\nРекорд: " + Assets.record;
                    records.setText(recordsText);
                }
            }
        });
        TextButton discardButton = new TextButton("Отклонить", Assets.buttonSkin, "default");
        discardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (previuosPerson != currentPerson){
                    if (!currentPerson.isValid()) {
                        int moneyDelta = MathUtils.random(4, 7);
                        money += moneyDelta;
                        history.addNewPerson(false,"",moneyDelta);
                        reasonToDecline.setText("");
                    }
                    else {
                        int moneyDelta = MathUtils.random(8, 12);
                        money -= moneyDelta;
                        reasonToDeclineText = currentPerson.getReasonToDecline();
                        history.addNewPerson(false,reasonToDeclineText,-moneyDelta);
                        reasonToDecline.setText(reasonToDeclineText);
                    }
                    if (currentPerson != null) currentPerson.setState(-1);

                    previuosPerson = currentPerson;

                    if (money < 0) {
                        money = 0;
                    }

                    if (Assets.record < money) {
                        Assets.record = money;
                    }
                    recordsText = "Очки: " + money + "\nРекорд: " + Assets.record;
                    records.setText(recordsText);
                }
            }
        });

        pauseGameButton.setPosition(10, 10);
        pauseGameButton.setSize(buttonSmallSize.x, buttonSmallSize.y);

        menuButtonsTable = new Table();
        menuButtonsTable.setPosition(1600, 200);
        menuButtonsTable.add(approvedButton).width(buttonSmallSize.x).height(buttonSmallSize.y).pad(10);
        menuButtonsTable.add(discardButton).width(buttonSmallSize.x).height(buttonSmallSize.y).pad(10);

        gameStage.addActor(pauseGameButton);
        gameStage.addActor(menuButtonsTable);
        gameStage.addActor(currentPerson);
        for (Document elem : currentPerson.returnDocs()) {
            gameStage.addActor(elem);
        }
        gameStage.addActor(guideBook);
        gameStage.addActor(guideBook.returnButtons());
        gameStage.setDebugAll(true);

        gameStage.addActor(history);
    }

    protected void updateGameStage() {
        gameStage.clear();
        currentPerson = personFactory.getNewPerson(2);
        gameStage.addActor(pauseGameButton);
        gameStage.addActor(menuButtonsTable);
        gameStage.addActor(currentPerson);
        for (Document elem : currentPerson.returnDocs()) {
            gameStage.addActor(elem);
        }
        gameStage.addActor(guideBook);
        gameStage.addActor(guideBook.returnButtons());
        gameStage.setDebugAll(true);
        gameStage.addActor(history);
    }

    public void setInputProcessor() {
        if (isPaused) {
            game.setUpInputProcessor(pauseStage);
        }
        else {
            game.setUpInputProcessor(gameStage);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (isPaused) {
            pauseStage.act(delta);
            pauseStage.getBatch().begin();
            pauseStage.getBatch().draw(Assets.backgroundPause, 0, 0);
            pauseStage.getBatch().end();
            pauseStage.draw();
        }
        else {
            gameStage.act(delta);
            if (!guideBook.isExtended()) guideBook.returnButtons().setVisible(false);
            else guideBook.returnButtons().setVisible(true);

            if (currentPerson.isQuited()) {
                updateGameStage();
            }

            gameStage.getBatch().begin();
            gameStage.getBatch().draw(Assets.backgroundGame, 0, 0);
            gameStage.getBatch().end();
            gameStage.draw();
            gameStage.getBatch().begin();
            records.render(gameStage.getBatch(), 1);
            date.render(gameStage.getBatch(), 1);
            reasonToDecline.render(gameStage.getBatch(), 1);
            gameStage.getBatch().end();
        }
    }

    public void unpause() {
        isPaused = false;
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {
        gameStage.getViewport().update(width, height, true);
        pauseStage.getViewport().update(width, height, true);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        gameStage.dispose();
        pauseStage.dispose();
    }
}
