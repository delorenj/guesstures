package epc.labs.guesstures;

import java.util.ArrayList;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.os.Bundle;
import android.util.Log;

public class Main extends Activity implements OnGesturePerformedListener {
		private static final String TAG = "Guesstures";
		GestureLibrary mLibrary;
		
		@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

		@Override
		public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
			ArrayList<Prediction> predictions = mLibrary.recognize(gesture);
			if(predictions.size() > 0) {
				Prediction prediction = predictions.get(0);
				if(prediction.score > 1.0) {
					onMatchFound(overlay, gesture, prediction);
				}
			}
		}

		private void onMatchFound(GestureOverlayView overlay, Gesture gesture, Prediction prediction) {
			Log.i(TAG, "Match Found!: "+prediction.name);
			
		}
}