
public abstract class Character extends Drawable {
	private float health = 1;
	private boolean persuingPlayer = false;
	public Character(Scene<Drawable> scene, Hitbox hitbox) {
		super(scene, hitbox);
	}
	
	private float destX = 0.0f;
	private float destZ = 0.0f;
	private float startX = 0.0f;
	private float startZ = 0.0f;
	private float destDist = 0.0f;
	private float moveSpeed = 0.004f;
	private float destAlt = 0.0f;
	protected float moveAmount = 0.0f;
	
	public boolean isMovingTowards() {
		return movingTowards;
	}
	
	public void cancelMovement() {
		movingTowards = false;
	}
	
	public void setMoveSpeed(float val) {
		moveSpeed = val;
	}
	
	public float getMoveSpeed() {
		return moveSpeed;
	}
	
	public void moveTowards(float x, float z) {
		moveTowards(new P3D(x,0,z));
	}
	
	public void moveTowards(P3D p) {
		if (movingTowards)
			return;
		firedArrival = false;
		destX = p.x;
		destZ = p.z;
		p.y = 0;
		startX = getInstanceLoc().x;
		startZ = getInstanceLoc().z;
		//System.out.println("At:" + destX + "," + destZ + " going towards:" + startX + "," + startZ);
		destDist = p.dist(new P3D(getInstanceLoc().x,0,getInstanceLoc().z));
		destAlt = 0;
		movingTowards = true;
	}
	
	public void bounceCollision(Drawable d) {
		// TODO filler
	}
	
	public void setPersuingPlayer(boolean val) {
		persuingPlayer = val;
	}
	
	public boolean isPersuingPlayer() {
		return persuingPlayer;
	}
	
	public float getHealth() {
		return health;
	}
	
	public void setHealth(float val) {
		health = val;
	}
	
	public void addHealth(float val) {
		health += val;
	}
	
	public abstract void uponArrival();
	
	protected boolean movingTowards = false;

	private boolean firedArrival = false;
	
	public abstract void draw(int darkness);

	public void tick() {
		if (movingTowards) {
			 float lx = startX - destX;
			 float lz = startZ - destZ;
			 float l = (float)(Math.sqrt(lx * lx + lz * lz));
			 float ux = lx / l;
			 float uz = lz / l;
			 destAlt += getMoveSpeed();
			// System.out.println(ux+","+uz+","+destAlt+","+startX);
			 //System.out.println(destAlt + "," + destDist);
			 float y = getInstanceLoc().y;
			 if (!(this instanceof Player)) {
				float yd = getInstanceLoc().y - getScene().getGamePlane().getPlayerLocation(startX,startZ);
				y = getScene().getGamePlane().getPlayerLocation(startX - ux * destAlt, startZ - uz * destAlt) + yd;
			 }
			 setInstanceLoc(startX - ux * destAlt, y, startZ - uz * destAlt);
			 if (destAlt >= destDist) {
				 movingTowards = false;
				 destX = 0;
				 destZ = 0;
				 if (!firedArrival) {
					 firedArrival = true;
					 uponArrival();
				 }
			 }
		}
	}
	

	public abstract PointTesselator getTesselator();

	public void kill() {
		
	}
}