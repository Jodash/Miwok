package com.example.android.miwok;

/**
 * Created by Ban on 2017/7/28.
 */

/**
 * {@link Word} represents a vocabulary word that the user wants to learn.
 * It contains a default translation and a Miwok translation for that word.
 */

public class Word {

    /**
     * Default translation for the word
     */
    private String mDefaultTranslation;

    /**
     * Miwok translation for the word
     */
    private String mMiwokTranslation;

    //Create a private integer variable for image resource ID
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    //Constant value that represents no image was provided for this word.
    private static final int NO_IMAGE_PROVIDED = -1;

    //Audio resource ID for the word.
    private int mAudioResourceId;

    //Create a new Word object.
    //@param defaultTranslation is the word in a language that the user is
    //already familiar with.
    //@param miwokTranslation is the word in the Miwok language.
    //param audioResourceId is the resource ID for the audio file assocaited with this word.


    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
    }

    //Create a new Word object.
    //@param defaultTranslation is the word in a language that the user is
    //already familiar with.
    //@param miwokTranslation is the word in the Miwok language.
    //@param imageResourceId is the drawable resource ID for the image associated
    //with the word
    //param audioResourceId is the resource ID for the audio file associated with this word.

    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Get the default translation of the word.
     */
    public String getmDefaultTranslation() {
        return mDefaultTranslation;
    }

    /**
     * Get the Miwok translation of the word.
     */
    public String getmMiwokTranslation() {
        return mMiwokTranslation;
    }

    //Get the image resource ID of the word.
    public int getImageResourceId() {
        return mImageResourceId;
    }

    //Returns whether or not there is an image for this word.
    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    //Returns the audio resource ID of the word.
    public int getAudioResourceId(){ return mAudioResourceId; }
}
