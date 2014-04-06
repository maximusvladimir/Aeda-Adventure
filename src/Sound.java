import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;


public class Sound {
	private static HashSet<SoundSet> clips = new HashSet<SoundSet>();
	private SoundSet thisSet;
	private String clipName;
	public Sound(String clipName) {
		this.clipName = clipName;
		SoundSet s = new SoundSet(clipName);
		if (!clips.contains(s)) {
			if (!s.load()) {
				System.err.println("Unable to load sound clip: " + clipName);
			}
		}
		thisSet = s;
		LineListener listener = new LineListener() {
	        public void update(LineEvent event) {
				if (event.getType() != LineEvent.Type.STOP) {
					return;
				}
				try {
					thisSet.clip.close();
					thisSet.clip.drain();
					thisSet.clip.flush();
				}
				catch (Throwable t) {
					
				}
	        }
	    };
	    thisSet.clip.addLineListener(listener );
	}
	
	private boolean markedPositionSupport = false;
	private boolean markedVolumeSupport = false;
	private boolean loop = false;
	
	
	public void setLooping(boolean v) {
		loop = v;
	}
	
	public boolean isLooping() {
		return loop;
	}
	
	public void halt() {
		thisSet.clip.stop();
		thisSet.unload();
		clips.remove(thisSet);
	}
	
	public void play() {
		if (isLooping())
			thisSet.clip.loop(Clip.LOOP_CONTINUOUSLY);
		else
			thisSet.clip.loop(0);
		thisSet.clip.start();
	}
	
	/**
	 * Sets the decibels of volume.
	 * @param v
	 */
	public void setVolume(float v) {
		if (!thisSet.clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
			if (!markedVolumeSupport) {
				markedVolumeSupport = true;
				System.err.println("Volume is not supported for " + clipName);
			}
			return;
		}
		FloatControl cct = (FloatControl) thisSet.clip.getControl(FloatControl.Type.MASTER_GAIN);
		if (v > cct.getMaximum())
			v = cct.getMaximum();
		if (v < cct.getMinimum())
			v = cct.getMinimum();
		cct.setValue(v);
	}
	
	public void setPosition(float x) {
		if (x > 1)
			x = 1;
		if (x < -1)
			x = -1;
		FloatControl pan = null;
		if (thisSet.clip.isControlSupported(FloatControl.Type.PAN)) {
			pan = (FloatControl) thisSet.clip.getControl(FloatControl.Type.PAN);
		}
		else if (thisSet.clip.isControlSupported(FloatControl.Type.BALANCE)) {
			pan = (FloatControl) thisSet.clip.getControl(FloatControl.Type.BALANCE);
		}
		else if (!markedPositionSupport){
			markedPositionSupport = true;
			System.err.println("No pan/balance is supported for " + clipName);
		}
		if (pan != null)
			pan.setValue(x);
	}
}

class SoundSet {
	private String clipName;
	private AudioInputStream ais;
	public Clip clip;
	public SoundSet(String clipName) {
		this.clipName = clipName;
		if (this.clipName.indexOf(".wav") < 0)
			this.clipName += ".wav";
	}
	
	public boolean load() {
		try {
			//URL url = Sound.class.getResource("/music/"+clipName);
			InputStream is = Sound.class.getResource("/music/"+clipName).openStream();
	        BufferedInputStream url = new BufferedInputStream( is );
			ais = AudioSystem.getAudioInputStream(url);
			clip =  AudioSystem.getClip();
			clip.open(ais);
			return true;
		}
		catch (Throwable t) {
			System.out.println(t.getMessage());
		}
		return false;
	}
	
	public boolean unload() {
		try {
			clip.close();
			ais.close();
			return true;
		}
		catch (Throwable t) {
			
		}
		return false;
	}
	
	public boolean equals(Object o) {
		if (o instanceof SoundSet) {
			SoundSet other = (SoundSet)o;
			if (other.clipName.toLowerCase().equals(clipName.toLowerCase()))
				return true;
		}
		return false;
	}
}