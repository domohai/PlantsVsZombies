package abc;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.*;
import util.AssetPool;
import util.Const;
import util.Vector2D;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PlayScene extends Scene {
	private GameObject test = null, snapToGrid = null;
	
	public PlayScene(String name) {
		super(name);
	}
	
	@Override
	public void init() {
		load_Resources();
		if (dataLoaded) return;
		
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
		test = new GameObject("new GameObject", transform, 5, Type.ZOMBIE, 1);
		
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
		this.addZombie(test);
		
		Sprite sprite = AssetPool.getSpritesheet("assets/plants/sunflower.png").getSprite(0);
		Vector2D mouseVec = new Vector2D();
		mouseVec.setValue(0, 0);
		Transform mouseTransform = new Transform();
		mouseTransform.setValue(mouseVec);
		snapToGrid = new GameObject("mouse", mouseTransform, 2, Type.PLANT, 0);
		snapToGrid.addComponent(sprite);
		snapToGrid.addComponent(new SnapToGrid());
	}
	
	@Override
	public void load_Resources() {
		new Spritesheet("assets/zombies/zombie_move.png", Const.ZOMBIE_WIDTH, Const.ZOMBIE_HEIGHT, 14, 14);
		new Spritesheet("assets/bg1.jpg", 1400, 600, 1, 1);
		new Spritesheet("assets/plants/sunflower.png", 75, 90, 6, 6);
		
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
		snapToGrid.update(dt);
		// press Enter to save game
		if (KeyListener.get().is_keyPressed(KeyEvent.VK_ENTER)) {
			this.save();
		}
	}
	
	@Override
	public void draw(Graphics2D g2D) {
		renderer.render(g2D);
		snapToGrid.draw(g2D);
	}
	
	@Override
	public void save() {
		Gson gson = new GsonBuilder().setPrettyPrinting()
				.registerTypeAdapter(Component.class, new ComponentDeserializer())
				.registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
				.create();
		try {
			FileWriter writer = new FileWriter("data.txt");
			
			//writer.write(gson.toJson(this.gameObjects));
			for (Integer i : this.zombies.keySet()) {
				for (int j = 0; j < this.zombies.get(i).size(); j++) {
					writer.write(gson.toJson(this.zombies.get(i)));
				}
				for (int j = 0; j < this.plants.get(i).size(); j++) {
					writer.write(gson.toJson(this.plants.get(i)));
				}
			}
			
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
			GameObject[] Objects = gson.fromJson(inFile, GameObject[].class);
			
			for (int i = 0; i < Objects.length; i++) {
				Sprite sprite = Objects[i].getComponent(Sprite.class);
				if (sprite != null && sprite.isSubImage) {
					String file_path = sprite.file_path;
					int index = sprite.index;
					Spritesheet spritesheet = AssetPool.getSpritesheet(file_path);
					sprite = spritesheet.getSprite(index).copy();
					// remove old Sprite which does not have image
					Objects[i].removeComponent(Sprite.class);
					if (sprite != null) {
						Objects[i].addComponent(sprite);
					}
				}
				
				if (Objects[i].getObjectType() == Type.ZOMBIE) {
					this.addZombie(Objects[i]);
				} else if (Objects[i].getObjectType() == Type.PLANT) {
					this.addPlant(Objects[i]);
				} else {
					this.addGameObject(Objects[i]);
				}
				//this.addGameObject(Objects[i]);
			}
			this.dataLoaded = true;
		}
	}
	
	@Override
	public void addZombie(GameObject zombie) {
		Bounds bounds = zombie.getComponent(Bounds.class);
		if (bounds == null) {
			System.out.println("Forgot to add Bounds to zombie!");
		} else {
			int line = zombie.line;
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
	
	@Override
	public void addPlant(GameObject plant) {
		Bounds bounds = plant.getComponent(Bounds.class);
		if (bounds == null) {
			System.out.println("Forgot to add Bounds to plant!");
		} else {
			int line = plant.line;
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
