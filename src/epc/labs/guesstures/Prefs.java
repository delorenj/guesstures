package epc.labs.guesstures;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Prefs extends PreferenceActivity {
	private static final String OPT_CHALK_SOUND = "chalk";
	private static final boolean OPT_CHALK_SOUND_DEF = true;
	private static final String OPT_CONTINUOUS_CHALK_SOUND = "continuous_chalk";
	private static final boolean OPT_CONTINUOUS_CHALK_SOUND_DEF = false;	
	
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		addPreferencesFromResource(R.xml.settings);
	}
	
	public static boolean chalkSoundOn(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(OPT_CHALK_SOUND, OPT_CHALK_SOUND_DEF);
	}

	public static boolean continuousChalkSoundOn(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(OPT_CONTINUOUS_CHALK_SOUND, OPT_CONTINUOUS_CHALK_SOUND_DEF);
	}
}
