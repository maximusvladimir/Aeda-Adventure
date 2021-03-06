import java.awt.image.DataBufferInt;


public interface IMain {
	public int getWidth();
	public int getHeight();
	public void setActiveScreen(String st);
	public Screen getScreen(String st);
	public int getFPS();
	public boolean isFullscreen();
	public int getDrawTime();
	
	public boolean screenExists(String t);
	public void addScreen(Screen s);
	public void removeScreen(Screen s);
	public int getNumScreens();
	
	public int getActiveScreen();
	public Screen getScreen(int index);
	
	public boolean isPaused();
	public void pause();
	public void resume();
	
	public int getPixel(int x, int y);
	public DataBufferInt getDBI();
}