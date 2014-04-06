import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Model {
	private Operation3D[] stacks;
	private int next = 0;
	private DrawType meshType = DrawType.Points;
	private PointTesselator internalTesselator;
	public Model() {
		internalTesselator = new PointTesselator();
	}
	
	public PointTesselator doNotPlayAroundWithThisMethodPleaseThanksIReallyAppreciateIt() {
		internalTesselator.setDrawType(meshType);
		return internalTesselator;
	}
	
	public boolean loadModel(File file) {
		ArrayList<Operation3D> tmpB = new ArrayList<Operation3D>();
		long queryStart = System.currentTimeMillis();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		String line;
		long debugLines = 0;
		boolean foundTheDrawType = false;
		try {
			while ((line = br.readLine()) != null) {
				// I hate spaces when reading files.
				if (line.indexOf(" ") > -1) {
					line = line.replace(" ", "");
				}
				// Skip over comments.
				if (line.startsWith("//")) {
					debugLines++;
					continue;
				}
				
				// Convert everything to lowercase to make life easier.
				line = line.toLowerCase();
				
				// I hate reading parenthesis in file readers.
				if (line.indexOf("(") > -1)
					line = line.replace("(", "");
				if (line.indexOf(")") > -1)
					line = line.replace(")", "");
				
				// Lets start with scanning for points.
				if (line.startsWith("p")) {
					float parsableX = Float.MAX_VALUE;
					float parsableY = Float.MAX_VALUE;
					float parsableZ = Float.MAX_VALUE;
					line = line.substring(1);
					// we should have something like "-5.320423,0.23423523,21.2352" (no quotes).
					String[] pieces = line.split(",");
					if (pieces.length != 3) {
						System.out.println("Expected 3 parameters on line " + debugLines + ". Got: " + line);
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						return false;
					}
					try {
						parsableX = Float.parseFloat(pieces[0]);
						parsableY = Float.parseFloat(pieces[1]);
						parsableZ = Float.parseFloat(pieces[2]);
					}
					catch (Throwable t) {
						System.out.println("Failed to parse parameters (x,y,z) on line " + debugLines + ". Got: " + line);
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						return false;
					}
					tmpB.add(new P3D(parsableX,parsableY,parsableZ));
				}
				else if (line.startsWith("c")) { // Now we read in colors.
					int parsableX = 0;
					int parsableY = 0;
					int parsableZ = 0;
					line = line.substring(1);
					// we should have something like "255,0,34" (no quotes).
					String[] pieces = line.split(",");
					if (pieces.length != 3 && pieces.length != 4) {
						System.out.println("Expected 3 parameters on line " + debugLines + ". Got: " + line);
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						return false;
					}
					try {
						parsableX = Integer.parseInt(pieces[0]);
						parsableY = Integer.parseInt(pieces[1]);
						parsableZ = Integer.parseInt(pieces[2]);
					}
					catch (Throwable t) {
						System.out.println("Failed to parse parameters (x,y,z) on line " + debugLines + ". Got: " + line);
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						return false;
					}
					tmpB.add(new C3D(parsableX,parsableY,parsableZ));
				}	
				else if (line.startsWith("dt")) { // detect the draw type.
					line = line.substring(2);
					foundTheDrawType = true;
					if (line.equals("points")) {
						meshType = DrawType.Points;
					}
					else if (line.equals("polygon")) {
						meshType = DrawType.Polygon;
					}
					else if (line.equals("triangle") || line.equals("triangles")) {
						meshType = DrawType.Triangle;
					}
					else if (line.equals("trianglelines")) {
						meshType = DrawType.TriangleLines;
					}
					else {
						foundTheDrawType = false;
						System.out.println("Unable to read draw type from line #"+ debugLines + ", or unsupported draw type. Defaulting to DrawType.Points. Got:" + line);
					}
				}
				else if (line.equals("\n") || line.equals("\n\r") || line.equals("\r") || line.equals("\r\n") || line.equals("")) {
					
				}
				else {
					System.out.println("Got something really weird: " + line + ", on line # " + debugLines + ". Did you mean to comment this out?");
				}
				debugLines++;
			}
			if (!foundTheDrawType)
				System.out.println("No draw type found in entire file. Setting DrawType.Points as default.");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long durQuery = System.currentTimeMillis() - queryStart;
		System.out.println("File loaded sucessfully:");
		stacks = new Operation3D[tmpB.size()];
		for (int i = 0; i < tmpB.size(); i++) {
			stacks[i] = tmpB.get(i);
			System.out.println(stacks[i]);
		}
		System.out.println("Took: " + durQuery + "ms");
		return true;
	}
	
	public boolean loadModel(String pathFromCurrentDir) {
		try {
			return loadModel(new File(Model.class.getResource(pathFromCurrentDir).toURI()));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Operation3D next() {
		if (stacks == null)
			throw new IllegalStateException("Cannot request next operation, until model has been sucessfully loaded.");
		return stacks[next++];
	}
	
	public boolean isCompletelyQueried() {
		return next + 1 < stacks.length - 1;
	}
	
	public void reset() {
		next = 0;
	}
}
