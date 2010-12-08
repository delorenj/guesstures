package epc.labs.guesstures;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class ScoreView extends ViewGroup {
	public static final String TAG = "Guesstures";
	protected Context mContext;
	protected int mScore;
	protected String mstrScore;
	protected ArrayList<Drawable> mdrawScore;
	private ArrayList<Drawable> mFonts;
	
	
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
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if(changed) {
		}
		
	}
	
	protected void initFont() {
		int[] mFontIds = new int[10];
		mFontIds[0] = R.drawable.font_0;
		mFontIds[1] = R.drawable.font_1;
		mFontIds[2] = R.drawable.font_2;
		mFontIds[3] = R.drawable.font_3;
		mFontIds[4] = R.drawable.font_4;
		mFontIds[5] = R.drawable.font_5;
		mFontIds[6] = R.drawable.font_6;
		mFontIds[7] = R.drawable.font_7;
		mFontIds[8] = R.drawable.font_8;
		mFontIds[9] = R.drawable.font_9;
		
		for(int i=0; i<10; i++) {
			mFonts.add(i, mContext.getResources().getDrawable(mFontIds[i]));
		}		
	}
	
	protected void setScore(int score) {
		mScore = score;
		mstrScore = Integer.toString(score);
		mdrawScore.clear();
		int scoreWidth = 0;
		int scoreHeight = mFonts.get(0).getIntrinsicHeight();
		for(int i=0; i<mstrScore.length(); i++) {
			int digit = Integer.parseInt(mstrScore.charAt(i)+"");
			mdrawScore.add(mFonts.get(digit));
			scoreWidth += mFonts.get(digit).getIntrinsicWidth();
		}		
	}
}