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
//		recognizeByPrediction(overlay, gesture);
		recognizeByImageCompare(overlay, gesture);
	}

	private void recognizeByImageCompare(GestureOverlayView overlay, Gesture gesture) {
		Log.i(TAG, "Comparing by bitmap");
		final int bmpHeight = 16, bmpWidth = 16;
		final int bmpSize = bmpHeight*bmpWidth;
		float[] bss = new float[bmpSize];
		float[] gss = new float[bmpSize];
		double score;
		ArrayList<Gesture> balls = mLibrary.getGestures("Balls");
		Gesture ball = balls.get(0);
		Bitmap ballPic = ball.toBitmap(bmpWidth, bmpHeight, 1, Color.WHITE);
		Bitmap gesturePic = gesture.toBitmap(bmpWidth, bmpHeight, 1, Color.WHITE);
		ImageView iv = (ImageView) findViewById(R.id.libraryBitmap);
		iv.setImageBitmap(ballPic);
		iv = (ImageView) findViewById(R.id.gestureBitmap);
		iv.setImageBitmap(gesturePic);		
		gss = GestureUtils.spatialSampling(gesture, bmpHeight, true);		
		bss = GestureUtils.spatialSampling(ball, bmpHeight, true);
		int matchedPixels = 0;
		for(int i=0 ;i<bmpSize; i++) {
			Log.i(TAG, ""+gss[i] + ", " + bss[i]);
			if((gss[i] > 0) && (bss[i] > 0)) {
				matchedPixels++;//pixels have some gray in them (MATCH?!)
			}
			else if((gss[i] == 0) && (bss[i] == 0)) { //pixels have no gray in them (MATCH?!)
				matchedPixels++;
			}
		}
		score = (double)((double)matchedPixels/(double)bmpSize);
		TextView tv = (TextView) findViewById(R.id.debug);
		tv.setText("Spatial Sampling Score: "+score);
		recognizeByPrediction(overlay, gesture);
		
	}

	private void recognizeByPrediction(GestureOverlayView overlay, Gesture gesture) {
		Log.i(TAG, "Comparing with native predictions");
		ArrayList<Prediction> predictions = mLibrary.recognize(gesture);
		if(predictions.size() > 0) {
//			printGestureDebugInfo(overlay, gesture, predictions);
			Prediction prediction = predictions.get(0);
			if(prediction.score > 1.0) {
				onMatchFound(overlay, gesture, prediction);
			}
		}
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