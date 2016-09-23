package com.example.lixudong.todoapp;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.lixudong.todoapp.adapter.ItemAdapter;
import com.example.lixudong.todoapp.model.Item;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 20;

    List<Item> toDoItems;
    ItemAdapter aToDoAdapter;
    ListView lvItems;
    EditText etEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActiveAndroid.initialize(this);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new Delete().from(Item.class).where("id = ?", toDoItems.get(position).getId()).execute();
                toDoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra("EditTitle", toDoItems.get(position).title);
                intent.putExtra("EditPosition", position);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            int position = data.getExtras().getInt("Position");
            String updatedTitle = data.getExtras().getString("UpdatedTitle");
            Item toUpdate = toDoItems.get(position);
            toUpdate.title = updatedTitle;
            toUpdate.save();
            aToDoAdapter.notifyDataSetChanged();
        }
    }

    private void populateArrayItems() {
        readItems();
        aToDoAdapter = new ItemAdapter(this, toDoItems);
    }

    private void readItems() {
        try {
            toDoItems = new Select().from(Item.class).execute();
        } catch (SQLiteException e) {
            toDoItems = new ArrayList<>();
        }
    }

    public void onAddItem(View view) {
        Item toAdd = new Item();
        toAdd.title = etEditText.getText().toString();
        toAdd.save();
        aToDoAdapter.add(toAdd);
        etEditText.setText("");
    }
}
