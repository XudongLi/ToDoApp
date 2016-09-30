package com.example.lixudong.todoapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lixudong.todoapp.util.ItemPriority;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EditItemActivity extends AppCompatActivity {

    private static final Map<String, Integer> priorityMap = new HashMap<>();
    static {
        priorityMap.put(ItemPriority.HIGH.toString(), 0);
        priorityMap.put(ItemPriority.MEDIUM.toString(), 1);
        priorityMap.put(ItemPriority.LOW.toString(), 2);
    }

    EditText editTitle;
    EditText etDate;
    SimpleDateFormat dateFormatter;
    String datePicked;
    String priorityPicked;
    EditText editNote;

    DatePickerDialog datePickerDialog;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        editTitle = (EditText) findViewById(R.id.editTitle);
        editTitle.setText(getIntent().getStringExtra("EditTitle"));
        editNote = (EditText) findViewById(R.id.editDescribe);
        editNote.setText(getIntent().getStringExtra("EditDescribe"));

        setDatePickerDialog();
        setSpinner();
    }

    private void setDatePickerDialog() {
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

    private void setSpinner() {
        spinner = (Spinner) findViewById(R.id.editPriority);
        String[] priorities = new String[] {
            ItemPriority.HIGH.toString(),
            ItemPriority.MEDIUM.toString(),
            ItemPriority.LOW.toString()
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, priorities);
        spinner.setAdapter(adapter);
        priorityPicked = getIntent().getStringExtra("EditPriority");
        spinner.setSelection((priorityPicked == null)? 0 : priorityMap.get(priorityPicked));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                priorityPicked = parent.getItemAtPosition(position).toString();
                spinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    public void onEditItem(View view) {
        String updatedText = editTitle.getText().toString();
        String updatedNote = editNote.getText().toString();
        Intent data = new Intent();
        data.putExtra("UpdatedTitle", updatedText);
        data.putExtra("UpdatedDate", datePicked);
        data.putExtra("UpdatedPriority", priorityPicked);
        data.putExtra("UpdatedDescribe", updatedNote);
        data.putExtra("Position", getIntent().getExtras().getInt("EditPosition"));
        setResult(RESULT_OK, data);
        finish();
    }
}
