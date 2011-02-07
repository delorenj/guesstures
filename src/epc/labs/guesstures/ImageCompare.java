package epc.labs.guesstures;

import android.gesture.Gesture;

public interface ImageCompare {

	double similarity(Gesture l, Gesture r);

	double similarity(Gesture l, Gesture r, int size);
	
	//String recognize(Gesture subject);
}