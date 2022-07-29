package abc;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class GameObject {
	private String name;
	private List<Component> components;
	public Transform transform;
	public int zIndex;
	
	public GameObject(String name, int zIndex) {
		this.name = name;
		this.zIndex = zIndex;
		this.components = new ArrayList<>();
		this.transform = new Transform();
	}
	
	public GameObject(String name, Transform transform, int zIndex) {
		this.name = name;
		this.zIndex = zIndex;
		this.components = new ArrayList<>();
		this.transform = transform;
	}
	
	public void start() {
		for (int i = 0; i < this.components.size(); i++) {
			this.components.get(i).start();
		}
	}
	
	public void update(double dt) {
		for (int i = 0; i < this.components.size(); i++) {
			this.components.get(i).update(dt);
		}
	}
	
	public void draw(Graphics2D g2D) {
		for (Component c : this.components) {
			c.draw(g2D);
		}
	}
	
	public void addComponent(Component newComponent) {
		this.components.add(newComponent);
		newComponent.gameObject = this;
	}
	
	public <T extends Component> T getComponent(Class<T> componentClass) {
		for (Component c : this.components) {
			if (componentClass.isAssignableFrom(c.getClass())) {
				try {
					return componentClass.cast(c);
				} catch (ClassCastException e) {
					e.printStackTrace();
					System.out.println("Error: Casting component!");
				}
			}
		}
		return null;
	}
	
	public <T extends Component> void removeComponent(Class<T> componentClass) {
		for (int i = 0; i < this.components.size(); i++) {
			Component c = this.components.get(i);
			if (componentClass.isAssignableFrom(c.getClass())) {
				components.remove(i);
				return;
			}
		}
	}
	
}
