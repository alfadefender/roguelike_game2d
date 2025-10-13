package com.github.documents_please.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Assets {
    public static AssetManager manager = new AssetManager();
    public static Skin buttonSkin;
    public static Texture backgroundGame;
    public static Texture backgroundPause;
    public static Texture backgroundMainMenu;

    public static Texture passportTextureIcon;
    public static Texture passportTexture;
    public static Texture invitePermissionTexture;
    public static Texture invitePermissionTextureIcon;
    public static Texture vaccinationTexture;
    public static Texture vaccinationTextureIcon;
    public static Texture historyTexture;
    public static Texture historyTextureIcon;

    public static Array<Texture> guideBook;
    public static Texture guideBookIcon;

    public static Array<Texture> stamps;

    public static Array<Animation<AtlasRegion>> idles;
    public static Array<Animation<AtlasRegion>> walkIns;
    public static Array<Animation<AtlasRegion>> accepts;
    public static Array<Animation<AtlasRegion>> declines;

    public static Vector2 screenResolution;
    public static Vector2 buttonGeneralSize;
    public static Vector2 buttonSmallSize;
    public static Vector2 buttonSquaredSize;

    public static BitmapFont mainFont;

    public static List<String> femaleNames;
    public static List<String> maleNames;
    public static List<String> femaleSurnames;
    public static List<String> maleSurnames;

    public static List<String> cities;
    public static List<String> purposes;
    public static List<String> diseases;

    public static int record;

    public static void load() {
        manager.load("background.png", Texture.class);
        manager.load("main_menu.png", Texture.class);
        manager.load("pause_overlay.png", Texture.class);
        manager.load("buttons.atlas", TextureAtlas.class);

        manager.load("passport.png", Texture.class);
        manager.load("passport_icon.png", Texture.class);
        manager.load("invite.png", Texture.class);
        manager.load("invite_icon.png", Texture.class);
        manager.load("vaccination.png", Texture.class);
        manager.load("vaccination_icon.png", Texture.class);
        manager.load("history_page.png", Texture.class);
        manager.load("history_icon.png", Texture.class);

        manager.load("stamp1.png", Texture.class);
        manager.load("stamp2.png", Texture.class);
        manager.load("stamp3.png", Texture.class);
        manager.load("stamp4.png", Texture.class);

        manager.load("book_page_1.png", Texture.class);
        manager.load("book_page_2.png", Texture.class);
        manager.load("book_page_3.png", Texture.class);
        manager.load("book_page_4.png", Texture.class);
        manager.load("book_page_5.png", Texture.class);
        manager.load("book_page_6.png", Texture.class);
        manager.load("guide_icon.png", Texture.class);

        manager.load("female1_anim.atlas", TextureAtlas.class);
        manager.load("female2_anim.atlas", TextureAtlas.class);
        manager.load("male1_anim.atlas", TextureAtlas.class);
        manager.load("male2_anim.atlas", TextureAtlas.class);
        manager.finishLoading();

        buttonSkin = new Skin(Gdx.files.internal("button_skin.json"),
            manager.get("buttons.atlas", TextureAtlas.class));

        backgroundGame = manager.get("background.png", Texture.class);
        backgroundMainMenu = manager.get("main_menu.png", Texture.class);
        backgroundPause = manager.get("pause_overlay.png", Texture.class);

        passportTexture = manager.get("passport.png", Texture.class);
        passportTextureIcon = manager.get("passport_icon.png", Texture.class);
        invitePermissionTexture = manager.get("invite.png", Texture.class);
        invitePermissionTextureIcon = manager.get("invite_icon.png", Texture.class);
        vaccinationTexture = manager.get("vaccination.png", Texture.class);
        vaccinationTextureIcon = manager.get("vaccination_icon.png", Texture.class);
        historyTexture = manager.get("history_page.png", Texture.class);
        historyTextureIcon = manager.get("history_icon.png", Texture.class);

        stamps = new Array<>();

        for (int i = 1; i <= 4; i++)
            stamps.add(manager.get("stamp" + i + ".png", Texture.class));

        guideBook = new Array<>();

        for (int i = 1; i <= 6; i++)
            guideBook.add(manager.get("book_page_" + i + ".png", Texture.class));

        guideBookIcon = manager.get("guide_icon.png", Texture.class);

        idles = new Array<>();
        walkIns = new Array<>();
        accepts = new Array<>();
        declines = new Array<>();

        setUpFemaleAnim1();
        setUpFemaleAnim2();

        setUpMaleAnim1();
        setUpMaleAnim2();

        screenResolution = new Vector2(1920, 1080);
        buttonGeneralSize = new Vector2(400, 200);
        buttonSmallSize = new Vector2(300, 150);
        buttonSquaredSize = new Vector2(170, 170);

        mainFont = new BitmapFont(Gdx.files.internal("default.fnt"), false);

        parseJsonNames();
        parseJsonCities();
        parseJsonPurposes();
        parseJsonDiseases();

        parseRecord();
    }

    private static void setUpHelper(String name) {
        TextureAtlas atlas = manager.get(name + "_anim.atlas", TextureAtlas.class);
        Array<AtlasRegion> regions = new Array<>();
        for (int i = 1; i <= 10; i++) {
            AtlasRegion region = atlas.findRegion(name + "_idle" + i);
            if (region != null) regions.add(region);
        }
        System.out.println(regions);
        Animation<AtlasRegion> temp = new Animation<>(0.08f, regions);
        temp.setPlayMode(Animation.PlayMode.LOOP);
        idles.add(temp);

        regions.clear();
        for (int i = 1; i <= 30; i++) {
            AtlasRegion region = atlas.findRegion(name + "_walking_in" + i);
            if (region != null) regions.add(region);
        }
        System.out.println(regions);
        walkIns.add(new Animation<>(0.06f, regions));

        regions.clear();
        for (int i = 1; i <= 30; i++) {
            AtlasRegion region = atlas.findRegion(name + "_accepted" + i);
            if (region != null) regions.add(region);
        }
        System.out.println(regions);
        accepts.add(new Animation<>(0.06f, regions));

        regions.clear();
        for (int i = 1; i <= 30; i++) {
            AtlasRegion region = atlas.findRegion(name + "_decline" + i);
            if (region != null) regions.add(region);
        }
        System.out.println(regions);
        declines.add(new Animation<>(0.06f, regions));
    }

    public static void setUpFemaleAnim1() {
        setUpHelper("female1");
    }

    public static void setUpFemaleAnim2() {
        setUpHelper("female2");
    }

    public static void setUpMaleAnim1() {
        setUpHelper("male1");
    }

    public static void setUpMaleAnim2() {
        setUpHelper("male2");
    }

    private static void parseJsonNames() {
        String jsonData = Gdx.files.internal("names.json").readString();

        JsonReader jsonReader = new JsonReader();
        JsonValue root = jsonReader.parse(jsonData);

        femaleNames = Arrays.stream(root.get("femaleNames").asStringArray()).toList();
        maleNames = Arrays.stream(root.get("maleNames").asStringArray()).toList();
        femaleSurnames = Arrays.stream(root.get("femaleSurnames").asStringArray()).toList();
        maleSurnames = Arrays.stream(root.get("maleSurnames").asStringArray()).toList();
        jsonReader.stop();
    }

    private static void parseJsonCities() {
        String jsonData = Gdx.files.internal("addresses.json").readString();

        JsonReader jsonReader = new JsonReader();
        JsonValue root = jsonReader.parse(jsonData);

        cities = Arrays.stream(root.get("cities").asStringArray()).toList();
        jsonReader.stop();
    }

    private static void parseJsonPurposes() {
        String jsonData = Gdx.files.internal("purposes.json").readString();

        JsonReader jsonReader = new JsonReader();
        JsonValue root = jsonReader.parse(jsonData);

        purposes = Arrays.stream(root.get("purposes").asStringArray()).toList();
        jsonReader.stop();
    }

    private static void parseJsonDiseases() {
        String jsonData = Gdx.files.internal("diseases.json").readString();

        JsonReader jsonReader = new JsonReader();
        JsonValue root = jsonReader.parse(jsonData);

        diseases = Arrays.stream(root.get("diseases").asStringArray()).toList();
        jsonReader.stop();
    }

    private static void parseRecord() {
        try {
            if (!Gdx.files.internal("config.json").exists()) {
                record = 0;
                return;
            }

            String json = Gdx.files.internal("config.json").readString();
            JsonValue jsonValue = new JsonReader().parse(json);
            record = jsonValue.getInt("record", 0);
        } catch (Exception e) {
            record = 0;
            System.err.println("Error reading record: " + e.getMessage());
        }
    }

    private static void saveRecord() {
        try {
            JsonValue jsonValue = new JsonValue(JsonValue.ValueType.object);
            jsonValue.addChild("record", new JsonValue(record));
            Gdx.files.local("config.json").writeString(jsonValue.toJson(JsonWriter.OutputType.json), false);

        } catch (Exception e) {
            throw new RuntimeException("Failed to save record", e);
        }
    }

    public static void dispose() {
        manager.dispose();
        buttonSkin.dispose();
        backgroundGame.dispose();
        passportTextureIcon.dispose();
        passportTexture.dispose();
        invitePermissionTexture.dispose();
        invitePermissionTextureIcon.dispose();
        vaccinationTexture.dispose();
        vaccinationTextureIcon.dispose();
        historyTexture.dispose();
        historyTextureIcon.dispose();
        mainFont.dispose();
        saveRecord();
    }
}
