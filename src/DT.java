
public class DT extends Operation3D {
	private DrawType internalType;
	public DT() {
		id = 5;
		internalType = DrawType.Points;
	}
	
	public DT(int drawTypeId) {
		id = 5;
		internalType = DrawType.getFromInt(drawTypeId);
	}
	
	public DT(DrawType dt) {
		id = 5;
		internalType = dt;
	}
	
	public DrawType getDrawType() {
		return internalType;
	}
	
	public void setDrawType(DrawType dt) {
		internalType = dt;
	}
}
