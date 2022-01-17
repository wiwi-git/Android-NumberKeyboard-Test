package com.example.customkeyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MyInputMethodService extends InputMethodService {
    static String TAG = "MyInputMethodService";

    LinearLayout keyboardView;
    FrameLayout keyboardFrame;
    KeyboardNumpad numpad;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        keyboardView = (LinearLayout) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        keyboardFrame = keyboardView.findViewById(R.id.keyboard_frame);
        numpad = new KeyboardNumpad();

    }

    @Override
    public View onCreateInputView() {
        Log.d(TAG, "onCreateInputView: ");

        keyboardFrame.removeAllViews();
        keyboardFrame.addView(numpad.newLayout(getApplicationContext(),getLayoutInflater(),getCurrentInputConnection()));

        return keyboardView;
    }

    @Override
    public void updateInputViewShown() {
        super.updateInputViewShown();
        Log.d(TAG, "updateInputViewShown: ");
        getCurrentInputConnection().finishComposingText();
    }
}
