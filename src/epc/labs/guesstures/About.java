package epc.labs.guesstures;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class About extends Activity {
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.about);
        final Button button = (Button) findViewById(R.id.about_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               finish();
            }
        });
	}
}