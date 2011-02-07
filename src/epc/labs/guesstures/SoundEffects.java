package epc.labs.guesstures;

import android.content.Context;
import android.util.Log;

public class SoundEffects extends SoundManager {
	private static final String TAG = "Guesstures";
	private static SoundEffects INSTANCE;
	private int mDrawId;
	public static SoundEffects getInstance(Context ctx) {
		if(INSTANCE==null || INSTANCE.released)INSTANCE=new SoundEffects(ctx);
		return INSTANCE;
	}

	private SoundEffects(Context context) {
		super(3, context);

		addSound(R.raw.yay);
		addSound(R.raw.chalk_extended_short);
		addSound(R.raw.chalk_start);
	}

	public void chalkStart() {
		play(R.raw.chalk_start);
	}

	public void startChalkDraw() {
		Log.i(TAG, "Starting Chalk Drawing Motion Noise");
    mDrawId = playLoop(R.raw.chalk_extended_short);
	}

	public void stopChalkDraw() {
		Log.i(TAG, "Stopping Chalk Draw Noise");
		stop(mDrawId);
	}
	
	public void yay() {
		play(R.raw.yay);
	}
}
