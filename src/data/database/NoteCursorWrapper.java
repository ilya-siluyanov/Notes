package com.example.isilu.notes.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.isilu.notes.model.Note;
import com.example.isilu.notes.model.Time;

import static com.example.isilu.notes.data.database.NoteDbSchema.NoteTable.Column.DATE;
import static com.example.isilu.notes.data.database.NoteDbSchema.NoteTable.Column.DESCRIPTION;
import static com.example.isilu.notes.data.database.NoteDbSchema.NoteTable.Column.TIME;
import static com.example.isilu.notes.data.database.NoteDbSchema.NoteTable.Column.TITLE;
import static com.example.isilu.notes.data.database.NoteDbSchema.NoteTable.Column.UUID;

public class NoteCursorWrapper extends CursorWrapper {
    NoteCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Note getNote() {
        String uuid = getString(getColumnIndex(UUID));
        String title = getString(getColumnIndex(TITLE));
        String description = getString(getColumnIndex(DESCRIPTION));
        String date= getString(getColumnIndex(DATE));
        Time time = new Time(getString(getColumnIndex(TIME)));
        return new Note(uuid).setTitle(title).setDescription(description).setDate(date).setTime(time);
    }

}
