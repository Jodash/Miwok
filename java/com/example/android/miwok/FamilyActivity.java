package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    //This listener gets triggered when the MediaPlayer has completed
    //playing the audio file.
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            //Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

            //Handles audio focus when playing a sound file.
            private AudioManager mAudioManager;

            //This listener gets triggered whenever the audio focus changes.
            //ie.gain or lose audio focus due to another app or device.

            private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        //Pause playback and reset player to the start fo the fil so that
                        //we can play the word from the beginning when resume playback.
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        //The AUDIOFOCUS_GAIN means we have regained focus and can resume playback.
                        mMediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        //The AUDIOFOCUS_LOSS means we've lost audio focus and
                        //stop playback and clean up resources.
                        releaseMediaPlayer();
                    }
                }
            };

            @Override
            protected void onCreate (Bundle savedInstanceState){
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_family);

                //Create and setup the AudioManager to request audio focus.
                mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


                //Create a List of words
                final ArrayList<Word> words = new ArrayList<Word>();

                words.add(new Word("father", "әpә", R.drawable.family_father, R.raw.family_father));
                words.add(new Word("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
                words.add(new Word("son", "angsi", R.drawable.family_son, R.raw.family_son));
                words.add(new Word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
                words.add(new Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
                words.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
                words.add(new Word("older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
                words.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
                words.add(new Word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
                words.add(new Word("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

                WordAdapter adapter =
                        new WordAdapter(this, words, R.color.category_family);
                ListView listView = (ListView) findViewById(R.id.list);
                listView.setAdapter(adapter);

                //Set a click listener to play the audio when the list item is clicked on
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        //Release the media player if it currently exists in order to play a
                        // different sound file.
                        releaseMediaPlayer();
                        //Get the object at the given position as clicked.
                        Word word = words.get(position);

                        //Create and setup the MediaPlayer for the audio resource.
                        mMediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getAudioResourceId());
                        //Request audio focus with a short amount of time.
                        int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                            //We have audio focus now.

                            //Create and setup the MediaPlayer for the audio resource associated
                            //with the current word.
                            mMediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getAudioResourceId());
                            //Start the audio file.
                            mMediaPlayer.start();

                            //Setup a listener on the media player so that we can stop and release the
                            //media player once the sound has finished playing.
                            mMediaPlayer.setOnCompletionListener(mCompletionListener);
                        }
                    }
                });
            }

            @Override
            protected void onStop () {
                super.onStop();
                //When the activity is stopped, release the media player resources because no need
                //to play any more sounds.
                releaseMediaPlayer();
            }

            /**
             * Clean up the media player by releasing its resources.
             */

        private void releaseMediaPlayer() {
            // If the media player is not null, then it may be currently playing a sound.
            if (mMediaPlayer != null) {
                // Regardless of the current state of the media player, release its resources
                // because we no longer need it.
                mMediaPlayer.release();

                // Set the media player back to null. For our code, we've decided that
                // setting the media player to null is an easy way to tell that the media player
                // is not configured to play an audio file at the moment.
                mMediaPlayer = null;

                //Regardless of whether or not we were granted audio focus, abandon it.
                //This also unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
                mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
            }
        }
    }





