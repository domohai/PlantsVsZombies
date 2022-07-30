package abc;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.*;
import util.AssetPool;
import util.Const;
import util.Vector2D;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayScene extends Scene {
	public Map<Integer, List<GameObject>> plants;
	public Map<Integer, List<GameObject>> zombies;
	GameObject test;
	
	public PlayScene(String name) {
		super(name);
		this.plants = new HashMap<>();
		this.zombies = new HashMap<>();
		for (Integer i = 1; i < 6; i++) {
			this.plants.put(i, new ArrayList<>());
			this.zombies.put(i, new ArrayList<>());
		}
	}
	
	@Override
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
	
	@Override
	public void init() {
		load_Resources();
		// add ground
		Vector2D groundVector = new Vector2D();
		groundVector.setValue(-200.0f, 0.0f);
		Transform groundTransform = new Transform();
		groundTransform.setValue(groundVector);
		GameObject ground = new GameObject("Background", groundTransform, -5);
		ground.addComponent(new Ground(AssetPool.getSpritesheet("assets/bg1.jpg")));
		// the ground will not change position or something so just draw it
		renderer.submit(ground);
		
		Vector2D vector2D = new Vector2D();
		vector2D.setValue(600.0f, 100.0f);
		Transform transform = new Transform();
		transform.setValue(vector2D);
		test = new GameObject("new GameObject", transform, 5);
		
		Spritesheet spritesheet = AssetPool.getSpritesheet("assets/zombies/zombie_move.png");
		test.addComponent(spritesheet.getSprite(0));
		
		Vector2D moveVector = new Vector2D();
		moveVector.setValue(Const.ZOMBIE_SPEED, 0.0f);
		Movement movement = new Movement();
		movement.setValue(moveVector);
		test.addComponent(movement);
		
		Bounds bounds = new Bounds();
		bounds.setValue(Const.ZOMBIE_WIDTH, Const.ZOMBIE_HEIGHT);
		test.addComponent(bounds);
		this.addZombie(test, 1);
		
		
		
	}
	
	public void load_Resources() {
		new Spritesheet("assets/zombies/zombie_move.png", Const.ZOMBIE_WIDTH, Const.ZOMBIE_HEIGHT, 14, 14);
		new Spritesheet("assets/bg1.jpg", 1400, 600, 1, 1);
	
	}
	
	@Override
	public void update(double dt) {
		for (Integer i = 1; i < 6; i++) {
			for (GameObject g : this.plants.get(i)) {
				g.update(dt);
			}
			for (GameObject g : this.zombies.get(i)) {
				g.update(dt);
			}
		}
		for (GameObject g : this.gameObjects) {
			g.update(dt);
		}
	}
	
	@Override
	public void draw(Graphics2D g2D) {
		renderer.render(g2D);
	}
	
	public void addZombie(GameObject zombie, Integer line) {
		Bounds bounds = zombie.getComponent(Bounds.class);
		if (bounds == null) {
			System.out.println("Forgot to add Bounds to zombie!");
		} else {
			switch (line) {
				case 1:
					zombie.transform.position.y = Const.LINE_1 - bounds.height + 10;
					break;
				case 2:
					zombie.transform.position.y = Const.LINE_2 - bounds.height + 10;
					break;
				case 3:
					zombie.transform.position.y = Const.LINE_3 - bounds.height + 10;
					break;
				case 4:
					zombie.transform.position.y = Const.LINE_4 - bounds.height + 10;
					break;
				case 5:
					zombie.transform.position.y = Const.LINE_5 - bounds.height + 10;
					break;
				default:
					System.out.println("Not a valid line: " + line);
					break;
			}
			this.zombies.get(line).add(zombie);
			if (isRunning) zombie.start();
			renderer.submit(zombie);
		}
	}
	
	public void addPlant(GameObject plant, Integer line) {
		Bounds bounds = plant.getComponent(Bounds.class);
		if (bounds == null) {
			System.out.println("Forgot to add Bounds to plant!");
		} else {
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
