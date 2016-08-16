package com.example.hwhong.sqliteexplore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hwhong on 7/20/16.
 */
public class MyDBHelper extends SQLiteOpenHelper {

    //https://www.youtube.com/watch?v=7IcNYnQ78Pg#t=582.961

    private static final String DBName = "mydb.db";
    private static final int VERSION  = 1;

    public static final String TABLENAME  = "students";
    public static final String ID  = "_id";
    public static final String NAME  = "name";
    public static final String CITY  = "city";

    private SQLiteDatabase myDB;

    public MyDBHelper(Context context) {
        super(context, DBName, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String queryTable = "CREATE TABLE " + TABLENAME +
                " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT NOT NULL, " +
                CITY + " TEXT NOT NULL " +
                ")";

        sqLiteDatabase.execSQL(queryTable );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void openDB () {
        myDB = getWritableDatabase();
    }

    public void closeDB () {
        if (myDB != null && myDB.isOpen()) {
            myDB.close();
        }
    }

    public long insert(int id, String name, String city) {
        //content value objects contain key-value pairs that can be inserted into database
        ContentValues contentValues = new ContentValues();
        if (id != -1) {
            contentValues.put(ID, id);
        }
        contentValues.put(NAME, name);
        contentValues.put(CITY, city);

        return myDB.insert(TABLENAME, null, contentValues);
    }

    public long update(int id, String name, String city) {
        //content value objects contain key-value pairs that can be inserted into database
        ContentValues contentValues = new ContentValues();

        contentValues.put(ID, id);
        contentValues.put(NAME, name);
        contentValues.put(CITY, city);

        String where = ID + " = " + id;

        //returns the number of row affected
        return myDB.update(TABLENAME, contentValues, where, null );
    }

    public long delete(int id) {
        String where = ID + " = " + id;

        return myDB.delete(TABLENAME, where, null);
    }

    //Cursor is a temporary buffer area that holds results returned from a database query
    public Cursor getAllRecords() {
        String query = "SELECT * FROM " + TABLENAME;

        return myDB.rawQuery(query, null);

    }

    public Cursor getDataBasedOnQuery (String query) {
        return myDB.rawQuery(query, null);
    }

}
