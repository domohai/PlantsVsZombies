package abc;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
	private String name;
	protected boolean isRunning = false;
	protected List<GameObject> gameObjects;
	protected Renderer renderer;
	
	public Scene(String name) {
		this.name = name;
		this.gameObjects = new ArrayList<>();
		this.renderer = new Renderer();
	}
	
	public void init() {}
	
	public void start() {
		for (GameObject g : this.gameObjects) {
			g.start();
		}
		isRunning = true;
	}
	
	public void addGameObject(GameObject newGameObject) {
		gameObjects.add(newGameObject);
		this.renderer.submit(newGameObject);
		if (isRunning) newGameObject.start();
	}
	
	public abstract void update(double dt);
	public abstract void draw(Graphics2D g2D);
}
