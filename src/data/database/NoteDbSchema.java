package com.example.isilu.notes.data.database;

@SuppressWarnings("ALL")
final class NoteDbSchema {
    public static final class NoteTable {
        public static final String NAME = "notes";

        public static final class Column {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "desc";
            public static final String DATE = "date";
            public static final String TIME = "time";
        }
    }
}
