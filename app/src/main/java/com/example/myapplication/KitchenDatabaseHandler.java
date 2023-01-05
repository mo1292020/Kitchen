package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class KitchenDatabaseHandler extends SQLiteOpenHelper{

    // All Static variables
    // Database Version
    private static  final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "Kitchen";

    // Login table name
    private static final String TABLE_Kitchen = "Kitchen";
    private static final String TABLE_Food = "Food";
    // Insert Table Columns names
    private static final String KEY_KitchenID = "KitchenId";
    private static final String KEY_KitchenNAME = "KitchenName";
    private static final String KEY_KitchenPassword = "Kitchenpassword";
    private static final String KEY_FoodID = "FoodId";
    private static final String KEY_FoodKitchenID = "FoodKitchenId";
    private static final String KEY_FoodName = "FoodName";
    private static final String KEY_FoodPrice = "FoodPrice";

    // Table Create Statements
    private static final String CREATE_Kitchen_TABLE = "CREATE TABLE " + TABLE_Kitchen + "("
            + KEY_KitchenID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_KitchenNAME + " TEXT,"
            + KEY_KitchenPassword + " TEXT" + ") ; ";
    private static final String CREATE_Food_TABLE = "CREATE TABLE " + TABLE_Food + "("
            + KEY_FoodID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + KEY_FoodName + " TEXT, "
            + KEY_FoodPrice + " TEXT, "
            + KEY_FoodKitchenID + " integer,"
            + " FOREIGN KEY ("+KEY_FoodKitchenID+") REFERENCES "+TABLE_Kitchen+"("+KEY_KitchenID+"));";



    public KitchenDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_Kitchen_TABLE);
        sqLiteDatabase.execSQL(CREATE_Food_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Kitchen);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Food);
        // Create tables again
        onCreate(db);
    }

    public Cursor IndicatorKitchenTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from "+TABLE_Kitchen,null);
    }

    public Cursor IndicatorFoodTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from "+TABLE_Food,null);
    }

    public void addKitchen(String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_KitchenNAME, name); // Name
        values.put(KEY_KitchenPassword, password); // Password
        // Inserting Row
        db.insert(TABLE_Kitchen, null, values);
        db.close(); // Closing database connection
    }

    public void addFood(String KitchenId, String name, String price) {
        String selectQuery = "SELECT  * FROM " + TABLE_Food;
        //get last id
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FoodName, name); // Name
        values.put(KEY_FoodPrice, price); // Price
        values.put(KEY_FoodKitchenID,KitchenId);
        // Inserting Row
        db.insert(TABLE_Food, null, values);
        db.close(); // Closing database connection
    }

    public void updateKitchen(String name, String password,String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_KitchenNAME, name); // Name
        updateValues.put(KEY_KitchenPassword, password); // Password
        db.update(TABLE_Kitchen, updateValues, KEY_KitchenID + "=?", new String[] {uid});
        db.close();
    }

    public void updateFood(String Fid,String name, String price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_FoodID,Fid);
        updateValues.put(KEY_FoodName, name); // Name
        updateValues.put(KEY_FoodPrice, price); // Password
        db.update(TABLE_Food, updateValues, KEY_FoodID + "=?", new String[] {Fid});
        db.close();
    }

    public void DeleteKitchen(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_Kitchen, KEY_KitchenID + "=?", new String[]{id});
    }

    public void DeleteFood(String Fid) {
        SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_Food, KEY_FoodID + "=?", new String[]{Fid});
    }




}
