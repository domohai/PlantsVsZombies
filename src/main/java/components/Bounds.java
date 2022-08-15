package components;

import abc.GameObject;
import util.Const;
import util.Vector2D;

public class Bounds extends Component {
	public float width, height;
	public float halfW, halfH;
	public Vector2D center;
	public static float dx = 0.0f;
	public static float combineHalfW = 0.0f;
	
	public Bounds() {
		this.width = 0;
		this.height = 0;
		this.halfH = 0;
		this.halfW = 0;
		this.center = new Vector2D();
	}
	
	public Bounds(float width, float height) {
		this.width = width;
		this.height = height;
		this.halfW = width / 2.0f;
		this.halfH = height / 2.0f;
		this.center = new Vector2D();
	}
	
	public void setValue(float width, float height) {
		this.width = width;
		this.height = height;
		this.halfW = width / 2.0f;
		this.halfH = height / 2.0f;
	}
	
	@Override
	public void start() {
		this.calculateCenter();
	}
	
	public void calculateCenter() {
		this.center.x = this.gameObject.transform.position.x + this.halfW;
		this.center.y = this.gameObject.transform.position.y + this.halfH;
	}
	
	public static boolean checkCollision(Bounds b1, Bounds b2) {
		if (b1 != null && b2 != null) {
			b1.calculateCenter();
			b2.calculateCenter();
			Bounds.dx = b2.center.x - b1.center.x;
			Bounds.combineHalfW = b1.halfW + b2.halfW - 15;
			if (Math.abs(dx) < combineHalfW) {
				return true;
			}
		}
		return false;
	}
	
	public static void resolvePlantCollision(GameObject plant, GameObject zombie) {
		zombie.getComponent(Zombie.class).isAttacking = true;
		plant.getComponent(Rigidbody.class).hit = true;
		plant.getComponent(Rigidbody.class).setDamage(Const.ZOMBIE_DAMAGE);
		
	}
	
	public static void resolveBulletCollision(GameObject bullet, GameObject zombie) {
		zombie.getComponent(Rigidbody.class).hit = true;
		zombie.getComponent(Rigidbody.class).setDamage(Const.PLANT_DAMAGE);
		bullet.getComponent(Bullet.class).hitZombie = true;
		//System.out.println(zombie.getComponent(Rigidbody.class).HP);
	}
	
	@Override
	public Component copy() {
		Bounds copy = new Bounds();
		copy.setValue(this.width, this.height);
		return copy;
	}
}
