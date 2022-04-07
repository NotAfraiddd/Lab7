package com.example.lab07;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String Database_names = "lab7";
    private static final String Table_names = "Person";
    private static final String CreateQuery = String.format("create table %s (" +
            " id Integer primary key," +
            " fullname text " +
            ")",Table_names);
    public DatabaseHandler(Context context) {
        super(context, Database_names, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CreateQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS "+Table_names);
        onCreate(sqLiteDatabase);
    }
    void add(Person person){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",person.getId());
        values.put("fullname",person.getFullName());
        db.insert(Table_names,null,values);
        db.close();
    }
    public Person getPersonById(int id){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Cursor cursor = db.query(Table_names, new String[]{ "id","fullname"},"id" + "=?",
                new String[]{String.valueOf("id")},null,null,null,null);

        Person person = new Person(Integer.parseInt(cursor.getString(0)),cursor.getString(1));
        return person;
    }
    public List<Person> getAllPersons(){
        List<Person> personList = new ArrayList<Person>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ Table_names,null);

        if(cursor.moveToFirst()){
            while (cursor.moveToNext()){
                Person person = new Person();
                person.setId(Integer.parseInt(cursor.getString(0)));
                person.setFullName(cursor.getString(1));
            }
        }
        return personList;
    }
    public boolean updatePerson(Person person){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put("id",person.getId());
        values.put("fullname",person.getFullName());

        int a = db.update(Table_names,values,"id"+"=?",new String[]{String.valueOf(person.getId())});
        return a > 0;
    }
    public boolean deletePersonbyID(int personID){
        SQLiteDatabase db = this.getWritableDatabase();
        int a= db.delete(Table_names,"id"+"=?",new String[]{String.valueOf(personID)});
        return a > 0;
    }
}
