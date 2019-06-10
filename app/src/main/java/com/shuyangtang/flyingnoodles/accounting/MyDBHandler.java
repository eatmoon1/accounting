package com.shuyangtang.flyingnoodles.accounting;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.util.ArrayList;


public class MyDBHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productDB1d.db";
    public static final String TABLE_PRODUCTS = "products";
    public static final String COLUMN_CATEGORY = "category";
    //public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCTNAME = "productname";
    public static final String COLUMN_PRICE = "price";

    //We need to pass database information along to superclass
    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_PRODUCTS + " (" + COLUMN_CATEGORY + " CHAR," +
                COLUMN_PRODUCTNAME + " TEXT," + COLUMN_PRICE + " DOUBLE );";
        db.execSQL(query);
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
        createTable(db);
    }


    public void createTable(SQLiteDatabase db){

        String delete = "DELETE FROM " + TABLE_PRODUCTS + " WHERE 1";
        db.execSQL(delete);
        String insert = "INSERT INTO " + TABLE_PRODUCTS + " values('housing', 'HotelW', 120);";
        db.execSQL(insert);
        insert = "INSERT INTO " + TABLE_PRODUCTS + " values('food', 'Salmon', 22);";
        db.execSQL(insert);
        insert = "INSERT INTO " + TABLE_PRODUCTS + " values('food', 'Shrimp', 27);";
        db.execSQL(insert);


    }
    //Add a new row to the database
    public void addProduct(Transaction transaction){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY, transaction.get_category());
        values.put(COLUMN_PRODUCTNAME, transaction.get_productname());
        values.put(COLUMN_PRICE, transaction.get_price());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    //Delete a product from the database
    public void deleteProduct(String productName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME + "='" + productName + "';");
    }

    // this is going in record_TextView in the Main activity.
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1";

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("productname")) != null) {
                //dbString += recordSet.getString(recordSet.getColumnIndex("productname"));
                dbString += recordSet.getString(0);
                dbString += ",   ";
                //dbString += recordSet.getString(recordSet.getColumnIndex("price"));
                dbString += recordSet.getString(1);
                dbString += ",  ";
                dbString += recordSet.getString(2);
                dbString += "\n";
            }
            recordSet.moveToNext();
        }
        //dbString += "\n";

        db.close();
        return dbString;
    }

    public String viewToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT category, sum(price) as sumprice, max(price) as maxprice, count(category)as numOfCategory FROM " + TABLE_PRODUCTS + " GROUP BY category;";
        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("category")) != null) {
                // dbString += recordSet.getString(recordSet.getColumnIndex("productname"));
                dbString += recordSet.getString(0);
                dbString += ",   total price: ";
                dbString += recordSet.getString(recordSet.getColumnIndex("sumprice"));
                dbString += " max price: ";
                dbString += recordSet.getString(recordSet.getColumnIndex("maxprice"));
                dbString += ", # of items: ";
                dbString += recordSet.getString(recordSet.getColumnIndex("numOfCategory"));
                dbString += "\n";
            }
            recordSet.moveToNext();
        }

        db.close();
        return dbString;
    }

    public ArrayList<Transaction> saveToList(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1";
        ArrayList<Transaction> Data = new ArrayList<Transaction>(0);
        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("productname")) != null) {
                String category = recordSet.getString(0);
                String name =  recordSet.getString(1);
                Double price = recordSet.getDouble(2);
                Data.add(new Transaction(category, name, price));
            }
            recordSet.moveToNext();
        }
        db.close();

        if(Data.isEmpty() && Data == null){
            return new ArrayList<Transaction>(0);
        }
        else
            return Data;
    }

}