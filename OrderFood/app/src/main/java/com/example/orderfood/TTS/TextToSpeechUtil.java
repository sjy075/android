package com.example.orderfood.TTS;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class TextToSpeechUtil implements TextToSpeech.OnInitListener {

    private TextToSpeech textToSpeech;
    private Context context;

    public TextToSpeechUtil(Context context) {
        this.context = context;
        textToSpeech = new TextToSpeech(context, this);
    }

    public void speak(String text) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    public void shutdown() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.CHINESE);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TextToSpeechUtil", "语言不支持");
            }
        } else {
            Log.e("TextToSpeechUtil", "初始化失败");
        }
    }
}