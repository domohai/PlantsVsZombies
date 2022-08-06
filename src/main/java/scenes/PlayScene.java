package scenes;

import UI.MainContainer;
import abc.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import components.Component;
import components.*;
import util.AssetPool;
import util.Const;
import util.Vector2D;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayScene extends Scene {
	public GameObject test = null;
	public MouseControl mouseControl = null;
	public MainContainer mainContainer;
	
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
	public void init() {
		
		mouseControl = new MouseControl();
		mainContainer = new MainContainer();
		mainContainer.start();
		
		// add ground
		GameObject ground = new GameObject("Background", new Transform(new Vector2D(-200.0f, 0.0f), new Vector2D()),
				Const.GROUND_ZINDEX, Type.OTHER, 0);
		ground.addComponent(new Ground(AssetPool.getSpritesheet("assets/bg1.jpg")));
		// the ground will not change position or something so just draw it
		renderer.submit(ground);
		
		if (dataLoaded) return;
		
		test = new GameObject("new GameObject", new Transform(new Vector2D(600.0f, 100.0f), new Vector2D()),
				Const.ZOMBIE_ZINDEX, Type.ZOMBIE, 1);
		Spritesheet spritesheet = AssetPool.getSpritesheet("assets/zombies/zombie_move.png");
		test.addComponent(spritesheet.getSprite(0));
		
		Vector2D moveVector = new Vector2D(Const.ZOMBIE_SPEED, 0.0f);
		Movement movement = new Movement();
		movement.setValue(moveVector);
		test.addComponent(movement);
		
		Bounds bounds = new Bounds();
		bounds.setValue(Const.ZOMBIE_WIDTH, Const.ZOMBIE_HEIGHT);
		test.addComponent(bounds);
		this.addZombie(test);
		
	}
	
	@Override
	public void load_Resources() {
		new Spritesheet("assets/zombies/zombie_move.png", Const.ZOMBIE_WIDTH, Const.ZOMBIE_HEIGHT, 14, 14);
		new Spritesheet("assets/bg1.jpg", 1400, 600, 1, 1);
		new Spritesheet("assets/plants/sunflower.png", Const.PLANT_WIDTH, Const.PLANT_HEIGHT, 6, 6);
		new Spritesheet("assets/plants/pea.png", Const.PLANT_WIDTH, Const.PLANT_HEIGHT, 13, 13);
		new Spritesheet("assets/plants/snowpea.png", Const.PLANT_WIDTH, Const.PLANT_HEIGHT, 12, 12);
		new Spritesheet("assets/ui/seedrow.png", 54, 75, 3, 3);
	
	
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
			//System.out.println("zombie: " + this.zombies.get(i).size());
			//System.out.println("plant: " + this.plants.get(i).size());
		}
		for (GameObject g : this.gameObjects) {
			g.update(dt);
		}
		//System.out.println(this.gameObjects.size());
		mouseControl.update(dt);
		mainContainer.update(dt);
		// press Enter to save game
		if (KeyListener.get().is_keyPressed(KeyEvent.VK_ENTER)) {
			this.save();
		}
		//System.out.println(MouseListener.getX());
		//System.out.println(MouseListener.getY());
	}
	
	@Override
	public void draw(Graphics2D g2D) {
		renderer.render(g2D);
		mouseControl.draw(g2D);
		mainContainer.draw(g2D);
	}
	
	@Override
	public void save() {
		Gson gson = new GsonBuilder().setPrettyPrinting()
				.registerTypeAdapter(Component.class, new ComponentDeserializer())
				.registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
				.create();
		List<GameObject> tempList = new ArrayList<>();
		for (int i = 0; i < this.gameObjects.size(); i++) {
			tempList.add(this.gameObjects.get(i));
		}
		for (Integer i = 1; i < 6; i++) {
			for (int j = 0; j < this.plants.get(i).size(); j++) {
				tempList.add(this.plants.get(i).get(j));
			}
			for (int j = 0; j < this.zombies.get(i).size(); j++) {
				tempList.add(this.zombies.get(i).get(j));
			}
		}
		try {
			FileWriter writer = new FileWriter("data.txt");
			writer.write(gson.toJson(tempList));
			/*
			if (this.gameObjects.size() > 0) {
				writer.write(gson.toJson(this.gameObjects));
			}
			for (Integer i  = 1; i < 6; i++) {
				if (this.zombies.get(i).size() > 0) {
					writer.write(gson.toJson(this.zombies.get(i)));
				}
			}
			for (Integer i = 1; i < 6; i++) {
				if (this.plants.get(i).size() > 0) {
					writer.write(gson.toJson(this.plants.get(i)));
				}
			}
			*/
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
		JsonReader reader = null;
		try {
			inFile = new String(Files.readAllBytes(Paths.get("data.txt")));
			reader = new JsonReader(new StringReader(inFile));
			reader.setLenient(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!inFile.equals("")) {
			GameObject[] Objects = gson.fromJson(inFile, GameObject[].class);
			//System.out.println(Objects.length);
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
				
				if (Objects[i].objectType == Type.ZOMBIE) {
					this.addZombie(Objects[i]);
				} else if (Objects[i].objectType == Type.PLANT) {
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
			//return;
		}
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
		if (this.isRunning) zombie.start();
		this.renderer.submit(zombie);
	}
	
	@Override
	public void addPlant(GameObject plant) {
		if (plant.transform.position.x + Const.PLANT_WIDTH < 60 || plant.transform.position.x > Const.SCREEN_WIDTH ||
		plant.transform.position.y + Const.PLANT_HEIGHT < 70 || plant.transform.position.y > Const.SCREEN_HEIGHT) {
			return;
		}
		Bounds bounds = plant.getComponent(Bounds.class);
		if (bounds == null) {
			System.out.println("Forgot to add Bounds to plant!");
		}
		
		this.plants.get(plant.line).add(plant);
		if (this.isRunning) plant.start();
		this.renderer.submit(plant);
	}
}

