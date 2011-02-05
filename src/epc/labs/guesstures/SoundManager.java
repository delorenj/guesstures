package epc.labs.guesstures;

import java.util.HashMap;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

public class SoundManager {
	public boolean released=false;
	protected Context context;
	private SoundPool soundPool;
  private int volume;
  private HashMap<Integer, Integer> soundPoolMap;
	public static final int UNLOCK = 1;
	public static final int CHALK_START = 2;
	public static final int CHALK_DRAW = 3;

	protected SoundManager(int size, Context context) {
		this.context=context;
		// keep maxStreams high, ugly hack because pre cupcake android could deadlock
		// with sound pool
		soundPool=new SoundPool(size+20, AudioManager.STREAM_MUSIC, 40);
		soundPoolMap = new HashMap<Integer, Integer>();
		AudioManager mgr=(AudioManager)context.getSystemService
(Context.AUDIO_SERVICE);
		volume=mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
	}

	/*
	public boolean isLoaded() {
		soundPool.
	}
	*/

	public void addSound(int resid) {
		int soundId=soundPool.load(context, resid, 1);
		soundPoolMap.put(resid, soundId);
		soundPool.setLoop(soundId, 1);
	}

	public void addLoopSound(int resid) {
		int soundId=soundPool.load(context, resid, 1);
		soundPoolMap.put(resid, soundId);
		soundPool.setLoop(soundId, -1);
	}

	public void play(int resid) {
		Log.i("SoundEffects", "Playing: "+resid);
		int soundId=soundPoolMap.get(resid);
		soundPool.setLoop(soundId, 1);
		soundPool.play(soundId, volume, volume, 1, 0, 1f);
	}

	public int playLoop(int resid) {
		int soundId=soundPoolMap.get(resid);
		int instanceId = soundPool.play(soundId, volume, volume, 1, 0, 1f);
		soundPool.setLoop(instanceId, -1);
		soundPool.play(soundId, volume, volume, 1, 0, 1f);
		return instanceId;
	}

	public void stop(int soundId, int instanceId) {
		soundPool.stop(soundId);
		// apperently a bug, workaround
		soundPool.setLoop(instanceId, 0);
		soundPool.setVolume(soundId, 0f, 0f);
	}

	public void release() {
		released=true;
		soundPool.release();
	}
}
