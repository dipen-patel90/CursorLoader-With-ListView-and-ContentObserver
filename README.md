CursorLoader-With-ListView-and-ContentObserver
==============================================

CursorLoader With ListView and ContentObserver


This post will cover following topics:


LoaderManager
AsyncTaskLoader
SimpleCursorAdapter
ContentObserver
SqliteDatabase


We are going to use LoaderManager  with ListView for updating ListView data.
When user will add a new record in table it will automatically populate in list view. We are using ContentObserver for observing any changes in cursor value.

We will implement LoaderCallbacks<Cursor> which contain following method:

onCreateLoader(int id, Bundle args) : We will initialize our custom CursorLoaderDemo class which will load data in background for ListView.

onLoadFinished(Loader<Cursor> loader, Cursor c): When our CursorLoaderDemo  class will load data in background it will be return in this method. We are using this data to populate in ListView.

Also we are adding ContentObserver to this cursor so that when new data will be available for this cursor it will call onChange(boolean selfChange) method of this ContentObserver class. Where we can reload LoaderManager.

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

onLoaderReset(Loader<Cursor> loader): This method will be called when loader will reset, where we are removing adapter value.
----------------------------------------------------------------------------------------------------------

We have to create URI for our table to add it in ContentResolver

public static final Uri URI_TABLE_USERS = Uri.parse("sqlite://com.loader.demo/table/" + TABLE_USERS);

When we will Add, Update or Delete any record of this table we can use this URI to notify that data is changed in this table.
----------------------------------------------------------------------------------------------------------
