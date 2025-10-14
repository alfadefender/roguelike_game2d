package com.github.documents_please.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.github.documents_please.documents.Document;
import com.github.documents_please.documents.InvitePermission;
import com.github.documents_please.documents.Passport;
import com.github.documents_please.documents.VaccinationCertificate;

public class Person extends Actor {
    protected Animation<AtlasRegion> personIdleAnim;
    protected Animation<AtlasRegion> personWalkInAnim;
    protected Animation<AtlasRegion> personAcceptAnim;
    protected Animation<AtlasRegion> personDeclineAnim;
    protected Animation<AtlasRegion> currentAnimation;

    protected AtlasRegion photo;

    protected float animX;
    protected float animY;

    protected float time;
    protected AtlasRegion currentTexture;

    protected Passport passport;
    protected InvitePermission invite;
    protected VaccinationCertificate vaccination;

    // 0 = nothing, 1 = accepted, -1 = declined
    protected int state;

    protected boolean isValid;
    protected String reasonToDecline;


    public Person(Animation<AtlasRegion> animationIdle, Animation<AtlasRegion> animationWalkIn,
                  Animation<AtlasRegion> animationAccept, Animation<AtlasRegion> animationDecline) {
        super();
        personIdleAnim = animationIdle;
        personWalkInAnim = animationWalkIn;
        personAcceptAnim = animationAccept;
        personDeclineAnim = animationDecline;
        currentAnimation = personWalkInAnim;
        photo = animationIdle.getKeyFrame(0);

        passport = null;
        invite = null;
        vaccination = null;

        time = 0.1f;
        currentTexture = currentAnimation.getKeyFrame(time);

        animX = 100;
        animY = 630;

        state = 0;
        isValid = true;
        reasonToDecline = "Документы  без  ошибок";
    }

    public boolean isQuited() {
        return currentAnimation.isAnimationFinished(time) && state != 0;
    }

    public void setState(int key) {
        state = key;
        changePersonAnim();
    }

    public boolean isValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public void setReasonToDecline(String reasonToDecline) {
        this.reasonToDecline = reasonToDecline;
    }

    public String getReasonToDecline() {
        return reasonToDecline;
    }

    public void changePersonAnim() {
        currentAnimation = switch (state) {
            case 0 -> personIdleAnim;
            case 1 -> personAcceptAnim;
            case -1 -> personDeclineAnim;
            default -> personWalkInAnim;
        };
        time = 0f;
    }

    public Array<Document> returnDocs() {
        Array<Document> array = new Array<>();
        if (passport != null) array.add(passport);
        if (invite != null) array.add(invite);
        if (vaccination != null) array.add(vaccination);
        return array;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(currentTexture, animX, animY);
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        time += delta;
        currentTexture = currentAnimation.getKeyFrame(time);
        if (currentAnimation == personWalkInAnim && currentAnimation.isAnimationFinished(time))
            changePersonAnim();
    }

    public void setInvite(String nameInvite, String genderInvite, String purposeInvite, String howLongInvite,
                          String validUntilInvite, String idInvite, Texture firstStampInvite, Texture secondStampInvite) {
        this.invite = new InvitePermission(firstStampInvite, secondStampInvite, nameInvite, genderInvite,
                                            purposeInvite, howLongInvite, validUntilInvite, idInvite);
    }

    public void setPassport(String namePassport, String surnamePassport, String addressPassport, String idPassport,
                            String agePassport, String genderPassport, String validUntilPassport, Texture stampPassport) {
        passport = new Passport(photo, stampPassport, namePassport, surnamePassport, addressPassport, idPassport,
                                agePassport, genderPassport, validUntilPassport);
    }

    public void setVaccination(String nameVaccination, String surnameVaccination, String diseaseVaccination,
                               String dateVaccination, Texture stampVaccination) {
        vaccination = new VaccinationCertificate(stampVaccination, nameVaccination, surnameVaccination,
                                                diseaseVaccination, dateVaccination);
    }

}
