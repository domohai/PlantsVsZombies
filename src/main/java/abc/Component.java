package abc;
import java.awt.Graphics2D;

public abstract class Component<T> {
	public GameObject gameObject = null;
	
	public void start() {}
	public void update(double dt) {}
	public void draw(Graphics2D g2D) {}
}