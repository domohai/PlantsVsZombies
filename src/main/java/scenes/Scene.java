package scenes;
import abc.GameObject;
import abc.Renderer;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Scene {
	private String name;
	protected boolean isRunning = false;
	protected List<GameObject> gameObjects, objectsToRemove = null;
	protected Map<Integer, List<GameObject>> plants = null;
	protected Map<Integer, List<GameObject>> zombies = null;
	protected Map<Integer, List<GameObject>> bullets = null;
	protected Renderer renderer;
	protected boolean dataLoaded = false;
	
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
		this.isRunning = true;
	}
	
	public void addGameObject(GameObject newGameObject) {
		this.gameObjects.add(newGameObject);
		this.renderer.submit(newGameObject);
		if (this.isRunning) newGameObject.start();
	}
	
	public void remove(GameObject object) {}
	
	public abstract void update(double dt);
	public abstract void draw(Graphics2D g2D);
	
	public void load_Resources() {}
	
	public void load() {}
	public void save() {}
	
	public void addZombie(GameObject zombie) {}
	public void addPlant(GameObject plant) {}
	public void addBullet(GameObject bullet) {}
}
