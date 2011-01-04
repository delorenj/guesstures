package epc.labs.guesstures;

import java.util.ArrayList;

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
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class Guesstures extends Activity implements OnGestureListener, OnGesturePerformedListener {
	private static final String TAG = "Guesstures";
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
    
    GestureOverlayView overlay = (GestureOverlayView) findViewById(R.id.gestureOverlay);
    overlay.addOnGesturePerformedListener(this);
    overlay.addOnGestureListener(this);
    Intent about = new Intent(this, About.class);
    if(savedInstanceState == null) {
  		startActivity(about);	
  	}
  }

	@Override
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		Log.i(TAG, "onGesturePerformed");
		int numStrokes = gesture.getStrokesCount();
		String result = "No Match";
		ArrayList<Gesture> libMatch = null;
		SpatialCompare comp1 = new SpatialCompare();
		
		if(numStrokes >= mLibraries.length) {
			result = comp1.recognize(mLibraries[numStrokes-1], gesture);
			libMatch = mLibraries[numStrokes-1].getGestures(result);
		}
		
		TextView tv = (TextView) findViewById(R.id.result);
		tv.setText(result);
		
		if(libMatch != null) {
			onMatchFound(overlay, libMatch.get(0), gesture, result);
		}
		else {
			ImageView iv = (ImageView) findViewById(R.id.lpic);
			iv.setImageBitmap(null);
			iv = (ImageView) findViewById(R.id.rpic);
			iv.setImageBitmap(null);						
		}
	}

	private void onMatchFound(GestureOverlayView overlay, Gesture l, Gesture r, String name) {
		Log.i(TAG, "Match Found!: "+ name);
		UIOverlay ui = (UIOverlay) findViewById(R.id.uiOverlay);
		ui.updateScore(name);
		ImageView iv = (ImageView) findViewById(R.id.lpic);
		iv.setImageBitmap(l.toBitmap(48, 48, 1, Color.WHITE));
		iv = (ImageView) findViewById(R.id.rpic);
		iv.setImageBitmap(r.toBitmap(48, 48, 1, Color.WHITE));			
		
	}

	@Override
	public void onGesture(GestureOverlayView overlay, MotionEvent event) {
	}

	@Override
	public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
		Log.i(TAG, "onGestureCancelled");
		
	}

	@Override
	public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
		Log.i(TAG, "onGestureEnded");
		
	}

	@Override
	public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
		Log.i(TAG, "onGestureStarted");
		
	}
}