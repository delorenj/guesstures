package epc.labs.guesstures;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class UIOverlay extends View {
	public static final String TAG = "Guesstures";
	private ScoreView mScoreView;
	private Bitmap mBackground;
	
	public UIOverlay(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScoreView = new ScoreView(context);

	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Log.i(TAG, "Overlay onDraw");

	}
	
}