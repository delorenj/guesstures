package epc.labs.guesstures;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GalleryImageAdapter extends BaseAdapter {
	private static String TAG = "Guesstures";
  private Context mContext;
	private DatabaseHelper mDatabase;
	private int[] mThumbIds;
	
    public GalleryImageAdapter(Context c) {
        mContext = c;
        mDatabase = new DatabaseHelper(c);
        initThumbIds();
    }

    private void initThumbIds() {
      Log.i(TAG, "Entered function: initThumbIds");
      ArrayList<String> guesstures = mDatabase.queryUnlockedGuesstures();
      mThumbIds = new int[guesstures.size()];
      int i = 0;
      for(String guessture : guesstures) {
        	int id = mContext.getResources().getIdentifier(guessture, "drawable", "epc.labs.guesstures");
        	Log.i(TAG, "Identifier: " + id);
        	mThumbIds[i] = id;
        	i++;
      }    	
    }
    public int getCount() {
        return mDatabase.queryScore();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setPadding(5, 5, 5, 15);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
//    private Integer[] mThumbIds = {
//            R.drawable.tree, R.drawable.balls,
//            R.drawable.font_2, R.drawable.font_3,
//            R.drawable.font_4, R.drawable.font_5,
//            R.drawable.font_6, R.drawable.font_7,
//            R.drawable.font_8, R.drawable.font_9
//    };
}