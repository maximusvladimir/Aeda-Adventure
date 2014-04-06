import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

public class SoundManager {
	private static Thread soundThread;
	private static boolean ENTRANCE = false;
	public static void start() {
		if (ENTRANCE)
			return;
		ENTRANCE = true;
		soundThread = new Thread(new Runnable() {
			public void run() {
				try {
					backgroundStream = AudioSystem
							.getAudioInputStream(SoundManager.class
									.getResource("background.wav"));
					clickStream = AudioSystem
							.getAudioInputStream(SoundManager.class
									.getResource("click.wav"));
					fs1Stream = AudioSystem
							.getAudioInputStream(SoundManager.class
									.getResource("footsteps2.wav"));
					fs2Stream = AudioSystem
							.getAudioInputStream(SoundManager.class
									.getResource("footsteps3.wav"));
					gemStream = AudioSystem
							.getAudioInputStream(SoundManager.class
									.getResource("gem.wav"));
				} catch (Throwable t) {
					t.printStackTrace();
				}
				Clip clipBackground = null;
				Clip clipClick = null;
				Clip clipFs1= null;
				Clip clipFs2 = null;
				Clip clipGem= null;
				try {
					clipBackground = AudioSystem.getClip();
					clipClick = AudioSystem.getClip();
					clipClick.open(clickStream);
					clipFs1 = AudioSystem.getClip();
					clipFs1.open(fs1Stream);
					clipFs2 = AudioSystem.getClip();
					clipFs2.open(fs2Stream);
					clipGem = AudioSystem.getClip();
					clipGem.open(gemStream);
					
				} catch (Throwable e) {
					e.printStackTrace();
				}
				
				while (runAudioSystem) {
					if (!runCheckOnce(clipClick, clickStream, playClick)) {
						playClick = false;
					}
					if (!runCheckOnce(clipFs1, fs1Stream, playFootstep1)) {
						playFootstep1 = false;
					}
					if (!runCheckOnce(clipFs2, fs2Stream, playFootstep2)) {
						playFootstep2 = false;
					}
					if (!runCheckOnce(clipGem, gemStream, playGem)) {
						playGem = false;
					}
					if (playBackground) {
						if (runCheck(clipBackground, backgroundStream)) {
							trySetVolume(clipBackground, 400);
						}
					} else
						clipHalt(clipBackground);
					for (int i = 0; i < 1; i++)
						Thread.yield();
				}
			}
		});
		soundThread.setPriority(Thread.MIN_PRIORITY);
		soundThread.setName("SOUNDMANAGER");
		//soundThread.start();
	}

	private static void trySetVolume(Clip clip, float decibels) {
		try {
			FloatControl gainControl = (FloatControl) clip
					.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(decibels);
		} catch (Throwable t) {

		}
	}

	private static void clipHalt(Clip clip) {
		try {
			System.out.println("Clip stopped.");
			clip.close();
			System.gc();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private static boolean runCheck(Clip clip, AudioInputStream stream) {
		if (!clip.isOpen()) {
			try {
				clip.open(stream);
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} catch (Throwable t) {
				System.err.println(t.getMessage());
			}
			return true;
		} else
			return false;
	}

	private static boolean runCheckOnce(Clip clip, AudioInputStream stream,
			boolean set) {
		if (set) {
			if (!clip.isActive()) {
				clip.start();
				return false;
			}
		}
		else {
			if (!clip.isActive()) {
				clip.setFramePosition(0);
			}
		}
		return true;
	}

	private static AudioInputStream backgroundStream;
	private static AudioInputStream clickStream;
	private static AudioInputStream fs1Stream;
	private static AudioInputStream fs2Stream;
	private static AudioInputStream gemStream;
	public static boolean playBackground = true;
	public static boolean playClick = false;
	public static boolean playFootstep1 = false;
	public static boolean playFootstep2 = false;
	public static boolean playGem = false;
	public static boolean runAudioSystem = true;
	
	public static Sound backgroundSound = null;
	public static boolean soundEnabled = true;
}
