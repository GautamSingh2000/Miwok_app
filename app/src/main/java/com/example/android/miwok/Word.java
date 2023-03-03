package com.example.android.miwok;

public class Word {
    private String defaultTranslation, miwoktranslation;
    private int image;
    private boolean mImageResourceId = true;
    private int audio;

    public Word(String defaultTranslation, String miwoktranslation, int audio) {
        this.defaultTranslation = defaultTranslation;
        this.miwoktranslation = miwoktranslation;
        this.audio=audio;
        mImageResourceId=false;
    }

    public Word(String defaultTranslation, String miwoktranslation, int image, int audio) {
        this.defaultTranslation = defaultTranslation;
        this.miwoktranslation = miwoktranslation;
        this.audio=audio;
        this.image = image;
    }

    public String getDefaultTranslation() {
        return defaultTranslation;
    }

    public String getMiwoktranslation() {
        return miwoktranslation;
    }

    public int getImageResourceId() {
        return image;
    }

    public boolean hasImage() {
        return mImageResourceId;
    }

    public int getAudio(){ return audio; }
}

