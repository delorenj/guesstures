package epc.labs.guesstures;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class UnlockAnimationView extends View {
	private static String TAG = "Guesstures";
	private static final int SPRITE_HEIGHT = 85;
	private static final int SPRITE_WIDTH = 85;
	private Context mContext;
	private Drawable mSprite;
	
	public UnlockAnimationView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}
	
	public void setSprite(String name) {
		Log.i(TAG, "UnlockAnim: setSprite()");
		name = name.replace(" ", "_");
		name = name.replace("-", "_");			
		int id = mContext.getResources().getIdentifier(name, "drawable", "epc.labs.guesstures");
  	if(id == 0) {
  		Log.e(TAG, "Missing Gallery Image: " + name);
  		id = mContext.getResources().getIdentifier("no_photo", "drawable", "epc.labs.guesstures");
  	} 		
		mSprite = mContext.getResources().getDrawable(id);
		Rect bounds = new Rect();
		bounds.set(	getWidth()/2 - (SPRITE_WIDTH/2),
				getHeight()/2 -(SPRITE_HEIGHT/2), 
				getWidth()/2 + (SPRITE_WIDTH/2), 
				getHeight()/2 +(SPRITE_HEIGHT/2));
		mSprite.setBounds(bounds);
		setVisibility(View.VISIBLE);
		invalidate(bounds);
	}
	
	@Override
	protected void onDraw(Canvas c) {
		//Log.i(TAG, "UnlockAnim: onDraw()");
		if(getVisibility() == View.VISIBLE) {
			mSprite.draw(c);
		}
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		Log.i(TAG, "UnlockAnim: onLayout()");
		if(changed && (getVisibility() == View.VISIBLE)) {
			Rect bounds = new Rect();
			bounds.set(	getWidth()/2 - (SPRITE_WIDTH/2),
					getHeight()/2 -(SPRITE_HEIGHT/2), 
					getWidth()/2 + (SPRITE_WIDTH/2), 
					getHeight()/2 +(SPRITE_HEIGHT/2));
			mSprite.setBounds(bounds);				
		}
	}
	
	public void initAnimation() {
		Log.i(TAG, "UnlockAnim: initAnim()");
		invalidate();
	}
}
