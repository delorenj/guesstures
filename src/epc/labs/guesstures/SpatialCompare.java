package epc.labs.guesstures;

import android.gesture.Gesture;
import android.gesture.GestureUtils;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

public class SpatialCompare implements ImageCompare {
	private static final String TAG = "Guesstures";

	@Override
	public double similarity(Gesture l, Gesture r, int size) {
		Log.i(TAG, "Comparing by bitmap");
		final int bmpHeight = size, bmpWidth = size;
		final int bmpSize = bmpHeight*bmpWidth;
		float[] lss = new float[bmpSize];
		float[] rss = new float[bmpSize];
		double score;
		lss = GestureUtils.spatialSampling(l, bmpHeight, true);		
		rss = GestureUtils.spatialSampling(r, bmpHeight, true);
//		Log.i(TAG,"Original: " + printArray(lss));
//		Log.i(TAG, "Awesome-o:" + printArray(rss));
		int matchedWhites = 0;
		int matchedBlacks = 0;
		double blackMass = 0;
		
		for(int i=0 ;i<bmpSize; i++) {
//			if(i%1 == 0) Log.i(TAG, ""+lss[i] + ", " + rss[i]);lss.
			if((lss[i] > 0) && (rss[i] > 0)) {
				matchedWhites++;//pixels have some gray in them (MATCH?!)
			}
			else if((lss[i] == 0) && (rss[i] == 0)) { //pixels have no gray in them (MATCH?!)
				matchedBlacks++;
			}
			
			if(lss[i] == 0) {
				blackMass++;
			}
		}
		blackMass = (double)(blackMass/(double)bmpSize);
		score = (double)((double)matchedWhites/(double)bmpSize);
		Log.i(TAG, "Black Mass: " + blackMass);
		Log.i(TAG, "Spatial Sampling Score: "+score);
		return score;
	}

	@Override
	public double similarity(Gesture l, Gesture r) {
		return similarity(l,r,32);
	}

	static String printArray(float[] objArr) { 
		String str=""; 
		for (int i=0; i<objArr.length; i++){ 
			str=str + objArr[i] + ",";
		} 
		return str.substring(0, str.length()-1);
		
	} 	
}