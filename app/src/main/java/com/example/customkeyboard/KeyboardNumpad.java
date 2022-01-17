package com.example.customkeyboard;

import android.content.Context;
import android.content.res.Configuration;
import android.inputmethodservice.Keyboard;
import android.media.AudioManager;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeyboardNumpad {
    LinearLayout numpadLyaout;
    InputConnection inputConnection;
    Context context;

    List<String> firstLineText = Arrays.asList("1", "2", "3","DEL");
    List<String> secondLineText = Arrays.asList("4", "5", "6", "Enter");
    List<String> thirdLineText = Arrays.asList("7","8","9",".");
    List<String> fourthLineText = Arrays.asList("-", "0", ",", "");

    ArrayList<Button> buttons = new ArrayList<>();

    ArrayList<List<String>> myKeysText = new ArrayList<>();
    ArrayList<LinearLayout> layoutLines = new ArrayList<>();

    public LinearLayout newLayout(Context context, LayoutInflater layoutInflater, InputConnection inputConnection) {
        this.context = context;
        this.inputConnection = inputConnection;

        numpadLyaout = (LinearLayout) layoutInflater.inflate(R.layout.keyboard_numpad, null);

        int height = 150;
        Configuration config = context.getResources().getConfiguration();

        LinearLayout firstLine =  numpadLyaout.findViewById(R.id.first_line);
        LinearLayout secondLine =  numpadLyaout.findViewById(R.id.second_line);
        LinearLayout thirdLine =  numpadLyaout.findViewById(R.id.third_line);
        LinearLayout fourthLine =  numpadLyaout.findViewById(R.id.fourth_line);

        List<LinearLayout> lines = Arrays.asList(firstLine, secondLine, thirdLine, fourthLine);

        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            height = (int) Math.round(height * 0.7);
        }

        layoutLines.clear();
        for (LinearLayout line: lines) {
            line.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
            layoutLines.add(line);
        }

        myKeysText.clear();
        myKeysText.add(firstLineText);
        myKeysText.add(secondLineText);
        myKeysText.add(thirdLineText);
        myKeysText.add(fourthLineText);

        setLayoutComponents();

        return numpadLyaout;
    }

    private void setLayoutComponents(){
        for (int lineIndex = 0; lineIndex < layoutLines.size(); lineIndex++) {
            ArrayList<View> children = new ArrayList<>();
            for (int i = 0; i < layoutLines.get(lineIndex).getChildCount(); i++) {
                children.add(layoutLines.get(lineIndex).getChildAt(i));
            }
            List<String> myText = myKeysText.get(lineIndex);
            int longClickIndex = 0;

            for (int itemIndex = 0; itemIndex < layoutLines.get(lineIndex).getChildCount(); itemIndex++) {
                Button actionButton = children.get(itemIndex).findViewById(R.id.key_button);
                actionButton.setText(myText.get(itemIndex));
                buttons.add(actionButton);

                String buttonText = myText.get(itemIndex);

                View.OnClickListener myOnClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (buttonText) {
                            case "DEL" :
                                inputConnection.deleteSurroundingText(1,0);
                                break;

                            case "Enter" :
                                long eventTime = SystemClock.uptimeMillis();
                                inputConnection.sendKeyEvent(new KeyEvent(eventTime, eventTime,
                                        KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER, 0, 0, 0, 0,
                                        KeyEvent.FLAG_SOFT_KEYBOARD));
                                inputConnection.sendKeyEvent(new KeyEvent(
                                        SystemClock.uptimeMillis(), eventTime,
                                        KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER, 0, 0, 0, 0,
                                        KeyEvent.FLAG_SOFT_KEYBOARD));
                                break;
                            default:
//                        playClick(
//                                actionButton.text.toString().toCharArray().get(
//                                        0
//                                ).toInt()
//                        )
                                inputConnection.commitText(actionButton.getText(),1);
                                break;
                        }
                    }
                };

                actionButton.setOnClickListener(myOnClickListener);
                children.get(itemIndex).setOnClickListener(myOnClickListener);
            }
        }
    }

//    private void playClick(int i) {
//        AudioManager am = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager?
//        when (i) {
//            32 -> am!!.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR)
//            Keyboard.KEYCODE_DONE, 10 -> am!!.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN)
//            Keyboard.KEYCODE_DELETE -> am!!.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE)
//                else -> am!!.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD, -1.toFloat())
//        }
//    }
}

