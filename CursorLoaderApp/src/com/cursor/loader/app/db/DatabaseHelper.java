package com.cursor.loader.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * @author dipenp
 * 
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "CursorLoaderDemoDB";

	// User table name
	private static final String TABLE_USERS = "users";
	// User table column
	public static final String ID = "_id";
	public static final String USER_NAME = "user_name";
	// User table uri, We have to create URI for this table to use it in Observer
	public static final Uri URI_TABLE_USERS = Uri.parse("sqlite://com.loader.demo/table/" + TABLE_USERS);

	// Database helper instance
	private static DatabaseHelper _instance;

	private Context context;
	
	/**
	 * @param context constructor
	 */
	private DatabaseHelper(Context context) {
 		super(context, DATABASE_NAME, null, DATABASE_VERSION);
 		this.context = context;
	}
	
	/**
	 * @param context
	 * @return get databasehelper instance
	 */
	public static DatabaseHelper getInstance(Context context) {
		if (null == _instance) {
			_instance = new DatabaseHelper(context);
		}
		return _instance;
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_USERS + "(" + ID
				+ " INTEGER PRIMARY KEY," + USER_NAME + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * Add user name in DB
	 * @param name 
	 */
	public void addUserName(String name) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(USER_NAME, name);

		// Inserting Row
		db.insert(TABLE_USERS, null, values);
		db.close(); // Closing database connection
		
//		context.getContentResolver().notifyChange(DatabaseHelper.URI_TABLE_USERS, null);
	}

	/**
	 * @return return all the username
	 */
	public Cursor getAllUsername() {

		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_USERS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		return cursor;
	}
	
	/**
	 * delete all record
	 */
	public void deleteAll(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_USERS, null, null);
	}
}
