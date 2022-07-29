package abc;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Renderer {
	List<GameObject> gameObjects;
	public Renderer() {
		this.gameObjects = new ArrayList<>();
	}
	
	public void submit(GameObject newGameObject) {
		this.gameObjects.add(newGameObject);
	}
	
	public void render(Graphics2D g2D) {
		for (GameObject g : this.gameObjects) {
			g.draw(g2D);
		}
	}
}
