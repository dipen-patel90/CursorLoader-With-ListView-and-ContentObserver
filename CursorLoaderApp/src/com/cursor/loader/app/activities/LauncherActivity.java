package com.cursor.loader.app.activities;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.cursor.loader.app.R;
import com.cursor.loader.app.common.CursorLoaderDemo;
import com.cursor.loader.app.common.CursorObserver;
import com.cursor.loader.app.db.DatabaseHelper;

/**
 * @author dipenp
 *
 */
public class LauncherActivity extends Activity implements LoaderCallbacks<Cursor>{

	private EditText editText;
	private ListView listView;
	private Button addButton, deleteButton;

	private String[] columns = new String[] { DatabaseHelper.USER_NAME };
	private int[] to = new int[] { R.id.user_name_txt };

	private DatabaseHelper dbHelper;
	private SimpleCursorAdapter cursorAdapter;
	
	private static String TAG ="LOADER";
	private int LOADER_ID = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);

		editText = (EditText) findViewById(R.id.edit_text);
		addButton = (Button) findViewById(R.id.button);
		deleteButton = (Button) findViewById(R.id.deleteButton);
		listView = (ListView) findViewById(R.id.list);

		dbHelper = DatabaseHelper.getInstance(this);

		/*
		 * Initializing cursor adapter*/
		cursorAdapter = new SimpleCursorAdapter(this, R.layout.list_layout, null, columns, to);
		listView.setAdapter(cursorAdapter);
		
		/*
		 * Add record in DB*/
		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = editText.getText().toString().trim();

				if (null != name && !"".equals(name)) {
					dbHelper.addUserName(name);
					editText.setText("");
					
					/**
					 * Notifying DB values are changed*/
					getContentResolver().notifyChange(DatabaseHelper.URI_TABLE_USERS, null);
				} else {
					editText.setError("Please enter name");
				}
			}
		});
		
		/*
		 * Delete all record*/
		deleteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dbHelper.deleteAll();
				
				/**
				 * Notifying DB values are changed*/
				getContentResolver().notifyChange(DatabaseHelper.URI_TABLE_USERS, null);
			}
		});
		
		//Initializing loader
		getLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();
	}

	/***
	 * 
	 * Loaders callback methods
	 * 
	 ***/
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Log.e(TAG, ":::: onCreateLoader");
		
		CursorLoaderDemo demoLoader = new CursorLoaderDemo(this, dbHelper);
		return demoLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
		Log.e(TAG, ":::: onLoadFinished");
		
		cursorAdapter.swapCursor(c);
		
		/**
		 * Registering content observer for this cursor, When this cursor value will be change
		 * This will notify our loader to reload its data*/
		CursorObserver cursorObserver = new CursorObserver(new Handler(), loader);
		c.registerContentObserver(cursorObserver);
		c.setNotificationUri(getContentResolver(), DatabaseHelper.URI_TABLE_USERS);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		Log.e(TAG, ":::: onLoaderReset");
		
		cursorAdapter.swapCursor(null);
	}
}
