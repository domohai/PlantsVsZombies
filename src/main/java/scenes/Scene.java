package scenes;

import abc.GameObject;
import abc.Renderer;

import java.awt.*;
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
	
	public Scene(String name) {
		this.name = name;
		this.gameObjects = new ArrayList<>();
		this.renderer = new Renderer();
		
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
	
	public void load_Resources() {}
	
	public void load() {}
	public void save() {}
	
	public void addZombie(GameObject zombie) {}
	public void addPlant(GameObject plant) {}
}
