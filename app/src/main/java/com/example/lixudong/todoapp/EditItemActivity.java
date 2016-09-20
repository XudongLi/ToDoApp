package com.example.lixudong.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        editText = (EditText) findViewById(R.id.editText);
        editText.setText(getIntent().getStringExtra("EditContent"));
    }

    public void onEditItem(View view) {
        String updatedText = editText.getText().toString();
        Intent data = new Intent();
        data.putExtra("UpdatedText", updatedText);
        data.putExtra("Position", getIntent().getExtras().getInt("EditPosition"));
        setResult(RESULT_OK, data);
        finish();
    }
}
