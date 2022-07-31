package abc;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.Bounds;
import util.Const;
import java.awt.Graphics2D;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Scene {
	private String name;
	protected boolean isRunning = false;
	protected List<GameObject> gameObjects;
	protected Map<Integer, List<GameObject>> plants;
	protected Map<Integer, List<GameObject>> zombies;
	protected Renderer renderer;
	protected boolean dataLoaded = false;
	protected GameObject test;
	
	public Scene(String name) {
		this.name = name;
		this.gameObjects = new ArrayList<>();
		this.renderer = new Renderer();
		this.plants = new HashMap<>();
		this.zombies = new HashMap<>();
		for (Integer i = 1; i < 6; i++) {
			this.plants.put(i, new ArrayList<>());
			this.zombies.put(i, new ArrayList<>());
		}
	}
	
	public void init() {}
	
	public void start() {
		for (Integer i = 1; i < 6; i++) {
			for (GameObject g : this.plants.get(i)) {
				g.start();
			}
			for (GameObject g : this.zombies.get(i)) {
				g.start();
			}
		}
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
	
	public void load() {}
	
	public void save() {}
	
	public void addZombie(GameObject zombie) {}
	
	public void addPlant(GameObject plant) {
		Bounds bounds = plant.getComponent(Bounds.class);
		if (bounds == null) {
			System.out.println("Forgot to add Bounds to plant!");
		} else {
			int line = plant.getLine();
			switch (line) {
				case 1:
					plant.transform.position.y = Const.LINE_1 - bounds.height;
					break;
				case 2:
					plant.transform.position.y = Const.LINE_2 - bounds.height;
					break;
				case 3:
					plant.transform.position.y = Const.LINE_3 - bounds.height;
					break;
				case 4:
					plant.transform.position.y = Const.LINE_4 - bounds.height;
					break;
				case 5:
					plant.transform.position.y = Const.LINE_5 - bounds.height;
					break;
				default:
					System.out.println("Not a valid line: " + line);
					break;
			}
			this.plants.get(line).add(plant);
			if (isRunning) plant.start();
			renderer.submit(plant);
		}
	}
}
