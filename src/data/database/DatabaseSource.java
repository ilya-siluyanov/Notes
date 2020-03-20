package com.example.isilu.notes.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.isilu.notes.data.database.NoteDbSchema.NoteTable;
import com.example.isilu.notes.model.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.isilu.notes.data.database.NoteDbSchema.NoteTable.Column.DATE;
import static com.example.isilu.notes.data.database.NoteDbSchema.NoteTable.Column.DESCRIPTION;
import static com.example.isilu.notes.data.database.NoteDbSchema.NoteTable.Column.TIME;
import static com.example.isilu.notes.data.database.NoteDbSchema.NoteTable.Column.TITLE;
import static com.example.isilu.notes.data.database.NoteDbSchema.NoteTable.Column.UUID;
import static com.example.isilu.notes.logger.LogHelper.log;

public class DatabaseSource {
    private static DatabaseSource source;
    private SQLiteDatabase database;

    private DatabaseSource(Context context) {
        log(this.getClass(), "new");
        database = new NoteOpenHelper(context.getApplicationContext()).getWritableDatabase();
    }

    public static DatabaseSource getSource(Context context) {
        if (source == null) {
            source = new DatabaseSource(context);
        }
        return source;
    }

    public Note getNote(UUID id) {
        Note result = null;
        try {
            log(this.getClass(), "getNoteById "+id.toString());
            NoteCursorWrapper noteCursorWrapper = queryNotes(UUID + " = ?", new String[]{id.toString()});
            if (noteCursorWrapper.getCount() == 0) return null;
            noteCursorWrapper.moveToFirst();
            result = noteCursorWrapper.getNote();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public List<Note> getNotes() {
        log("getNotes");
        List<Note> result = new ArrayList<>();
        try {
            NoteCursorWrapper noteCursorWrapper = queryNotes(null, null);
            noteCursorWrapper.moveToFirst();
            while (!noteCursorWrapper.isAfterLast()) {
                result.add(noteCursorWrapper.getNote());
                noteCursorWrapper.moveToNext();
            }
        } catch (Exception e) { e.printStackTrace();}
        return result;
    }

    public void addNote(final Note note) {
        log("addNote "+note.toString());
        database.insert(NoteTable.NAME, null, getContentValues(note));
    }

    private NoteCursorWrapper queryNotes(String clause, String[] args) {
        return new NoteCursorWrapper(database.query(
                NoteTable.NAME,
                null,
                clause,
                args,
                null,
                null,
                null
        ));
    }

    private ContentValues getContentValues(Note note) {
        log("getContentValues"+note.toString());
        ContentValues contentValues = new ContentValues();
        contentValues.put(UUID, note.getId().toString());
        contentValues.put(TITLE, note.getTitle());
        contentValues.put(DESCRIPTION, note.getDescription());
        contentValues.put(DATE, note.getDate());
        contentValues.put(TIME, note.getTime().toString());
        return contentValues;
    }

    public void updateNote(Note note) {
        log("updateNote"+note.toString());
        database.update(
                NoteTable.NAME,
                getContentValues(note),
                UUID + " = ?",
                new String[]{note.getId().toString()});
    }

    public void removeNote(Note note) {
        log("removeNote "+note.toString());
        database.delete(
                NoteTable.NAME,
                UUID + " = ?",
                new String[]{note.getId().toString()});
    }
}