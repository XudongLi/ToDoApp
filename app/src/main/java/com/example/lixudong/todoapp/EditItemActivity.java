package com.example.lixudong.todoapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditItemActivity extends AppCompatActivity {

    EditText editTitle;
    EditText etDate;
    SimpleDateFormat dateFormatter;
    String datePicked;

    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        editTitle = (EditText) findViewById(R.id.editTitle);
        editTitle.setText(getIntent().getStringExtra("EditTitle"));

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        etDate = (EditText) findViewById(R.id.editDate);
        etDate.requestFocus();
        datePicked = getIntent().getStringExtra("EditDate");
        if(datePicked != null) {
            etDate.setText(datePicked);
        }

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, day);
                datePicked = dateFormatter.format(newDate.getTime());
                etDate.setText(datePicked);
            }
        };

        Calendar presetCalendar = Calendar.getInstance();
        try {
            presetCalendar.setTime(dateFormatter.parse(datePicked));
        } catch (ParseException | NullPointerException e) {
            //Just Ignore the exception, use the current date
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        datePickerDialog = new DatePickerDialog(this, dateSetListener, presetCalendar.get(Calendar.YEAR),
                presetCalendar.get(Calendar.MONTH), presetCalendar.get(Calendar.DAY_OF_MONTH));

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }

    public void onEditItem(View view) {
        String updatedText = editTitle.getText().toString();
        Intent data = new Intent();
        data.putExtra("UpdatedTitle", updatedText);
        data.putExtra("UpdatedDate", datePicked);
        data.putExtra("Position", getIntent().getExtras().getInt("EditPosition"));
        setResult(RESULT_OK, data);
        finish();
    }
}
