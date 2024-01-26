package com.example.mabibliotheque;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class bdhelperhistoire extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "histoire";
    private List<histoire> histoireList;
    private static final int DATABASE_VERSION = 1;

    // Noms des colonnes
    private static final String TABLE_NAME = "histoire";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOM = "nom";
    private static final String COLUMN_Description = "Description";
    private static final String COLUMN_URL = "URL";
    private static final String COLUMN_IMAGE = "image";

    public bdhelperhistoire(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NOM + " TEXT,"
                + COLUMN_Description + " TEXT,"
                + COLUMN_URL + " TEXT,"
                + COLUMN_IMAGE + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public long ajouterHistoire(histoire histoire) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOM, histoire.getNom());
        values.put(COLUMN_Description, histoire.getDescription());
        values.put(COLUMN_URL, histoire.getURL());
        values.put(COLUMN_IMAGE, histoire.getImage());
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public int updateHistoire(histoire histoire) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOM, histoire.getNom());
        values.put(COLUMN_Description, histoire.getDescription());
        values.put(COLUMN_URL, histoire.getURL());
        values.put(COLUMN_IMAGE, histoire.getImage());

        // Updating row
        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(histoire.getId())});
    }
    public void deleteHistoire(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
    public List<histoire> getAllHistoires() {
        List<histoire> histoireList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                histoire histoire = new histoire();
                int columnIndexId = cursor.getColumnIndex(COLUMN_ID);
                int columnIndexNom = cursor.getColumnIndex(COLUMN_NOM);
                int columnIndexPrix = cursor.getColumnIndex(COLUMN_Description);
                int columnIndexURL = cursor.getColumnIndex(COLUMN_URL);
                int columnIndexImage = cursor.getColumnIndex(COLUMN_IMAGE);

                if (columnIndexId != -1) {
                    histoire.setId(cursor.getInt(columnIndexId));
                }

                if (columnIndexNom != -1) {
                    histoire.setNom(cursor.getString(columnIndexNom));
                }

                if (columnIndexPrix != -1) {
                    histoire.setDescription(cursor.getString(columnIndexPrix));
                }

                if (columnIndexURL != -1) {
                    histoire.setURL(cursor.getString(columnIndexURL));
                }

                if (columnIndexImage != -1) {
                    histoire.setImage(cursor.getString(columnIndexImage));
                }

                histoireList.add(histoire);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return histoireList;
    }
    public List<histoire> getFilteredHistoires(String query) {
        List<histoire> filteredList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_NOM + " LIKE '%" + query + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                histoire histoire = new histoire();
                int columnIndexId = cursor.getColumnIndex(COLUMN_ID);
                int columnIndexNom = cursor.getColumnIndex(COLUMN_NOM);
                int columnIndexPrix = cursor.getColumnIndex(COLUMN_Description);
                int columnIndexURL = cursor.getColumnIndex(COLUMN_URL);
                int columnIndexImage = cursor.getColumnIndex(COLUMN_IMAGE);

                if (columnIndexId != -1) {
                    histoire.setId(cursor.getInt(columnIndexId));
                }

                if (columnIndexNom != -1) {
                    histoire.setNom(cursor.getString(columnIndexNom));
                }

                if (columnIndexPrix != -1) {
                    histoire.setDescription(cursor.getString(columnIndexPrix));
                }

                if (columnIndexURL != -1) {
                    histoire.setURL(cursor.getString(columnIndexURL));
                }

                if (columnIndexImage != -1) {
                    histoire.setImage(cursor.getString(columnIndexImage));
                }

                filteredList.add(histoire);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return filteredList;
    }

    public histoire getById(int id) {

        for (histoire h : histoireList) {
            if (h.getId() == id) {
                return h;
            }
        }
        return null;
    }

}








