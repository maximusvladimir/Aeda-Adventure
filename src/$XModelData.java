import java.util.*;
import java.io.PrintStream;

public class $XModelData {
	//These variables are the header information
	public int hMajor = 0;
	public int hMinor = 0;
	public long hFlags = 0;
	public Vector FrameModelList = new Vector();
	public Vector AnimationSetList = new Vector();
	
	//This is for storing a display list id
	//The loader does not set this directly
	public int dlid = -1;
	
	public void recurseFrames(Vector fml,PrintStream dos) {
		for (int i = 0; i < fml.size(); i++) {
			$FrameModel fm = ($FrameModel)fml.elementAt(i);
			dos.println(fm.toString());
			recurseFrames(fm.FrameModelList,dos);
		}
	}
	
	public void outputData(PrintStream dos) {
		dos.println(toString());
		recurseFrames(FrameModelList,dos);
		for (int i = 0; i < AnimationSetList.size(); i++) {
			dos.println(AnimationSetList.elementAt(i).toString()+"\n");
		}
	}
	
	public String toString() {
		return("Header File Version: "+hMajor+"."+hMinor+" Flags: "+hFlags);
	}
	//Recursively check each frame for a name match, and it's sub-frames.
	public $FrameModel searchFrame(Vector fml,String name) {
		for (int i = 0; i < fml.size(); i++) {
			
			$FrameModel fm = ($FrameModel)fml.elementAt(i);
			if (fm.name.equalsIgnoreCase(name)) {
				return(fm);
			}
			fm = searchFrame(fm.FrameModelList,name);
			if (fm != null) {
				if (fm.name.equalsIgnoreCase(name)) {
					return(fm);
				}
			}
		}
		return(null);
	}
	
	public $FrameModel getFrameByName(String name) {
		$FrameModel fm = searchFrame(FrameModelList,name);
		if (fm == null) {
			System.out.println("Couldn't find frame: "+name);
		}
		return(fm);
	}
}