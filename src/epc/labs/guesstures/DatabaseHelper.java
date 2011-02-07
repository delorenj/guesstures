package epc.labs.guesstures;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import static epc.labs.guesstures.ProgressTableConstants.*;

public class DatabaseHelper extends SQLiteOpenHelper {
  private static final String TAG = "Guesstures";
  private static final String DATABASE_NAME = "guesstures.db";
  private static final int DATABASE_VERSION = 5;


  public DatabaseHelper(Context context) { 
  	super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
        + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + NAME + " TEXT UNIQUE,"
        + TIME + " INTEGER"
        + ");");  	
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
        + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
  }
  
  public int queryScore() {
  	SQLiteDatabase db = getReadableDatabase();
  	Cursor result = db.query(TABLE_NAME, null, null, null, null, null, null);
  	int score = result.getCount();
  	result.close();
  	return score;
  }
  
  public ArrayList<String> queryUnlockedGuesstures() {
	  SQLiteDatabase db = getReadableDatabase();
	  ArrayList<String> guesstures = new ArrayList<String>();
	  Cursor result = db.query(TABLE_NAME, new String[] {NAME}, null, null, null, null, TIME);
		while(result.moveToNext()) {
			guesstures.add(result.getString(result.getColumnIndex(NAME)));	  	
	  }
		result.close();
	  return guesstures;
  }
  
  public void resetScore() {
  	SQLiteDatabase db = getWritableDatabase();
  	db.delete(TABLE_NAME, null, null);
  }
   
  public boolean updateScore(String name) {
  	SQLiteDatabase db = getWritableDatabase();
  	ContentValues v = new ContentValues();
  	v.put(NAME, name);
  	v.put(TIME, System.currentTimeMillis());
  	int currScore = queryScore();
  	db.insert(TABLE_NAME, null, v);
  	return (currScore<queryScore());
  }
}