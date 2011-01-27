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
import android.widget.ImageView;

public class Guesstures extends Activity implements OnGestureListener, OnGesturePerformedListener {
	private static final String TAG = "Guesstures";
	private DatabaseHelper mDatabase;
	GestureLibrary[] mLibraries;
	
	@Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);    
    setContentView(R.layout.main);
    mLibraries = new GestureLibrary[2];
    mLibraries[0] = GestureLibraries.fromRawResource(this, R.raw.gestures1);
    mLibraries[1] = GestureLibraries.fromRawResource(this, R.raw.gestures2);
    
    for(GestureLibrary lib : mLibraries) {
      if(!lib.load()) {
      	finish();
      }    	
    }
    
    mDatabase = new DatabaseHelper(this);
    GestureOverlayView overlay = (GestureOverlayView) findViewById(R.id.gestureOverlay);
    overlay.addOnGesturePerformedListener(this);
    overlay.addOnGestureListener(this);
    Intent about = new Intent(this, About.class);
    if((savedInstanceState == null) && (mDatabase.queryScore() < 3)) {
  		startActivity(about);	
  	}
  }

	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		Log.i(TAG, "onGesturePerformed");
		int numStrokes = gesture.getStrokesCount();

		if(numStrokes <= mLibraries.length) {
				SpatialCompare sc = new SpatialCompare();
				sc.setLibrary(mLibraries[numStrokes-1]);
				sc.setActivity(this);
				sc.execute(gesture);
		}
	}

//	private void onMatchFound(GestureOverlayView overlay, Gesture l, Gesture r, String name) {
//		Log.i(TAG, "Match Found!: "+ name);
//		UIOverlay ui = (UIOverlay) findViewById(R.id.uiOverlay);
//		ui.updateScore(name);
//		ImageView iv = (ImageView) findViewById(R.id.lpic);
//		iv.setImageBitmap(l.toBitmap(48, 48, 1, Color.WHITE));
//		iv = (ImageView) findViewById(R.id.rpic);
//		iv.setImageBitmap(r.toBitmap(48, 48, 1, Color.WHITE));			
//		
//	}
	
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
		case R.id.tutorial:
			tutorial();
			return true;
		case R.id.gallery:
	    Intent gallery = new Intent(this, Gallery.class);
	    startActivity(gallery);	
			return true;			
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}

	private void tutorial() {
		Log.e(TAG, "Tutorial not implemented yet");
		
	}

	private void newGame() {
		UIOverlay ui = (UIOverlay) findViewById(R.id.uiOverlay);
		ui.resetScore();
	}

	public void onGesture(GestureOverlayView overlay, MotionEvent event) {
	}

	public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
		Log.i(TAG, "onGestureCancelled");
		
	}

	public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
		Log.i(TAG, "onGestureEnded");
		
	}

	public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
		Log.i(TAG, "onGestureStarted");
		
	}
}