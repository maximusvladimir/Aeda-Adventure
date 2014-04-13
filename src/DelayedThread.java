
public class DelayedThread {
	private Thread thread;
	public DelayedThread(final Runnable runnable, final long millis) {
		thread = new Thread(new Runnable() {
			public void run() {
				long l = System.currentTimeMillis();
				while (System.currentTimeMillis() - l < millis) {
					
				}
				if (runnable != null)
					runnable.run();
			}
		});
		thread.setName("delayedExecutionThread");
	}
	
	public void start() {
		thread.start();
	}
}
