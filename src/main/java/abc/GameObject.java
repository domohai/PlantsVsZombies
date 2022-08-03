package abc;
import components.Component;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

enum Type {
	ZOMBIE, PLANT, OTHER
}

public class GameObject {
	private String name;
	private List<Component> components;
	public Transform transform;
	private int zIndex;
	public int line;
	private Type objectType;
	
	public GameObject(String name, int zIndex) {
		this.name = name;
		this.zIndex = zIndex;
		this.line = 0;
		this.objectType = Type.OTHER;
		this.components = new ArrayList<>();
		this.transform = new Transform();
	}
	
	public GameObject(String name, Transform transform, int zIndex, Type type,  int line) {
		this.name = name;
		this.zIndex = zIndex;
		this.objectType = type;
		this.line = line;
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
	
	public GameObject copy() {
		Transform transformCopy = this.transform.copy();
		GameObject copyGameObject = new GameObject("Copy GameObject", transformCopy, this.zIndex, this.objectType, this.line);
		for (Component c : this.components) {
			Component copy = c.copy();
			if (copy != null) {
				copyGameObject.addComponent(copy);
			}
		}
		return copyGameObject;
	}
	
	public int getzIndex() {
		return this.zIndex;
	}
	
	public Type getObjectType() {
		return this.objectType;
	}
	
}
