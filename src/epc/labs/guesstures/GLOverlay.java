package epc.labs.guesstures;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class GLOverlay extends GLSurfaceView {

	public GLOverlay(Context context) {
		super(context);
		setRenderer(new OpenGLRenderer());
	}

	public GLOverlay(Context context, AttributeSet attrs) {
		super(context, attrs);
		setRenderer(new OpenGLRenderer());
	}	
}
