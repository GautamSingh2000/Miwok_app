package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class PhasesFragment extends Fragment {
    private MediaPlayer mediaPlayer;
    private AudioManager am;

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
            am.abandonAudioFocus(afchange);
        }
    }

    AudioManager.OnAudioFocusChangeListener afchange = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if (i == am.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            } else if (i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mediaPlayer.pause();
            } else if (i == AudioManager.AUDIOFOCUS_LOSS) {
                mediaPlayer.stop();
                releaseMediaPlayer();
            } else if (i == am.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            }
        }
    };
    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    public PhasesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.word_list, container, false);

        am=(AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Where are you going?", "minto wuksus",
                R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "tinn?? oyaase'n??",
                R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "mich??ks??s?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I???m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "????n??s'aa?", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I???m coming.", "h??????? ????n??m", R.raw.phrase_yes_im_coming));
        words.add(new Word("I???m coming.", "????n??m", R.raw.phrase_im_coming));
        words.add(new Word("Let???s go.", "yoowutis", R.raw.phrase_lets_go));
        words.add(new Word("Come here.", "??nni'nem", R.raw.phrase_come_here));

        CustomAdapter customAdapter = new CustomAdapter(getContext(),words,R.color.category_phrases);
        ListView listView = (ListView)viewRoot.findViewById(R.id.list);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word= words.get(i);
                releaseMediaPlayer();
                int result = am.requestAudioFocus(afchange,am.STREAM_MUSIC,am.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==am.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    mediaPlayer = MediaPlayer.create(getActivity(),word.getAudio());
                    Toast toast=Toast.makeText(getActivity(),"audio playing",Toast.LENGTH_SHORT);
                    toast.show();
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            releaseMediaPlayer();
                        }
                    });

                }
            }

        });
        return viewRoot;
    }
}