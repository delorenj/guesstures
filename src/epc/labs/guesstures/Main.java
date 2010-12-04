package epc.labs.guesstures;

import java.util.ArrayList;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureUtils;
import android.gesture.Prediction;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class Main extends Activity implements OnGestureListener, OnGesturePerformedListener {
	private static final String TAG = "Guesstures";
	GestureLibrary mLibrary;
	
	@Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    mLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
    if(!mLibrary.load()) {
    	finish();
    }
    GestureOverlayView overlay = (GestureOverlayView) findViewById(R.id.gestureOverlay);
    overlay.addOnGesturePerformedListener(this);
    overlay.addOnGestureListener(this);
  }

	@Override
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		Log.i(TAG, "onGesturePerformed");
		double spatialScore;
		Gesture testGesture = mLibrary.getGestures("Balls").get(0);
		
		ImageView iv = (ImageView) findViewById(R.id.lpic);
		iv.setImageBitmap(testGesture.toBitmap(32, 32, 1, Color.WHITE));
		iv = (ImageView) findViewById(R.id.rpic);
		iv.setImageBitmap(gesture.toBitmap(32, 32, 1, Color.WHITE));
		
		SpatialCompare comp1 = new SpatialCompare();
		spatialScore = comp1.similarity(testGesture, gesture, 48);

		TextView tv = (TextView) findViewById(R.id.result);
		tv.setText(""+spatialScore);
	}


	private void printGestureDebugInfo(GestureOverlayView overlay, Gesture gesture, ArrayList<Prediction> predictions) {
		TextView tv = (TextView) findViewById(R.id.result);
		tv.setText("DEBUG:\r\n");
		tv.append("Num Strokes: " + gesture.getStrokesCount() + "\r\n");
		for(Prediction p : predictions) {
			tv.append(p.name + ": " + p.score + "\r\n");
		}	
	}

	private void onMatchFound(GestureOverlayView overlay, Gesture gesture, Prediction prediction) {
		Log.i(TAG, "Match Found!: "+prediction.name);
		TextView tv = (TextView)findViewById(R.id.result);
		tv.setText(prediction.name);
		
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