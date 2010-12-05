package epc.labs.guesstures;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class UIOverlay extends View {
	public static final String TAG = "Guesstures";
	private Drawable mScore;
	
	public UIOverlay(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScore = context.getResources().getDrawable(R.drawable.icon);

	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Log.i(TAG, "Overlay onDraw");
		int scoreWidth = mScore.getIntrinsicWidth();
		int scoreHeight = mScore.getIntrinsicHeight();
		mScore.setBounds(getWidth() - scoreWidth, 0, getWidth(), scoreHeight);
		mScore.draw(canvas);

	}
	
}