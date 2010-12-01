package epc.labs.guesstures;

import java.util.ArrayList;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Main extends Activity implements OnGesturePerformedListener {
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
  }

	@Override
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		ArrayList<Prediction> predictions = mLibrary.recognize(gesture);
		if(predictions.size() > 0) {
			printGestureDebugInfo(overlay, gesture, predictions);
			Prediction prediction = predictions.get(0);
			if(prediction.score > 1.0) {
				onMatchFound(overlay, gesture, prediction);
			}
		}
	}

	private void printGestureDebugInfo(GestureOverlayView overlay,
		Gesture gesture, ArrayList<Prediction> predictions) {
		TextView tv = (TextView) findViewById(R.id.result);
		tv.setText("DEBUG:\r\n");
		tv.append("Num Strokes: " + gesture.getStrokesCount() + "\r\n");
		for(Prediction p : predictions) {
			tv.append(p.name + ": " + p.score + "\r\n");
		}
		
	}

	private void onMatchFound(GestureOverlayView overlay, Gesture gesture, Prediction prediction) {
		Log.i(TAG, "Match Found!: "+prediction.name);
		
	}
}