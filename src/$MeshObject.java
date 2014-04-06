import java.util.Vector;

public class $MeshObject {
	public String name;
	//The actual vertice points
	public $Vertex3f[] vertices = new $Vertex3f[0];
	//Array of vertice indexes used.
	public $Face[] faces = new $Face[0];
	//Texture coords for each face
	public $Vertex2f[] textureCoords = new $Vertex2f[0];
	//Normals per vertice
	public $Vertex3f[] normals = new $Vertex3f[0];
	//Faces for normals
	public $Face[] normalsFaces = new $Face[0];
	//Material for each face
	public int[] materials = new int[0];
	
	//per vertex colors
	public $RGBA[] vertexColors = new $RGBA[0];
	
	public $XSkinMeshHeaderObject skinMeshHeader = null;//If != null...
	public $VertexDuplicationIndicesObject duplicationIndices = null;
	public Vector SkinWeightsList = new Vector();
	
	public $MaterialObject[] MaterialList = new $MaterialObject[0];
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\nMesh name: "+name+"\n");
		sb.append("vertices:\n");
		for (int i = 0; i < vertices.length; i++) {
			sb.append(vertices[i].toString()+"\n");
		}
		sb.append("faces:\n");
		for (int i = 0; i < faces.length; i++) {
			sb.append(faces[i].toString()+"\n");
		}
		sb.append("textureCoords:\n");
		for (int i = 0; i < textureCoords.length; i++) {
			sb.append(textureCoords[i].toString()+"\n");
		}
		sb.append("normals:\n");
		for (int i = 0; i < normals.length; i++) {
			sb.append(normals[i].toString()+"\n");
		}
		sb.append("normalsFaces:\n");
		for (int i = 0; i < normalsFaces.length; i++) {
			sb.append(normalsFaces[i].toString()+"\n");
		}
		sb.append("vertexColors:\n");
		for (int i = 0; i < vertexColors.length; i++) {
			sb.append(vertexColors[i].toString()+"\n");
		}
		sb.append("MaterialList:\n");
		for (int i = 0; i < MaterialList.length; i++) {
			//System.out.println("Matlength="+MaterialList.length+" "+i);
			sb.append(MaterialList[i].toString()+"\n");
		}
		sb.append("\n______Mesh_"+name+"_______\n");
		sb.append("\nskinMeshHeader "+skinMeshHeader+"\n");
		sb.append("\nVertexDuplicates "+duplicationIndices+"\n");
		sb.append("\nSkinWeightsList "+SkinWeightsList+"\n");
		return(sb.toString());
	}
	
	public void sortByMaterial() {
		//We need to re-arrange the materials from 0 - ? or ? - 0, doesn't matter
		//every time we move a material, we need to move a face and normal face
		boolean unsorted = true;
		
		int matSwap;
		$Face faceSwap;//Can use the same face to swap normal and face
		
		long minValue;
		long maxValue;
		int minLocation = -1;
		//int maxLocation = -1;
		
		int minIndex = 0;
		int maxIndex = materials.length-1;
		minValue = materials[0];
		
		long tempValue;
		while(unsorted) {
			for (int i = minIndex; i <= maxIndex; i++) {
				tempValue = materials[i];
				if (tempValue <= minValue) {
					minValue = tempValue;
					minLocation = i;
				}
			}
			if (minIndex != minLocation) {
				matSwap = materials[minIndex];
				materials[minIndex] = materials[minLocation];
				materials[minLocation] = matSwap;
				
				faceSwap = faces[minIndex];
				faces[minIndex] = faces[minLocation];
				faces[minLocation] = faceSwap;
				
				faceSwap = normalsFaces[minIndex];
				normalsFaces[minIndex] = normalsFaces[minLocation];
				normalsFaces[minLocation] = faceSwap;
			}
			
			minValue = materials[materials.length-1];
			minIndex++;
			if (maxIndex - minIndex < 1) {
				unsorted = false;
			}
			
		}
	}
	
}