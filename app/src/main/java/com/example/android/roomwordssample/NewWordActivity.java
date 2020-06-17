package com.example.android.roomwordssample;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;

/**
 * Activity for entering a word.
 */

public class NewWordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText mEditWordView;

    private boolean edit;
    private Word word;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        mEditWordView = findViewById(R.id.edit_word);
        if(getIntent().hasExtra("com.example.android.roomwordssample.EXTRA_PALABRA")){
            word = (Word) getIntent().getExtras().getSerializable("com.example.android.roomwordssample.EXTRA_PALABRA");
            mEditWordView.setText(word.getWord());
            edit = true;
        }



        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditWordView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String wordTXT = mEditWordView.getText().toString();
                    if(edit){
                        Word wordNew = new Word(word.getId(),word.getWord());
                        wordNew.setWord(wordTXT);
                        replyIntent.putExtra("com.example.android.roomwordssample.EXTRA_PALABRA_OLD", (Serializable) word);
                        replyIntent.putExtra("com.example.android.roomwordssample.EXTRA_PALABRA_NEW", (Serializable) wordNew);
                    }else{
                        replyIntent.putExtra(EXTRA_REPLY, wordTXT);
                    }
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}

