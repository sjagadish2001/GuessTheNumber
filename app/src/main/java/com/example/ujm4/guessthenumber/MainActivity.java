package com.example.ujm4.guessthenumber;

import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySoundPool = new SoundPool( 5, AudioManager.STREAM_MUSIC, 0);
        mySoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
                loaded = true;
            }
        });
        soundID = mySoundPool.load(this, R.raw.backgroundmusic, 1);

        // Getting the user sound settings
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        float actualVolume = (float) audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = actualVolume / maxVolume;
        mySoundPool.play(soundID, volume, volume, 1, -1, 1f);
    }

    boolean loaded = false;
    private int soundID;
    private TextView random_num;
    int randomNum;
    int counter=0;
    private long startTime, endTime;
    private SoundPool mySoundPool;


    /** Called when the user clicks the  start button */
    public void findRandomNumber(View view) {
        random_num  = (TextView)findViewById(R.id.random_num);

        //reset, user clicks start game again
        EditText num = (EditText)findViewById(R.id.inputNumber);
        num.setText("");

        counter = 0; //reset
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        randomNum = rand.nextInt((1000 - 1) + 1) + 1;
        System.out.println("________"+randomNum);
        random_num.setText("XXXXXXXX");

        TextView result;
        result  = (TextView)findViewById(R.id.result_view);
        result.setText("Ok, Now guess the Number..!");
        startTime = System.currentTimeMillis();

    }//end


    /** Called when the user clicks the  button */
    public void checkNumber(View view) {

        EditText num = (EditText)findViewById(R.id.inputNumber);
        int userNum = 0;
        try {
            userNum = Integer.valueOf(num.getText().toString());
            TextView result;
            result  = (TextView)findViewById(R.id.result_view);

            if (userNum > randomNum ) {
                counter++;
                result.setText("Oops You guessed higher.. Try again.\nYou tried '"+ counter + "' times");
            } else if (userNum < randomNum) {
                result.setText("Oops You guessed lower.. Try again.\nYou tried '"+ counter + "' times");
                counter++;
            } else if (userNum == randomNum) {
                endTime = System.currentTimeMillis();
                long timeTaken = (endTime-startTime)/1000;
                result.setText("Hurray !! you guessed Correctly!! \nYou took " +timeTaken+" seconds to GUESS\nNo. of tries to guess correctly:"+counter);
                Toast.makeText(getApplicationContext(),
                        "Good Job You guessed it right...!", Toast.LENGTH_SHORT).show();
            }

        } catch (NumberFormatException nfe){
            Toast.makeText(getApplicationContext(),
                    "Enter a valid number between 1 and 1000", Toast.LENGTH_SHORT).show();
        }


    }//end

    public void clearField(View view) {

        EditText num = (EditText)findViewById(R.id.inputNumber);
        num.setText("");

    }//end


    @Override
    protected void onPause() {
        super.onPause();
        mySoundPool.release();
        mySoundPool = null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();


    }
}
