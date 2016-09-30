package com.example.lixudong.todoapp.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Items")
public class Item extends Model {

    @Column(name = "title")
    public String title;

    @Column(name = "description")
    public String description;

    @Column(name = "createDate")
    public String date;

    @Column(name = "priority")
    public String priority;

    public Item() {
        super();
    }

    public Item(String title, String description, String date, String priority) {
        super();
        this.title = title;
        this.description = description;
        this.date = date;
        this.priority = priority;
    }
}
