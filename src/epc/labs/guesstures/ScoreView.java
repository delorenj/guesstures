package epc.labs.guesstures;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ScoreView extends View {

	public static final String TAG = "Guesstures";
	private Context mContext;
	private String mstrScore;
	private Drawable[] mdrawScore;
	private Drawable[] mFont;

	public ScoreView(Context context) {
		super(context);
		mContext = context;
		initFont();
	}

	public ScoreView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;		
		initFont();
	}

	@Override
	protected void onDraw(Canvas c) {
//		Log.d(TAG, "onDraw(): ScoreView");
		if(mdrawScore.length == 0)
			return;
		int prevLeft = getWidth();
		int bottom = mdrawScore[0].getIntrinsicHeight();
		for(int i=mdrawScore.length-1; i>=0; i--) {
			Drawable d = mdrawScore[i];
			int newLeft = prevLeft - d.getIntrinsicWidth()-5;
			Rect bounds = new Rect();
			bounds.set(newLeft, 5, prevLeft-5, bottom+5);
			d.setBounds(bounds);
			d.draw(c);
			prevLeft = newLeft;
		}
	}
	
	public void initFont() {
		mFont = new Drawable[10];
		mFont[0] = mContext.getResources().getDrawable(R.drawable.font_0);
		mFont[1] = mContext.getResources().getDrawable(R.drawable.font_1);
		mFont[2] = mContext.getResources().getDrawable(R.drawable.font_2);
		mFont[3] = mContext.getResources().getDrawable(R.drawable.font_3);
		mFont[4] = mContext.getResources().getDrawable(R.drawable.font_4);
		mFont[5] = mContext.getResources().getDrawable(R.drawable.font_5);
		mFont[6] = mContext.getResources().getDrawable(R.drawable.font_6);
		mFont[7] = mContext.getResources().getDrawable(R.drawable.font_7);
		mFont[8] = mContext.getResources().getDrawable(R.drawable.font_8);
		mFont[9] = mContext.getResources().getDrawable(R.drawable.font_9);
	}

	public void drawScore(int score) {
		Log.d(TAG, "setScore(): " + score);
		mstrScore = Integer.toString(score);
		mdrawScore = new Drawable[mstrScore.length()];
		for(int i=0; i<mstrScore.length(); i++) {
			mdrawScore[i] = mFont[Integer.parseInt(mstrScore.charAt(i)+"")];
		}
		invalidate();
	}
}
