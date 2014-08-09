package com.cursor.loader.app.common;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.cursor.loader.app.db.DatabaseHelper;

/**
 * @author dipenp
 *
 */
public class CursorLoaderDemo extends AsyncTaskLoader<Cursor> {

	private static String TAG ="LOADER";
	
	private Context context;
	private DatabaseHelper dbHelper;

	public CursorLoaderDemo(Context context, DatabaseHelper dbHelper) {
		super(context);
		this.context = context;
		this.dbHelper = dbHelper;
	}

	@Override
	protected void onStartLoading() {
		Log.e(TAG, ":::: onStartLoading");

		super.onStartLoading();
	}

	@Override
	public Cursor loadInBackground() {
		Log.e(TAG, ":::: loadInBackground");

		Cursor c = dbHelper.getAllUsername();

		return c;
	}

	@Override
	public void deliverResult(Cursor data) {
		Log.e(TAG, ":::: deliverResult");

		super.deliverResult(data);
	}

	@Override
	protected void onStopLoading() {
		Log.e(TAG, ":::: onStopLoading");

		super.onStopLoading();
	}
}
