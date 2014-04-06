public class Hitbox {
	private static HitAction defaultAction = new HitAction() {
		public void onHit(Drawable d0, Drawable d1, int indexd0, int indexd1) {
			if (d0 instanceof GamePlane || d1 instanceof GamePlane)
				return;
			if (d0 instanceof Player) {
				Player p = (Player) d0;
				P3D np = Hitbox.resolvePlayer(p.getHitbox(), d1.getHitbox(),indexd1);
				np.x += p.getScene().getPlayerX();
				np.z += p.getScene().getPlayerZ();
				p.getScene().setPlayerMovable(false);
				p.moveTowards(np);
			} else if (d1 instanceof Player) {
				Player p = (Player) d0;
				P3D np = Hitbox.resolvePlayer(p.getHitbox(), d1.getHitbox(),indexd0);
				np.x += p.getScene().getPlayerX();
				np.z += p.getScene().getPlayerZ();
				p.getScene().setPlayerMovable(false);
				p.moveTowards(np);
			}
		}
	};
	private P3D[] bounds = null;
	private HitAction action = defaultAction;
	private P3D location = new P3D(0, 0, 0);

	private boolean enabled = true;
	public static HitAction getDefaultHitAction() {
		return defaultAction;
	}
	
	public Hitbox() {

	}

	public Hitbox(P3D boundStart, P3D boundEnd) {
		bounds = new P3D[] { boundStart, boundEnd };
	}
	
	public Hitbox(P3D... multiple) {
		bounds = multiple;
	}
	
	public void rotate90deg() {
		for (int i = 0; i < bounds.length / 2; i++) {
			float swap = bounds[(i*2)+0].z;
			bounds[(i*2)+0].z = bounds[(i*2)+0].x;
			bounds[(i*2)+0].x = swap;
			
			swap = bounds[(i*2)+1].z;
			bounds[(i*2)+1].z = bounds[(i*2)+1].x;
			bounds[(i*2)+1].x = swap;
		}
	}
	
	public void rotate180deg() {
		rotate90deg();
		rotate90deg();
	}
	
	public void rotate270deg() {
		rotate90deg();
		rotate90deg();
		rotate90deg();
	}
	
	public void disable() {
		enabled = false;
	}
	
	public void enabled() {
		enabled = true;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setHitAction(HitAction action) {
		this.action = action;
	}

	public void setDrawable(Drawable d) {
		drawable = d;
	}

	public void updatePosition(P3D pos) {
		location = pos;
	}

	public static P3D getCenter(P3D boundStart, P3D boundEnd) {
		return new P3D((boundStart.x + boundEnd.x) * 0.5f,
				(boundStart.y + boundEnd.y) * 0.5f,
				(boundStart.z + boundEnd.z) * 0.5f);
	}

	public boolean isHitable() {
		if (bounds == null || bounds.length < 2 || bounds.length % 2 != 0)
			return false;
		return true;
	}

	public P3D getLocation() {
		return location;
	}

	public int numHitboxes() {
		return bounds.length / 2;
	}

	public P3D adjustBoundToPosition(int boundIndex) {
		return new P3D(location.x + bounds[boundIndex].x, location.y
				+ bounds[boundIndex].y, location.z + bounds[boundIndex].z);
		/*float w = 0;
		float h = 0;
		float d = 0;
		if (boundIndex % 2 == 0) {
			w = Math.abs(bounds[boundIndex].x) + Math.abs(bounds[boundIndex+1].x);
			h = Math.abs(bounds[boundIndex].y) + Math.abs(bounds[boundIndex+1].y);
			d = Math.abs(bounds[boundIndex].z) + Math.abs(bounds[boundIndex+1].z);
		}
		else  {
			w = Math.abs(bounds[boundIndex-1].x) + Math.abs(bounds[boundIndex].x);
			h = Math.abs(bounds[boundIndex-1].y) + Math.abs(bounds[boundIndex].y);
			d = Math.abs(bounds[boundIndex-1].z) + Math.abs(bounds[boundIndex].z);
		}
		//System.out.println(w+","+h+","+d);
		float x = location.x - (w/2) + bounds[boundIndex].x;
		float y = location.y - (h/2) + bounds[boundIndex].y;
		float z = location.z - (d/2) + bounds[boundIndex].z;
		//System.out.println(x+","+y+","+z);
		
		return new P3D(x,y,z);*/
	}

	public static P3D resolvePlayer(Hitbox player, Hitbox h1, int otherIndex) {
		if (!player.isHitable() || !h1.isHitable())
			return null;

		P3D ps = player.adjustBoundToPosition(0);
		P3D pe = player.adjustBoundToPosition(1);
		P3D pc = getCenter(ps, pe);

		P3D hs = h1.adjustBoundToPosition(otherIndex * 2);
		P3D he = h1.adjustBoundToPosition(otherIndex * 2 + 1);
		P3D hc = getCenter(hs, he);

		/*float heighest = -10000;
		if (hs.y > heighest)
			heighest = hs.y;
		if (he.y > heighest)
			heighest = he.y;
		if (player.getDrawable().getScene().getPlayerY() > heighest) {
			// place on top of the hitbox.
			//return new P3D(player.getLocation().x, heighest,
				//	player.getLocation().z);
			player.getDrawable().getScene().getGamePlane().stopFall();
			player.getDrawable().getScene().getGamePlane().setPlayerHeightOverride(heighest);
		}*/

		float alpha = (float) (Math.atan2(hc.z - pc.z, hc.x - pc.x) + Math.PI);
		float dist = hc.dist(pc) * 0.1f;
		return new P3D((float) (MathCalculator.cos(alpha) * dist),
				player.getLocation().y, (float) (MathCalculator.sin(alpha) * dist));
	}

	private Drawable drawable;

	public Drawable getDrawable() {
		return drawable;
	}

	public boolean hit(Hitbox d) {
		if (!d.isHitable() || !isHitable() || !isEnabled())
			return false;
		boolean enumerator = false;
		int indexthis = -1;
		int indexthat = -1;
		for (int i = 0; i < bounds.length/2; i++) {
			int si = i * 2;
			int ei = i * 2 + 1;
			int si2 = 0;
			int ei2 = 0;
			for (int j = 0; j < d.bounds.length/2; j++) {
				si2 = j * 2;
				ei2 = j * 2 + 1;
				if (d.adjustBoundToPosition(si2).x > adjustBoundToPosition(ei).x)
					continue;
				if (d.adjustBoundToPosition(si2).y > adjustBoundToPosition(ei).y)
					continue;
				if (d.adjustBoundToPosition(si2).z > adjustBoundToPosition(ei).z)
					continue;
				if (d.adjustBoundToPosition(ei2).x < adjustBoundToPosition(si).x)
					continue;
				if (d.adjustBoundToPosition(ei2).y < adjustBoundToPosition(si).y)
					continue;
				if (d.adjustBoundToPosition(ei2).z < adjustBoundToPosition(si).z)
					continue;
				indexthat = j;
				indexthis = i;
				enumerator = true;
				break;
			}
			if (indexthis != -1)
				break;
		}
		if (!enumerator)
			return false;
		if (action != null)
			action.onHit(d.getDrawable(), getDrawable(),indexthat,indexthis);
		return true;
	}
}
