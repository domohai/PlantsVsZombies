package components;
import abc.GameObject;
import java.awt.Graphics2D;

public abstract class Component {
	public transient GameObject gameObject = null;
	
	public void start() {}
	public void update(double dt) {}
	public void draw(Graphics2D g2D) {}
	public abstract Component copy();
	
	public void destroy() {}
}
