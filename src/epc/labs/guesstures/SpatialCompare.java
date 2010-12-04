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
		lss = GestureUtils.spatialSampling(l, bmpHeight);		
		rss = GestureUtils.spatialSampling(r, bmpHeight);
//		Log.i(TAG,"Original: " + printArray(lss));
//		Log.i(TAG, "Awesome-o:" + printArray(rss));
		int matchedWhites = 0;
		int lWhites = 0;
		double bM_l = 0, bM_r = 0;
		double bM_delta;
		
		for(int i=0 ;i<bmpSize; i++) {
//			if(i%1 == 0) Log.i(TAG, ""+lss[i] + ", " + rss[i]);lss.
			if((lss[i] > 0) && (rss[i] > 0)) {
				if(Math.abs(lss[i]-rss[i]) < 0.05) {
					Log.i(TAG,"Match: " + Math.abs(lss[i]-rss[i]));
					matchedWhites++;//pixels have some gray in them (MATCH?!)
				}
			}
			if(lss[i] == 0) {
				bM_l++;
			}
			if(rss[i] == 0) {
				bM_r++;
			}
			if(lss[i] > 0) {
				lWhites++;
			}			
		}
		bM_l = (double)(bM_l/(double)bmpSize);
		bM_r = (double)(bM_r/(double)bmpSize);
		bM_delta = Math.abs(bM_l - bM_r);
		score = (double)((double)(matchedWhites)/(double)lWhites);
		Log.i(TAG, "Black Mass L: " + bM_l);
		Log.i(TAG, "Black Mass R: " + bM_r);
		Log.i(TAG, "Black Mass Delta: " + bM_delta);
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