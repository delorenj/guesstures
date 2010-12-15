package epc.labs.guesstures;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ScoreView extends ViewGroup {

	public static final String TAG = "Guesstures";
	private Context mContext;
	private int mScore;
	private String mstrScore;
	private Drawable[] mdrawScore;
	private Drawable[] mFont;

	public ScoreView(Context context) {
		super(context);
		mContext = context;
		setId(1);       // 1, for ScoreView id
		initFont();
	}

	public ScoreView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setId(1);       // 1, for ScoreView id		
		initFont();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		Log.d(TAG, "onLayout called!: l="+l+", t="+t+", r="+r+", b="+b);
		if(changed) {
			Log.i(TAG, "onLayout: Changed!");
			if(getChildCount() > 0) {
				Log.i(TAG, "onLayout: children="+getChildCount());
				int prevLeft = this.getWidth();
				for(int i=getChildCount()-1; i>=0; i--) {
					ImageView iv = (ImageView)getChildAt(i);
					int newLeft = prevLeft - iv.getDrawable().getIntrinsicWidth()-5;
					iv.layout(newLeft,
							5,
							prevLeft-5,
							iv.getDrawable().getIntrinsicHeight()+5);
					Log.d(TAG, "      Digit #"+i+": l="+newLeft+", t=5, r="+(prevLeft-5)+", b="+(iv.getDrawable().getIntrinsicHeight()+5));
					prevLeft = newLeft;
				}
			}
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

	public void setScore(int score) {
		Log.d(TAG, "setScore(): " + score);
		mScore = score;
		mstrScore = Integer.toString(score);
		Log.d(TAG, "setScore(): mstrScore=" + mstrScore);
		mdrawScore = new Drawable[mstrScore.length()];
		Log.d(TAG, "mScore:" + mScore);
		Log.d(TAG, "mstrScore:" + mstrScore);
		Log.d(TAG, "mstrScore.length(): "+mstrScore.length());
		Log.d(TAG, "mFont.length(): " + mFont.length);
		for(int i=0; i<mstrScore.length(); i++) {
			Log.d(TAG, "Loop " + i + ": " + Integer.parseInt(mstrScore.charAt(i)+""));
			mdrawScore[i] = mFont[Integer.parseInt(mstrScore.charAt(i)+"")];
			ImageView iv = new ImageView(this.getContext());
			iv.setId(i);
			iv.setImageDrawable(mdrawScore[i]);
			addView(iv);
		}
	}
}
