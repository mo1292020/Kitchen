package com.example.myapplication;

//import libraries
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class KitchenDatabaseHandler extends SQLiteOpenHelper
{

    // All Static variables

    // Database Version
    private static  final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Kitchen";


    // kitchen table name
    private static final String TABLE_Kitchen = "Kitchen";
    // Insert KitchenTable Columns names
    //kitchen id
    private static final String KEY_KitchenID = "KitchenId";
    //kitchen name
    private static final String KEY_KitchenName = "KitchenName";
    //kitchen password
    private static final String KEY_KitchenPassword = "KitchenPassword";
    //kitchen image
    private static final String KEY_KitchenImage = "KitchenImage";


    // food table name
    private static final String TABLE_Food = "Food";
    // Insert FoodTable Columns names
    //food id
    private static final String KEY_FoodID = "FoodId";
    //KitchenFood id
    private static final String KEY_FoodKitchenID = "FoodKitchenId";
    //food name
    private static final String KEY_FoodName = "FoodName";
    //food price
    private static final String KEY_FoodPrice = "FoodPrice";

    // KitchenTable Create Statements with id as a primary key auto increment & name & password & image
    private static final String CREATE_Kitchen_TABLE = "CREATE TABLE " + TABLE_Kitchen + "("
            + KEY_KitchenID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_KitchenName + " TEXT,"
            + KEY_KitchenPassword + " TEXT,"
            + KEY_KitchenImage + " BLOB);";

    // FoodTable Create Statements with id as a primary key auto increment & name & password
    // & KitchenFoodId as a foreign key (that link food from FoodTable with his kitchen in KitchenTable
    private static final String CREATE_Food_TABLE = "CREATE TABLE " + TABLE_Food + "("
            + KEY_FoodID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + KEY_FoodName + " TEXT, "
            + KEY_FoodPrice + " TEXT, "
            + KEY_FoodKitchenID + " integer,"
            + " FOREIGN KEY ("+KEY_FoodKitchenID+") REFERENCES "+TABLE_Kitchen+"("+KEY_KitchenID+"));";


    //constructor
    public KitchenDatabaseHandler(Context context)
    {
        //handel database with name & version
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //onCreate method to build Kitchen & Food Table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        //Create kitchen table
        sqLiteDatabase.execSQL(CREATE_Kitchen_TABLE);
        //Create food table
        sqLiteDatabase.execSQL(CREATE_Food_TABLE);
    }

    //onUpgrade method to create new version of database and drop old if exist
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
    {
        // Drop older KitchenTable if existed
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_Kitchen);
        //Drop older FoodTable if existed
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_Food);
        // Create tables again
        onCreate(database);
    }

    //Cursor method to indicator KitchenTable
    public Cursor IndicatorKitchenTable()
    {
        //our readable database connection
        SQLiteDatabase database = this.getReadableDatabase();
        //indicator TableKitchen
        return database.rawQuery("select * from "+TABLE_Kitchen,null);
    }

    //Cursor method to indicator FoodTable
    public Cursor IndicatorFoodTable()
    {
        //our readable database connection
        SQLiteDatabase database = this.getReadableDatabase();
        //indicator FoodKitchen
        return database.rawQuery("select * from "+TABLE_Food,null);
    }

    //method to add new kitchen with name & password & auto increment id in KitchenTable
    public void addKitchen(String NameKitchen, String PasswordKitchen, Bitmap ImageKitchen)
    {
        //our writeable database connection
        SQLiteDatabase database = this.getWritableDatabase();
        //ContentValues to store name & password and set to new row in KitchenTable
        ContentValues values = new ContentValues();
        //Put NameKitchen at KEY_KitchenName
        values.put(KEY_KitchenName, NameKitchen);
        //Put PasswordKitchen at KEY_KitchenPassword
        values.put(KEY_KitchenPassword, PasswordKitchen);
        //convert ImageKitchen to ByteArray by getBitmapAsByteArray method to store
        byte[] ImageKitchenBiteArray = getBitmapAsByteArray(ImageKitchen);
        //Put ImageKitchenBiteArray at KEY_KitchenImage
        values.put(KEY_KitchenImage, ImageKitchenBiteArray);
        // Inserting Row
        database.insert(TABLE_Kitchen, null, values);
        // Closing database connection
        database.close();
    }

    //method to add new Food with name & price & auto increment FoodId
    // & ForeignKeyId linked with his kitchen in FoodTable
    public void addFood(String FoodKitchenId, String NameFood, String PriceFood)
    {
        //our writeable database connection
        SQLiteDatabase database = this.getWritableDatabase();
        //ContentValues to store name & price & FoodKitchenId and set to new row in FoodTable
        ContentValues values = new ContentValues();
        //Put  NameFood at KEY_FoodName
        values.put(KEY_FoodName, NameFood);
        //Put  PriceFood at KEY_FoodPrice
        values.put(KEY_FoodPrice, PriceFood);
        //Put  FoodKitchenId at KEY_FoodKitchenId
        values.put(KEY_FoodKitchenID,FoodKitchenId);
        // Inserting Row
        database.insert(TABLE_Food, null, values);
        // Closing database connection
        database.close();
    }

    //method to update kitchen row by id
    public void updateKitchen(String NameKitchen, String PasswordKitchen,Bitmap ImageKitchen,String IdKitchen)
    {
        //our writeable database connection
        SQLiteDatabase database = this.getWritableDatabase();
        //ContentValues to store name & password and update row with IdKitchen in KitchenTable
        ContentValues updateValues = new ContentValues();
        //Put NameKitchen at KEY_KitchenName
        updateValues.put(KEY_KitchenName, NameKitchen);
        //Put PasswordKitchen at KEY_KitchenPassword
        updateValues.put(KEY_KitchenPassword, PasswordKitchen);
        //convert ImageKitchen to ByteArray by getBitmapAsByteArray method to store
        byte[] ImageKitchenBiteArray = getBitmapAsByteArray(ImageKitchen);
        //Put ImageKitchenBiteArray at KEY_KitchenImage
        updateValues.put(KEY_KitchenImage, ImageKitchenBiteArray);
        //update row with Id IdKitchen
        database.update(TABLE_Kitchen, updateValues, KEY_KitchenID + "=?", new String[] {IdKitchen});
        // Closing database connection
        database.close();
    }

    //method to update food row by id
    public void updateFood(String IdFood,String NameFood, String PriceFood)
    {
        //our writeable database connection
        SQLiteDatabase database = this.getWritableDatabase();
        //ContentValues to store name & price and update row with IdFood in FoodTable
        ContentValues updateValues = new ContentValues();
        //Put  NameFood at KEY_FoodName
        updateValues.put(KEY_FoodName, NameFood);
        //Put  PriceFood at KEY_FoodPrice
        updateValues.put(KEY_FoodPrice, PriceFood);
        //update row with Id IdFood
        database.update(TABLE_Food, updateValues, KEY_FoodID + "=?", new String[] {IdFood});
        // Closing database connection
        database.close();
    }

    //method to delete KitchenRow with id IdKitchen
    //note that it will delete kitchen with id IdKitchen and all food with ForeignId IdKitchen
    public void DeleteKitchen(String IdKitchen)
    {
        //our writeable database connection
        SQLiteDatabase database = this.getWritableDatabase();
        //delete kitchen with id IdKitchen and all food with ForeignId IdKitchen
        database.delete(TABLE_Kitchen, KEY_KitchenID + "=?", new String[] {IdKitchen});
    }

    //method to delete FoodRow with id IdFood
    public void DeleteFood(String IdFood)
    {
        //our writeable database connection
        SQLiteDatabase database = this.getWritableDatabase();
        //delete Food with id IdFood
        database.delete(TABLE_Food, KEY_FoodID + "=?", new String[] {IdFood});
    }

    //method to convert our bitmap image to ByteArray
    public static byte[] getBitmapAsByteArray(Bitmap bitmap)
    {
        //ByteArrayOutputStream to convert input
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //compress bitmap
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        //return bitmap after convert to ByteArray
        return outputStream.toByteArray();
    }
}
