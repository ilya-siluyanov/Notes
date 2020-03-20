package com.example.isilu.notes.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.isilu.notes.data.database.NoteDbSchema.NoteTable;

import static com.example.isilu.notes.data.database.NoteDbSchema.NoteTable.Column.DATE;
import static com.example.isilu.notes.data.database.NoteDbSchema.NoteTable.Column.DESCRIPTION;
import static com.example.isilu.notes.data.database.NoteDbSchema.NoteTable.Column.TIME;
import static com.example.isilu.notes.data.database.NoteDbSchema.NoteTable.Column.TITLE;
import static com.example.isilu.notes.data.database.NoteDbSchema.NoteTable.Column.UUID;

public class NoteOpenHelper extends SQLiteOpenHelper {
    private static int version = 1;
    private static String DATABASE_NAME = "notes.db";

    NoteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String template = "create table %s(_id integer primary key autoincrement, %s,%s,%s,%s,%s);";
        sqLiteDatabase.execSQL(String.format(template, NoteTable.NAME, UUID, TITLE, DESCRIPTION, DATE, TIME));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
