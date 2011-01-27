package epc.labs.guesstures;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

public class UIOverlay extends ViewGroup {
	public static final String TAG = "Guesstures";
	private ScoreView mScoreView;
	private Context mContext;
	private UnlockAnimationView mUnlockAnimationView;
	private DatabaseHelper mDatabase;
	
	public UIOverlay(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mScoreView = new ScoreView(context, attrs);
		mUnlockAnimationView = new UnlockAnimationView(context, attrs);
		mDatabase = new DatabaseHelper(context);
		mScoreView.drawScore(mDatabase.queryScore());
		addView(mScoreView);
		mUnlockAnimationView.setVisibility(View.INVISIBLE);
		addView(mUnlockAnimationView);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
//		Log.i(TAG, "Overlay onDraw");
	}

	public void updateScore(String name) {
		if(mDatabase.updateScore(name)) {							// Update score in database
			mScoreView.drawScore(mDatabase.queryScore());// Draw new score
		}
		mUnlockAnimationView.setSprite(name);
		Animation hyperspaceJump = AnimationUtils.loadAnimation(mContext, R.anim.unlock_anim);
		mUnlockAnimationView.startAnimation(hyperspaceJump);
	}

	public void resetScore() {
		mDatabase.resetScore();
		mScoreView.drawScore(0);
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