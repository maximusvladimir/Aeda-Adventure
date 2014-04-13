import java.io.*;
import java.util.*;

public class $Loader /*extends SimpleGame*/ {
	static int unnamed = 0;
	public static Vector segments = new Vector();
	
	public static String readFile(String FileName) {
		//Loads a file from disk.
		String tempData = null;
		BufferedReader is;
		StringBuffer realData = new StringBuffer();
		boolean firstRead = true;
		try {
			//URLConnection urlcon = location.openConnection();
			InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(FileName);
			Reader reader = null;
			if (input == null) {
				try {
					reader = new FileReader(FileName);
				}
				catch (Throwable t) {
					System.out.println("throwing null");
					throw new IOException();
				}
			}
			else
				reader = new InputStreamReader(input);
			boolean firstline = true;
			is = new BufferedReader(reader);
			//int iterator = 0;
			while ((tempData = is.readLine()) != null) {
				//iterator++;
				//strip white space
				tempData = tempData.trim();
				//System.out.println("reading"+iterator);
				//strip comments
				if (tempData.indexOf("#") != -1) {
					tempData = tempData.substring(0,tempData.indexOf("#"));
				} else if (tempData.indexOf("//") != -1) {
					tempData = tempData.substring(0,tempData.indexOf("//"));
				}
				realData.append(tempData+" ");
				if (firstline) {
					realData.append("\n");
					firstline = false;
				}
			}
			is.close();
		} catch (IOException e) {
			//PErr("readFile:"+FileName+":"+e);
			System.out.println(e);
		}
		return(realData.toString());
	}
	
	static int tcount = 0;
	public static String getBraceSegment(String s) {
		//checks from the start of this string until it finds a {
		//once one is found it looks for any { until it hits a }.
		StringTokenizer st = new StringTokenizer(s,"{}",true);
		boolean noneFound = true;
		int count = 0;
		StringBuffer sb = new StringBuffer();
		while ((noneFound || count != 0) && st.hasMoreTokens()) {
			String token = st.nextToken();
			sb.append(token);
			if (token.equals("{")) {
				count++;
				noneFound = false;
			} else if (token.equals("}")) {
				count--;
			}
			
		}
		//System.out.println("tcount="+tcount+"\n========================\n"+sb.toString());
/*		tcount++;
		System.out.println(tcount);
		if (tcount > 100) {
			System.exit(0);
		}*/
		segments.addElement(sb.toString());
		return(sb.toString());
	}
	
	public static void parseHeader(String s,$XModelData mData) {
		//Header should be int;int;int;
		//major minor flags
		StringTokenizer st = new StringTokenizer(s,"{}",false);
		if (st.countTokens() == 2) {
			st.nextToken();//toss out Header
			String token = st.nextToken();
			st = new StringTokenizer(s,";",false);
			if (st.countTokens() == 3) {
				try {
					mData.hMajor = Integer.parseInt(st.nextToken().trim());
					mData.hMinor = Integer.parseInt(st.nextToken().trim());
					mData.hFlags = Long.parseLong(st.nextToken().trim());
				} catch (NumberFormatException nfe) {
				}
			}
		}
	}
	
	public static float[] parseTransformMatrixArray(String filedata) {
		StringTokenizer st = new StringTokenizer(filedata,";,",false);
		String tString = st.nextToken();
		if (st.countTokens() < 16) {
			float tFloat[] = {
			1f,0f,0f,0f,
			0f,1f,0f,0f,
			0f,0f,1f,0f,
			0f,0f,0f,1f
			};
			return(tFloat);
		}
		float tFloat[] = new float[16];
		for (int i = 0; i < tFloat.length; i++) {
			tString = st.nextToken();
			try {
				tFloat[i] = Float.parseFloat(tString.trim());
			} catch (NumberFormatException nfe) {
			}
		}
		return(tFloat);
	}
	
	public static void parseTransformMatrix(String filedata,$FrameModel fm) {
		String s = filedata.substring(filedata.indexOf("{")+1,filedata.indexOf(";;")+2);
		//filedata = filedata.substring(0,s.length());
		float[] tMatrix = parseTransformMatrixArray(s);
		fm.transformMatrix = tMatrix;
		
	}
	
	public static void parseMeshTextureCoords(String filedata,$MeshObject mo) {
		//Trim off MeshNormals keyword
		filedata = filedata.substring(filedata.indexOf("{"),filedata.lastIndexOf("}"));
		
		String s = filedata.substring(filedata.indexOf("{")+1,filedata.indexOf(";;")+2);
		filedata = filedata.substring(s.length()+1,filedata.length());
		
		String tString = s.substring(0,s.indexOf(";"));//verts
		s = s.substring(tString.length()+1,s.length());//trim off the length portion
		
		int verts = 0;
		try {
			verts = Integer.parseInt(tString.trim());
		} catch (NumberFormatException nfe) {
			verts = 0;
		}
		mo.textureCoords = new $Vertex2f[verts];
		StringTokenizer st = new StringTokenizer(s,";,",false);
		for (int i = 0; i < verts; i++) {
			if (st.countTokens() < 2) {
				break;
			}
			String ts1 = st.nextToken();
			String ts2 = st.nextToken();
			mo.textureCoords[i] = new $Vertex2f();
			try {
				mo.textureCoords[i].x = Float.parseFloat(ts1.trim());
				mo.textureCoords[i].y = Float.parseFloat(ts2.trim());
			} catch (NumberFormatException nfe) {
			}
		}
	}
	public static void parseMeshVertexColors(String filedata,$MeshObject mo) {
		//System.out.println(filedata);
		filedata = filedata.substring(filedata.indexOf("{"),filedata.lastIndexOf("}"));
		
		String s = filedata.substring(filedata.indexOf("{")+1,filedata.indexOf(";;")+2);
		filedata = filedata.substring(s.length()+1,filedata.length());
		
		String tString = s.substring(0,s.indexOf(";"));//verts
		s = s.substring(tString.length()+1,s.length());//trim off the length portion
		
		int verts = 0;
		try {
			verts = Integer.parseInt(tString.trim());
		} catch (NumberFormatException nfe) {
			verts = 0;
		}
		int index = 0;
		mo.vertexColors = new $RGBA[verts];
		StringTokenizer st = new StringTokenizer(s,";,",false);
		for (int i = 0; i < verts; i++) {
			if (st.countTokens() < 5) {
				break;
			}
			String ts1 = st.nextToken();
			String ts2 = st.nextToken();
			String ts3 = st.nextToken();
			String ts4 = st.nextToken();
			String ts5 = st.nextToken();
			mo.vertexColors[i] = new $RGBA();
			try {
				index = Integer.parseInt(ts1.trim());
				mo.vertexColors[index].rgba[0] = Float.parseFloat(ts2.trim());
				mo.vertexColors[index].rgba[1] = Float.parseFloat(ts3.trim());
				mo.vertexColors[index].rgba[2] = Float.parseFloat(ts4.trim());
				mo.vertexColors[index].rgba[3] = Float.parseFloat(ts5.trim());
			} catch (NumberFormatException nfe) {
			}
			if (i % 400 == 0) {
				System.out.println(((((float)i) / verts) * 100)+"%");
			}
		}
		
	}
	
	public static void parseMeshNormals(String filedata,$MeshObject mo) {
		//Trim off MeshNormals keyword
		filedata = filedata.substring(filedata.indexOf("{"),filedata.lastIndexOf("}"));
		
		String s = filedata.substring(filedata.indexOf("{")+1,filedata.indexOf(";;")+2);
		filedata = filedata.substring(s.length()+1,filedata.length());
		
		String tString = s.substring(0,s.indexOf(";"));//verts
		s = s.substring(tString.length()+1,s.length());//trim off the length portion
		int verts = 0;
		try {
			verts = Integer.parseInt(tString.trim());
		} catch (NumberFormatException nfe) {
			verts = 0;
		}
		mo.normals = new $Vertex3f[verts];
		
		StringTokenizer st = new StringTokenizer(s,",",false);
		for (int i = 0; i < verts && st.hasMoreTokens(); i++) {
			StringTokenizer st2 = new StringTokenizer(st.nextToken(),";",false);
			if (st2.countTokens() != 3) {
				continue;
			}
			String ts1 = st2.nextToken();
			String ts2 = st2.nextToken();
			String ts3 = st2.nextToken();
			mo.normals[i] = new $Vertex3f();
			try {
				mo.normals[i].x = Float.parseFloat(ts1.trim());
				mo.normals[i].y = Float.parseFloat(ts2.trim());
				mo.normals[i].z = Float.parseFloat(ts3.trim());
			} catch (NumberFormatException nfe) {
			}
		}
		//parse the faces
		s = filedata.substring(0,filedata.indexOf(";;")+2);
		filedata = filedata.substring(s.length(),filedata.length());
		tString = s.substring(0,s.indexOf(";"));//faces
		s = s.substring(tString.length()+1,s.length());//trim off the length portion
		
		verts = 0;
		try {
			verts = Integer.parseInt(tString.trim());
		} catch (NumberFormatException nfe) {
			verts = 0;
		}
		mo.normalsFaces = new $Face[verts];
		st = new StringTokenizer(s,",;\n",false);
		for (int i = 0; i < verts; i++) {
			int vertsPerFace = 0;
			
			String tverts = st.nextToken();
			try {
				vertsPerFace = Integer.parseInt(tverts.trim());
			} catch (NumberFormatException nfe) {
			}
			int[] faces = new int[vertsPerFace];
			for (int i2 = 0; i2 < vertsPerFace; i2++) {
				try {
					faces[i2] = Integer.parseInt(st.nextToken().trim());
				} catch (NumberFormatException nfe) {
				}
			}
			mo.normalsFaces[i] = new $Face();
			mo.normalsFaces[i].vertIndex = faces;
		}
		
	}
	
	public static void parseMaterial(String filedata,$MaterialObject ma) {
		//I will likely keep a global list of all materials so I can avoid
		//multiple copies of the same materials.
		StringTokenizer st = new StringTokenizer(filedata," ",false);
		st.nextToken();//toss out Mesh
		//meshname
		ma.materialName = st.nextToken();
		if (ma.materialName.startsWith("{")) {
			ma.materialName = "nonamemat"+unnamed;
			unnamed++;
		}
		filedata = filedata.substring(filedata.indexOf("{")+1,filedata.lastIndexOf("}")-1);
		st = new StringTokenizer(filedata,"; ",false);
		if (st.countTokens() >= 11) {
			try {
				ma.r = Float.parseFloat(st.nextToken().trim());
				ma.g = Float.parseFloat(st.nextToken().trim());
				ma.b = Float.parseFloat(st.nextToken().trim());
				ma.a = Float.parseFloat(st.nextToken().trim());
				
				ma.sr = Float.parseFloat(st.nextToken().trim());
				ma.sg = Float.parseFloat(st.nextToken().trim());
				ma.sb = Float.parseFloat(st.nextToken().trim());
				ma.er = Float.parseFloat(st.nextToken().trim());
				ma.eg = Float.parseFloat(st.nextToken().trim());
				ma.eb = Float.parseFloat(st.nextToken().trim());
			} catch (NumberFormatException nfe) {
			}
		}
		filedata = filedata.substring(filedata.lastIndexOf(";;")+2,filedata.length());
		//Should trim off all of the material info without harming the texture section
		//if it exists
		filedata = filedata.trim();
		if (filedata.startsWith("TextureFilename")) {
			ma.textureName = filedata.substring(filedata.indexOf("\"")+1,filedata.lastIndexOf("\""));
		}
		
	}
	
	public static void parseMeshMaterialList(String filedata,$MeshObject mo) {
		//number of materials;
		//material per face;
		//System.out.println(filedata);
		filedata = filedata.substring(filedata.indexOf("{")+1,filedata.lastIndexOf("}")-1);
		String tString = filedata.substring(0,filedata.indexOf(";"));
		filedata = filedata.substring(tString.length()+1,filedata.length());
		int mats = 0;
		try {
			mats = Integer.parseInt(tString.trim());
		} catch (NumberFormatException nfe) {
			System.out.println(nfe);
		}
		tString = filedata.substring(0,filedata.indexOf(";"));
		filedata = filedata.substring(tString.length()+1,filedata.length());
		int faces = 0;
		try {
			faces = Integer.parseInt(tString.trim());
		} catch (NumberFormatException nfe) {
		}
		mo.MaterialList = new $MaterialObject[mats];
		String s = filedata.substring(0,filedata.indexOf(";")+1);
		filedata = filedata.substring(s.length(),filedata.length());
		mo.materials = new int[faces];
		StringTokenizer st = new StringTokenizer(s,",",false);
		for (int i = 0; i < faces && st.hasMoreTokens(); i++) {
			try {
				mo.materials[i] = Integer.parseInt(st.nextToken().trim());
			} catch (NumberFormatException nfe) {
			}
		}
		//Handle keyword materials
		int mCount = 0;
		while (filedata.length() > 0) {
			s = getBraceSegment(filedata);
			//Trim the already been parsed portion of text
			filedata = filedata.substring(s.length(),filedata.length());
			s = s.trim();
			if (s.startsWith("Material")) {
				$MaterialObject ma = new $MaterialObject();
				parseMaterial(s,ma);
				mo.MaterialList[mCount] = ma;
				mCount++;
			} else if (s.startsWith("{")) {
				//inlined reference to a material.
				String tname = parseReferenceName(s);
				String ts = getSegmentByName("Material "+tname);
				if (ts != null) {
					$MaterialObject ma = new $MaterialObject();
					parseMaterial(ts,ma);
					mo.MaterialList[mCount] = ma;
					mCount++;
				}
			}
		}
		//mo.sortByMaterial();
	}
	
	public static String getSegmentByName(String name) {
		for (int i = 0; i < segments.size(); i++) {
			String s = (String)segments.elementAt(i);
			if (s.startsWith(name)) {
				return(s);
			}
		}
		return(null);
	}
	
	public static void parseAnimationKey(String filedata,$AnimationObject ao) {
		$AnimationKeyObject ako = new $AnimationKeyObject();
		//StringTokenizer st = new StringTokenizer(filedata," ",false);
		//st.nextToken();//toss out AnimationKey
		filedata = filedata.substring(filedata.indexOf("{"),filedata.lastIndexOf("}"));
		
		String s = filedata.substring(filedata.indexOf("{")+1,filedata.indexOf(";;;")+3);
		filedata = filedata.substring(s.length()+1,filedata.length());
		
		//format
		//int; keytype
		//int; number of keys
		//int; time
		//int; number of floats; float,float,float;;,
		
		String tString = s.substring(0,s.indexOf(";"));//keytype
		s = s.substring(tString.length()+1,s.length());//trim off the length portion
		int keytype = 0;
		try {
			keytype = Integer.parseInt(tString.trim());
		} catch (NumberFormatException nfe) {
			keytype = 0;
		}
		ako.keytype = keytype;
		tString = s.substring(0,s.indexOf(";"));//keys
		s = s.substring(tString.length()+1,s.length());//trim off the length portion
		int keyframes = 0;
		try {
			keyframes = Integer.parseInt(tString.trim());
		} catch (NumberFormatException nfe) {
			keyframes = 0;
		}
		ako.TimedFloatKeys = new $TimedFloatKeyObject[keyframes];
		StringTokenizer st = new StringTokenizer(s,";,",false);
		for (int i = 0; i < keyframes && st.hasMoreTokens(); i++) {
			//StringTokenizer st2 = new StringTokenizer(st.nextToken(),";,",false);
			if (st.countTokens() < 2) {
				continue;
			}
			String ts1 = st.nextToken();
			String ts2 = st.nextToken();
			ako.TimedFloatKeys[i] = new $TimedFloatKeyObject();
			try {
				ako.TimedFloatKeys[i].time = Integer.parseInt(ts1.trim());
				int keys = Integer.parseInt(ts2.trim());
				int counter = 0;
				ako.TimedFloatKeys[i].keys = new float[keys];
/*				if (keys > st.countTokens()) {
					continue;
				}*/
				while (counter < keys) {
					ako.TimedFloatKeys[i].keys[counter] = Float.parseFloat(st.nextToken().trim());
					counter++;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("nfe:"+nfe);
			}
		}
		ao.AnimationKeyList.addElement(ako);
	}
	
	public static void parseAnimation(String filedata,$AnimationSetObject aso,$XModelData mdata) {
		$AnimationObject ao = new $AnimationObject();
		StringTokenizer st = new StringTokenizer(filedata," ",false);
		st.nextToken();//toss out Animation
		//animation name
		ao.name = st.nextToken();
		if (ao.name.startsWith("{")) {
			ao.name = "unanimation"+unnamed;
			unnamed++;
		}
		filedata = filedata.substring(filedata.indexOf("{")+1,filedata.lastIndexOf("}"));
		
		while (filedata.length() > 0) {
			String s = getBraceSegment(filedata);
			//Trim the already been parsed portion of text
			filedata = filedata.substring(s.length(),filedata.length());
			s = s.trim();
			if (s.startsWith("AnimationKey")) {
				//Animation
				parseAnimationKey(s,ao);
			} else if (s.startsWith("{")) {
				//inlined reference to a frame.
				String tname = parseReferenceName(s);
				ao.frame = mdata.getFrameByName(tname);
			}
		}
		aso.AnimationList.addElement(ao);
	}
	
	public static void parseAnimationSet(String filedata,$XModelData mdata) {
		$AnimationSetObject aso = new $AnimationSetObject();
		StringTokenizer st = new StringTokenizer(filedata," ",false);
		st.nextToken();//toss out AnimationSet
		//animation name
		aso.name = st.nextToken();
		if (aso.name.startsWith("{")) {
			aso.name = "nonameanimationset"+unnamed;
			unnamed++;
		}
		filedata = filedata.substring(filedata.indexOf("{")+1,filedata.lastIndexOf("}"));
		//Typical info should look like...
		//AnimationSet name {
		// {reftoframe}
		// Animation aname {
		//  AnimationKey {} AnimationKey {} ...
		// }
		// Animation aname {
		//  AnimationKey {} AnimationKey {} ...
		// }
		//}
		while (filedata.length() > 0) {
			String s = getBraceSegment(filedata);
			//Trim the already been parsed portion of text
			filedata = filedata.substring(s.length(),filedata.length());
			//System.out.println(filedata);
			s = s.trim();
			if (s.startsWith("Animation")) {
				//Animation
				parseAnimation(s,aso,mdata);
			}
		}
		mdata.AnimationSetList.addElement(aso);
	}
	
	public static String parseReferenceName(String s) {
		if (s.length() <= 2) {
			return("");
		}
		s = s.trim();
		//This should strip the { and } from the ends.
		return(s.substring(1,s.length()-1).trim());
	}
	
	public static void parseXSkinMeshHeader(String filedata,$MeshObject mo) {
		//System.out.println(filedata);
		String s = filedata.substring(filedata.indexOf("{")+1,filedata.lastIndexOf("}"));
		//System.out.println(filedata);
		//String s = filedata.substring(filedata.indexOf("{")+1,filedata.indexOf("}")+1);
		//filedata = filedata.substring(s.length()+1,filedata.length());
		$XSkinMeshHeaderObject smh = new $XSkinMeshHeaderObject();
		StringTokenizer st = new StringTokenizer(s,";",false);
		if (st.countTokens() >= 3) {
			try {
				smh.maxSkinWeightsPerVertex = Integer.parseInt(st.nextToken().trim());
				smh.maxSkinWeightsPerFace = Integer.parseInt(st.nextToken().trim());
				smh.bones = Integer.parseInt(st.nextToken().trim());
				mo.skinMeshHeader = smh;
			} catch (NumberFormatException nfe) {
			}
		}
	}
	public static void parseVertexDuplicationIndices(String filedata,$MeshObject mo) {
		$VertexDuplicationIndicesObject dio = new $VertexDuplicationIndicesObject();
		String s = filedata.substring(filedata.indexOf("{")+1,filedata.lastIndexOf("}"));
		
		//String s = filedata;
		//System.out.println(s);
		//String s = filedata.substring(filedata.indexOf("{")+1,filedata.indexOf("}")+1);
		//filedata = filedata.substring(s.length()+1,filedata.length());
		StringTokenizer st = new StringTokenizer(s,";,",false);
		if (st.countTokens() < 2) {
			return;//not enough info
		}
		int originalverts = 0;
		int dupes = 0;
		try {
			dupes = Integer.parseInt(st.nextToken().trim());
			originalverts = Integer.parseInt(st.nextToken().trim());
		} catch (NumberFormatException nfe) {
		}
		//System.out.println("dupes="+dupes+" verts="+originalverts);
		dio.indices = new int[dupes];
		dio.originalVertices = originalverts;
		for (int i = 0; i < dupes && st.hasMoreTokens(); i++) {
			try {
				dio.indices[i] = Integer.parseInt(st.nextToken().trim());
			} catch (NumberFormatException nfe) {
			}
		}
		mo.duplicationIndices = dio;
	}
	
	public static void parseSkinWeights(String filedata,$MeshObject mo) {
		$SkinWeightsObject swo = new $SkinWeightsObject();
		//StringTokenizer st = new StringTokenizer(filedata," ",false);
		//st.nextToken();//toss out SkinWeights
		
		filedata = filedata.substring(filedata.indexOf("\"")+1,filedata.length());
		swo.name = filedata.substring(0,filedata.indexOf("\""));
		//System.out.println("meshname:"+mo.name);
		filedata = filedata.substring(filedata.indexOf("\"")+1,filedata.lastIndexOf("}"));
		
		String s = filedata;
		//System.out.println(s);
		//String s = filedata.substring(filedata.indexOf("{")+1,filedata.indexOf(";")+1);
		//filedata = filedata.substring(s.length()+1,filedata.length());
		
		//String tString = s.substring(0,s.indexOf(";"));//verts
		//s = s.substring(tString.length()+1,s.length());//trim off the length portion
		StringTokenizer st = new StringTokenizer(s,",;",false);
		String tString = st.nextToken();
		int weights = 0;
		try {
			weights = Integer.parseInt(tString.trim());
		} catch (NumberFormatException nfe) {
			weights = 0;
		}
		swo.vertexIndices = new int[weights];
		swo.weights = new float[weights];
		
		//s = filedata.substring(0,filedata.indexOf(";")+1);
		//filedata = filedata.substring(s.length()+1,filedata.length());
		for (int i = 0; i < weights && st.hasMoreTokens(); i++) {
			try {
				swo.vertexIndices[i] = Integer.parseInt(st.nextToken().trim());
			} catch (NumberFormatException nfe) {
			}
		}
		//parse the weights
		//s = filedata.substring(0,filedata.indexOf(";")+1);
		//filedata = filedata.substring(s.length(),filedata.length());
		
		//st = new StringTokenizer(s,",",false);
		for (int i = 0; i < weights && st.hasMoreTokens(); i++) {
			try {
				swo.weights[i] = Float.parseFloat(st.nextToken().trim());
			} catch (NumberFormatException nfe) {
			}
		}
		swo.matrix4x4 = parseTransformMatrixArray(filedata);
		//System.out.println(swo);
		mo.SkinWeightsList.addElement(swo);
	}
	
	public static void parseMesh(String filedata,$FrameModel fm) {
		$MeshObject mo = new $MeshObject();
		StringTokenizer st = new StringTokenizer(filedata," ",false);
		st.nextToken();//toss out Mesh
		//meshname
		mo.name = st.nextToken();
		if (mo.name.startsWith("{")) {
			mo.name = "unmesh"+unnamed;
			unnamed++;
		}
		//System.out.println("meshname:"+mo.name);
		filedata = filedata.substring(filedata.indexOf("{"),filedata.lastIndexOf("}"));
		
		String s = filedata.substring(filedata.indexOf("{")+1,filedata.indexOf(";;")+2);
		filedata = filedata.substring(s.length()+1,filedata.length());
		
		String tString = s.substring(0,s.indexOf(";"));//verts
		s = s.substring(tString.length()+1,s.length());//trim off the length portion
		int verts = 0;
		try {
			verts = Integer.parseInt(tString.trim());
		} catch (NumberFormatException nfe) {
			verts = 0;
		}
		mo.vertices = new $Vertex3f[verts];
		
		st = new StringTokenizer(s,",",false);
		for (int i = 0; i < verts && st.hasMoreTokens(); i++) {
			StringTokenizer st2 = new StringTokenizer(st.nextToken(),";",false);
			if (st2.countTokens() != 3) {
				continue;
			}
			String ts1 = st2.nextToken();
			String ts2 = st2.nextToken();
			String ts3 = st2.nextToken();
			mo.vertices[i] = new $Vertex3f();
			try {
				mo.vertices[i].x = Float.parseFloat(ts1.trim());
				mo.vertices[i].y = Float.parseFloat(ts2.trim());
				mo.vertices[i].z = Float.parseFloat(ts3.trim());
			} catch (NumberFormatException nfe) {
			}
		}
		//parse the faces
		s = filedata.substring(0,filedata.indexOf(";;")+2);
		filedata = filedata.substring(s.length(),filedata.length());
		tString = s.substring(0,s.indexOf(";"));//faces
		s = s.substring(tString.length()+1,s.length());//trim off the length portion
		
		verts = 0;
		try {
			verts = Integer.parseInt(tString.trim());
		} catch (NumberFormatException nfe) {
			verts = 0;
		}
		mo.faces = new $Face[verts];
		st = new StringTokenizer(s,",;\n",false);
		for (int i = 0; i < verts; i++) {
			int vertsPerFace = 0;
			
			String tverts = st.nextToken();
			try {
				vertsPerFace = Integer.parseInt(tverts.trim());
			} catch (NumberFormatException nfe) {
			}
			int[] faces = new int[vertsPerFace];
			for (int i2 = 0; i2 < vertsPerFace; i2++) {
				try {
					faces[i2] = Integer.parseInt(st.nextToken().trim());
				} catch (NumberFormatException nfe) {
				}
			}
			mo.faces[i] = new $Face();
			mo.faces[i].vertIndex = faces;
		}
		
		//Time to look for other mesh data like normals and texturecoords
		while (filedata.length() > 0) {
			s = getBraceSegment(filedata);
			//Trim the already been parsed portion of text
			filedata = filedata.substring(s.length(),filedata.length());
			s = s.trim();
			if (s.startsWith("MeshNormals")) {
				//parseMeshNormals(s,mo);
			} else if (s.startsWith("MeshTextureCoords")) {
				//parseMeshTextureCoords(s,mo);
			} else if (s.startsWith("MeshVertexColors")) {
				parseMeshVertexColors(s,mo);
			} else if (s.startsWith("MeshMaterialList")) {
				//parseMeshMaterialList(s,mo);
			} else if (s.startsWith("XSkinMeshHeader")) {
				//parseXSkinMeshHeader(s,mo);
			} else if (s.startsWith("VertexDuplicationIndices")) {
				//parseVertexDuplicationIndices(s,mo);
			} else if (s.startsWith("SkinWeights")) {
				//parseSkinWeights(s,mo);
			} else if (s.startsWith("MeshFaceWraps")) {
				//Unhandled for now
			}
		}
		fm.MeshObjectList.addElement(mo);
	}
	
	public static void parseFrame(String filedata,$XModelData mData) {
		//This is a top level frame
		$FrameModel fm = new $FrameModel();
		handleFrame(filedata,fm);
		mData.FrameModelList.addElement(fm);
	}
	public static void parseFrame(String filedata,$FrameModel mData) {
		//This is called when a frame is within a frame
		$FrameModel fm = new $FrameModel();
		handleFrame(filedata,fm);
		mData.FrameModelList.addElement(fm);
	}
	
	public static void handleFrame(String filedata,$FrameModel fm) {
		//System.out.println(filedata+"\n----------------------------------\n");
		//Can only hit FrameTransformMatrix or Mesh or Frame
		//Need to handle animation here?
		StringTokenizer st = new StringTokenizer(filedata," ",false);
		st.nextToken();//toss out Frame
		fm.name = st.nextToken();
		if (fm.name.startsWith("{")) {
			fm.name = "nonameframe"+unnamed;
			unnamed++;
		}
		//System.out.println("name: "+fm.name+"\n");
		//Remove Frame framename { and the final }
		filedata = filedata.substring(filedata.indexOf("{")+1,filedata.lastIndexOf("}"));
		while (filedata.length() > 0) {
			String s = getBraceSegment(filedata);
			//Remove the used bracesegment from filedata.
			filedata = filedata.substring(s.length(),filedata.length());
			s = s.trim();
			if (s.startsWith("FrameTransformMatrix")) {
				//Requires a current Frame
				parseTransformMatrix(s,fm);
			} else if (s.startsWith("Mesh")) {
				//Requires a current Frame
				parseMesh(s,fm);
			} else if (s.startsWith("Frame")) {
				//It might be better to use startsWith("Frame ") with a space, however
				//the fact that FrameTransform... is already check it's prob safe.
				parseFrame(s,fm);
			} else if (s.startsWith("{")) {
				//inlined reference to a mesh.
				String tname = parseReferenceName(s);
				String ts = getSegmentByName("Mesh "+tname);
				if (ts != null) {
					parseMesh(ts,fm);
				}
			}
		}
	}
	
	public static void parse(String filedata,$XModelData mData) {
		//Do a startswith to check what template to load
		if (filedata.startsWith("xof")) {
			int indof = filedata.indexOf("\n");
			if (indof == -1) {
				//This file is just a header, no data
				return;
			}
			//trim this header info from the file
			filedata = filedata.substring(indof,filedata.length());
		}
		while (filedata.length() > 0) {
			String s = getBraceSegment(filedata);
			//Trim the already been parsed portion of text
			filedata = filedata.substring(s.length(),filedata.length());
			s = s.trim();
			if (s.startsWith("Header")) {
				//If a section has a sub-heading in it, will I know in advance to look for it
				//at a specific point? I should by template know that.
				parseHeader(s,mData);
			} else if (s.startsWith("Frame")) {
				parseFrame(s,mData);
			} else if (s.startsWith("AnimationSet")) {
				parseAnimationSet(s,mData);
			}
		}
		segments.clear();
	}
	
	public static $XModelData LoadModel(String filename) {
		String filedata = readFile(filename);
		//snatch the header info? First line should be it.
		$XModelData mData = new $XModelData();
		parse(filedata,mData);
		return(mData);
	}
	
	/*protected void simpleInitGame() {
		// TriMesh is what most of what is drawn in jME actually is
		XModelData xData = LoadModel("fighter0.x");
		TriMesh m=new TriMesh("My Mesh");
		// Vertex positions for the mesh
		int vertlength = 0;//The number of verts for all meshes combined
		int meshlength = 0;//the number of faces/normals for all meshes
		int texlength = 0;//the number of tex coords for all meshes.
		int vertstart = 0;
		int meshstart = 0;
		int texstart = 0;
		for (int imodel = 0; imodel < xData.FrameModelList.size(); imodel++) {
			FrameModel fmodel = (FrameModel)xData.FrameModelList.elementAt(imodel);
			for (int i = 0; i < fmodel.MeshObjectList.size(); i++) {
				MeshObject tmesh = (MeshObject)fmodel.MeshObjectList.elementAt(i);
				vertlength += tmesh.vertices.length;
				meshlength += tmesh.faces.length;
				texlength += tmesh.textureCoords.length;
			}
		}
			System.out.println(vertlength+" "+meshlength+" "+texlength);
			Vector3f[] vertexes= new Vector3f[vertlength];
			Vector3f[] normals=new Vector3f[vertlength];
			ColorRGBA[] colors=null;
			Vector2f[] texCoords=new Vector2f[texlength];
			int[] indexes= new int[meshlength*3];
			
		for (int imodel = 0; imodel < xData.FrameModelList.size(); imodel++) {
			FrameModel fmodel = (FrameModel)xData.FrameModelList.elementAt(imodel);
			for (int imesh = 0; imesh < fmodel.MeshObjectList.size(); imesh++) {
				System.out.println("imesh="+imesh);
				MeshObject tmesh = (MeshObject)fmodel.MeshObjectList.elementAt(imesh);
				
				for (int i = 0; i < tmesh.vertices.length; i++) {
					vertexes[vertstart+i] = new Vector3f(tmesh.vertices[i].x,tmesh.vertices[i].y,tmesh.vertices[i].z);
				}
				// Normal directions for each vertex position
				for (int i = 0; i < tmesh.normals.length; i++) {
					normals[meshstart+i] = new Vector3f(tmesh.normals[i].x,tmesh.normals[i].y,tmesh.normals[i].z);
				}
				// Color for each vertex position
				// Texture Coordinates for each position		
				for (int i = 0; i < tmesh.textureCoords.length; i++) {
					texCoords[texstart+i] = new Vector2f(tmesh.textureCoords[i].x,tmesh.textureCoords[i].y);
				}
				// The indexes of Vertex/Normal/Color/TexCoord sets. Every 3
				// makes a triangle.
				for (int i = 0; i < tmesh.faces.length; i++) {
					indexes[meshstart+(i*3)] = tmesh.faces[i].vertIndex[0];
					indexes[meshstart+(i*3)+1] = tmesh.faces[i].vertIndex[1];
					indexes[meshstart+(i*3)+2] = tmesh.faces[i].vertIndex[2];
				}
				// Feed the information to the TriMesh
				vertstart += vertlength;
				meshstart += meshlength;
				texstart += texlength;
			}
		}
		m.reconstruct(vertexes,normals,colors,texCoords,indexes);
		// Create a bounds
		m.setModelBound(new BoundingBox());
		m.updateModelBound();
		// Attach the mesh to my scene graph
		rootNode.attachChild(m);
  	Texture regTexture;
  	TextureState ts;
    ts = display.getRenderer().createTextureState();
    ts.setEnabled(true);
    
    regTexture =
        TextureManager.loadTexture(
        Loader.class.getClassLoader().getResource(
        "Lightning_BW.tga"),
        Texture.MM_LINEAR_LINEAR,
        Texture.FM_LINEAR);
    regTexture.setWrap(Texture.WM_WRAP_S_WRAP_T);
    
    ts.setTexture(regTexture);
    rootNode.setRenderState(ts);
		// Let us see the per vertex colors
		//lightState.setEnabled(false);
	}*/
}