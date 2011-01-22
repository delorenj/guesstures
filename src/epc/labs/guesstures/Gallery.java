package epc.labs.guesstures;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class Gallery extends Activity {
	private static String TAG = "Guesstures";
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.gallery);
		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new GalleryImageAdapter(this));
	}
}
