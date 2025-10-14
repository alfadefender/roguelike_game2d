package com.github.documents_please.history;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.github.documents_please.documents.Document;
import com.github.documents_please.documents.DocumentText;
import com.github.documents_please.resources.Assets;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class History extends Document {
    private int numberOfPerson;
    private List<String> personsList;
    private DocumentText personsText;


    public History(Texture textureIcon, Texture texture, float xI, float yI, float x, float y) {
        super(textureIcon, texture, xI, yI, x, y);
        numberOfPerson = 26;
        personsList = new LinkedList<>();
        personsText = new DocumentText("",
            new Label.LabelStyle(Assets.mainFont, new Color(0, 0, 0, 1)), x+15, y + 10);
    }

    public void addNewPerson(boolean action, String result, int money){
        String person = "";
        // action == 1, если человека пропустили
        if (action)
            person = "Пропустил  " +  result;
        else
            person = "Отказано    " +  result;

        int spaces = 64 - person.length();
        person += "  ".repeat(spaces) + money;
        if (personsList.size() == numberOfPerson) {
            personsList.remove(0);
        }
        personsList.add(person);
    }

    protected void updatePersonText(){
        String tempString = "";
        int tempIdx = 0;
        for (String item : personsList) {
            tempString += "\n" + item ;
            tempIdx++;
        }
        if (numberOfPerson - tempIdx > 0) tempString += "\n".repeat(numberOfPerson - tempIdx);
        personsText.setText(tempString);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isExtended) {
            batch.draw(documentTexture, x, y);
            updatePersonText();
        }
        else batch.draw(documentTextureIcon, xI, yI, 100, 100);
        super.draw(batch, parentAlpha);
        if (isExtended) {
            personsText.render(batch,parentAlpha);
        }
    }
}
