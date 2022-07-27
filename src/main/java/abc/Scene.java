package abc;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
	private boolean isRunning = false;
	protected List<GameObject> gameObjects = new ArrayList<>();
	
	public Scene() {}
	public void init() {}
	
	public void start() {
		for (GameObject g : this.gameObjects) {
			g.start();
		}
		isRunning = true;
	}
	
	public void addGameObject(GameObject newGameObject) {
		gameObjects.add(newGameObject);
		if (isRunning) newGameObject.start();
	}
	
	public abstract void update(double dt);
	public abstract void draw(Graphics2D g2D);
}
