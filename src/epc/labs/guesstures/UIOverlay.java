package epc.labs.guesstures;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class UIOverlay extends ViewGroup {
	public static final String TAG = "Guesstures";
	private ScoreView mScoreView;
	private DatabaseHelper mDatabase;
	
	public UIOverlay(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScoreView = new ScoreView(context, attrs);
		mDatabase = new DatabaseHelper(context);
		boolean updated = mDatabase.updateScore("Beef");
		Log.i(TAG,"Updated?: "+updated);
		mScoreView.setScore(mDatabase.queryScore());
		addView(mScoreView);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Log.i(TAG, "Overlay onDraw");
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if(!changed) return;	//return if nothing has changed
		for(int i=0; i<getChildCount(); i++) {
			View v = (View) getChildAt(i);
			Log.d(TAG,"UIOverlay: onLayout()");
			v.layout(l, t, r, b);
		}
	}
}