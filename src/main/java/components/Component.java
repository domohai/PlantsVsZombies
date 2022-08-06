package components;
import abc.GameObject;
import java.awt.Graphics2D;

public abstract class Component<T> {
	private static int ID_COUNTER = 0;
	private transient int uid = -1;
	public transient GameObject gameObject = null;
	
	public void start() {}
	public void update(double dt) {}
	public void draw(Graphics2D g2D) {}
	public abstract Component copy();
	
	public void generateId() {
		if (this.uid == -1) {
			this.uid = ID_COUNTER++;
		}
	}
	
	public int getUid() {
		return this.uid;
	}
	
}
