package abc;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Renderer {
	private Map<Integer, List<GameObject>> gameObjects;
	private int minValue;
	private int maxValue;
	private int currentValue;
	public Renderer() {
		this.gameObjects = new HashMap<>();
		this.minValue = Integer.MAX_VALUE;
		this.maxValue = Integer.MIN_VALUE;
		this.currentValue = 0;
	}
	
	public void submit(GameObject newGameObject) {
		this.gameObjects.computeIfAbsent(newGameObject.zIndex, (x) -> new ArrayList<>());
		this.gameObjects.get(newGameObject.zIndex).add(newGameObject);
	}
	
	public void render(Graphics2D g2D) {
		// loop through keySet to get max and min zIndex
		this.minValue = Integer.MAX_VALUE;
		this.maxValue = Integer.MIN_VALUE;
		for (Integer i : this.gameObjects.keySet()) {
			if (minValue > i) minValue = i;
			if (maxValue < i) maxValue = i;
		}
		// draw each gameObject base on its zIndex
		this.currentValue = minValue;
		while (currentValue <= maxValue) {
			if (this.gameObjects.get(currentValue) == null) {
				currentValue++;
				continue;
			}
			for (GameObject g : this.gameObjects.get(currentValue)) {
				g.draw(g2D);
			}
			currentValue++;
		}
	}
}
