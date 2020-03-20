package com.example.isilu.notes.model;

import java.util.Date;
import java.util.UUID;

public class Note {
    private String title;
    private String description;
    private UUID id;
    private String date;
    private Time time;

    public Note() {
        this(UUID.randomUUID().toString());
    }

    public Note(String uuidString) {
        this.id = UUID.fromString(uuidString);
        this.title = "";
        this.description = "";
        this.date = FormatUtils.getFormattedDate(new Date());
        this.time = new Time("0", "0");
    }

    public String getTitle() {
        return title;
    }

    public Note setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Note setDescription(String description) {
        this.description = description;
        return this;
    }

    public UUID getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Note setDate(String date) {
        this.date = date;
        return this;
    }

    public Time getTime() {
        return time;
    }

    public Note setTime(Time time) {
        this.time = time;
        return this;
    }

    @Override
    public String toString() {
        return "Note {" +
                "id = "+id.toString()+
                "title = "+title;
    }
}
