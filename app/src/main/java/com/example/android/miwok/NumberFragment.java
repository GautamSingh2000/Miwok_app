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


public class NumberFragment extends Fragment {

    private MediaPlayer audio_object;
    private AudioManager am;


    //conditions to check the changes comes in the changelistener
    AudioManager.OnAudioFocusChangeListener afchange =  new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if(i==am.AUDIOFOCUS_GAIN)
            {
                audio_object.start();
            }
            else if(i==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT)
            {
                audio_object.pause();
            }
            else if(i==AudioManager.AUDIOFOCUS_LOSS)
            {
                audio_object.stop();
                releseMediaPlayer();
            }
            else if(i==am.AUDIOFOCUS_GAIN)
            {
                audio_object.start();
            }
            else if(i==am.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
            {
                final int adjustLower = AudioManager.ADJUST_LOWER;
            }
        }
    };
    private void releseMediaPlayer() {
        if(audio_object!=null)
        {
            audio_object.release();
            audio_object=null;
            Toast tost = Toast.makeText(getContext(),"audio relese",Toast.LENGTH_SHORT);
            tost.show();
            am.abandonAudioFocus(afchange);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releseMediaPlayer();
    }



    public NumberFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        am =(AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);


        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo’e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "na’aacha", R.drawable.number_ten, R.raw.number_ten));

        CustomAdapter NewAdapter = new CustomAdapter(getContext(),words,R.color.category_numbers);
        ListView gridView = (ListView) rootView.findViewById(R.id.list);
        gridView.setAdapter(NewAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //here we are creating a object which save the data comes from the words array list object words
                Word word = words.get(i);
                releseMediaPlayer();

                //creating variable to store the result of AudioManager request
                int result = am.requestAudioFocus(afchange,am.STREAM_MUSIC,am.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==am.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    audio_object = MediaPlayer.create(getActivity(),word.getAudio());
                    audio_object.start();
                    Toast toast=Toast.makeText(getActivity(),"audio playing",Toast.LENGTH_SHORT);
                    toast.show();
                    audio_object.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            releseMediaPlayer();
                        }
                    });
                }
            }

        });
                return rootView;
    }
}