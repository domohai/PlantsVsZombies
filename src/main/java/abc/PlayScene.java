package abc;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.*;
import util.AssetPool;
import util.Const;
import util.Vector2D;
import java.awt.Graphics2D;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PlayScene extends Scene {
	//GameObject test;
	
	public PlayScene(String name) {
		super(name);
	}
	
	@Override
	public void init() {
		load_Resources();
		// add ground
		Vector2D groundVector = new Vector2D();
		groundVector.setValue(-200.0f, 0.0f);
		Transform groundTransform = new Transform();
		groundTransform.setValue(groundVector);
		GameObject ground = new GameObject("Background", groundTransform, -5, Type.OTHER, 0);
		ground.addComponent(new Ground(AssetPool.getSpritesheet("assets/bg1.jpg")));
		// the ground will not change position or something so just draw it
		renderer.submit(ground);
		
		Vector2D vector2D = new Vector2D();
		vector2D.setValue(600.0f, 100.0f);
		Transform transform = new Transform();
		transform.setValue(vector2D);
		test = new GameObject("new GameObject", transform, 5, Type.ZOMBIE , 1);
		
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
		//this.addZombie(test);
		this.addGameObject(test);
		
		/*
		Gson gson = new GsonBuilder().setPrettyPrinting()
				.registerTypeAdapter(Component.class, new ComponentDeserializer())
				.registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
				.create();
		System.out.println(gson.toJson(test));
		
		 */
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
	
	@Override
	public void save() {
		Gson gson = new GsonBuilder().setPrettyPrinting()
				.registerTypeAdapter(Component.class, new ComponentDeserializer())
				.registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
				.create();
		try {
			FileWriter writer = new FileWriter("data.txt");
			writer.write(gson.toJson(this.gameObjects));
			//writer.write(gson.toJson(this.zombies));
			//writer.write(gson.toJson(this.plants));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void load() {
		Gson gson = new GsonBuilder().setPrettyPrinting()
				.registerTypeAdapter(Component.class, new ComponentDeserializer())
				.registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
				.create();
		String inFile = "";
		try {
			inFile = new String(Files.readAllBytes(Paths.get("data.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!inFile.equals("")) {
			GameObject[] Objects1 = gson.fromJson(inFile, GameObject[].class);
			// TODO: find a way to separate zombies and plants
			for (int i = 0; i < Objects1.length; i++) {
				if (Objects1[i].getObjectType() == Type.ZOMBIE) {
					this.addZombie(Objects1[i]);
				} else if (Objects1[i].getObjectType() == Type.PLANT) {
					this.addPlant(Objects1[i]);
				} else {
					this.addGameObject(Objects1[i]);
				}
			}
		}
		this.dataLoaded = true;
	}
	
	@Override
	public void addZombie(GameObject zombie) {
		Bounds bounds = zombie.getComponent(Bounds.class);
		if (bounds == null) {
			System.out.println("Forgot to add Bounds to zombie!");
		} else {
			int line = zombie.getLine();
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
}
