package epc.labs.guesstures;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;

public class Guesstures extends Activity implements OnGestureListener, OnGesturePerformedListener {
	private static final String TAG = "Guesstures";
	public static final boolean DEBUG = true;
	private DatabaseHelper mDatabase;
	private SoundEffects se;
	GestureLibrary mLibrary;
	
	@Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);    
    setContentView(R.layout.main);
    se = SoundEffects.getInstance(this);
    mLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
    
    if(!mLibrary.load()) {
      	finish();
    }    	
    
    mDatabase = new DatabaseHelper(this);
    GestureOverlayView overlay = (GestureOverlayView) findViewById(R.id.gestureOverlay);
    overlay.addOnGesturePerformedListener(this);
    overlay.addOnGestureListener(this);
    overlay.setGestureColor(Color.WHITE);
    overlay.setGestureStrokeWidth(12);
    Intent about = new Intent(this, About.class);
    if((savedInstanceState == null) && (mDatabase.queryScore() < 3)) {
  		startActivity(about);	
  	}
  }

	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		Log.i(TAG, "onGesturePerformed");
		SpatialCompare sc = new SpatialCompare();
		sc.setLibrary(mLibrary);
		sc.setActivity(this);
		sc.execute(gesture);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.canvas_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.new_game:
			newGame();
			return true;
		case R.id.settings:
			startActivity(new Intent(this, Prefs.class));
			return true;
		case R.id.gallery:
	    Intent gallery = new Intent(this, Gallery.class);
	    startActivity(gallery);	
			return true;			
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}

	private void newGame() {
		UIOverlay ui = (UIOverlay) findViewById(R.id.uiOverlay);
		ui.resetScore();
	}

	public void onGesture(GestureOverlayView overlay, MotionEvent event) {
//		Log.i(TAG, "onGesture");
	}

	public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
//		Log.i(TAG, "onGestureCancelled");
		if(Prefs.continuousChalkSoundOn(this)) {		
			se.stopChalkDraw();		
		}
	}

	public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
//		Log.i(TAG, "onGestureEnded");
		if(Prefs.continuousChalkSoundOn(this)) {		
			se.stopChalkDraw();		
		}
	}

	public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
//		Log.i(TAG, "onGestureStarted");
		if(Prefs.chalkSoundOn(this)) {
			se.chalkStart();
			if(Prefs.continuousChalkSoundOn(this)) {
				se.startChalkDraw();	
			}						
		}
	}
}