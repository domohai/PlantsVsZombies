package scenes;

import UI.MainContainer;
import abc.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.Component;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayScene extends Scene {
	public GameObject test = null;
	public MouseControl mouseControl = null;
	public GameObject mainContainer = null;
	
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
		this.isRunning = true;
	}
	
	@Override
	public void init() {
		this.mouseControl = new MouseControl();
		this.mainContainer = new GameObject("Container", Const.CONTAINER_ZINDEX);
		MainContainer container = new MainContainer();
		this.mainContainer.transform = new Transform(new Vector2D(50.0f, 0.0f), new Vector2D());
		this.mainContainer.addComponent(container);
		this.mainContainer.start();
		this.renderer.submit(this.mainContainer);
		
		// add ground
		GameObject ground = new GameObject("Background", new Transform(new Vector2D(-200.0f, 0.0f), new Vector2D()),
				Const.GROUND_ZINDEX, Type.OTHER, 0);
		ground.addComponent(new Ground(AssetPool.getSpritesheet("assets/bg1.jpg")));
		// the ground will not change position or something so just draw it
		renderer.submit(ground);
		
		if (this.dataLoaded) return;
		
		test = Prefabs.generateZombie(AssetPool.getSpritesheet("assets/zombies/zombie_move.png"),
				AssetPool.getSpritesheet("assets/zombies/zomattack.png"));
		this.addZombie(test);
		
	}
	
	@Override
	public void load_Resources() {
		new Spritesheet("assets/zombies/zombie_move.png", Const.ZOMBIE_WIDTH, Const.ZOMBIE_HEIGHT, 14, 14);
		new Spritesheet("assets/bg1.jpg", 1400, 600, 1, 1);
		new Spritesheet("assets/plants/sunflower.png", Const.PLANT_WIDTH, Const.PLANT_HEIGHT, 6, 6);
		new Spritesheet("assets/plants/peashooter_idle.png", Const.PLANT_WIDTH, Const.PLANT_HEIGHT, 8, 8);
		new Spritesheet("assets/plants/peashooter_shoot.png", Const.PLANT_WIDTH, Const.PLANT_HEIGHT, 13, 13);
		new Spritesheet("assets/plants/snowpeashooter_idle.png", Const.PLANT_WIDTH, Const.PLANT_HEIGHT, 8, 8);
		new Spritesheet("assets/plants/snowpeashooter_shoot.png", Const.PLANT_WIDTH, Const.PLANT_HEIGHT, 12, 12);
		new Spritesheet("assets/ui/seedrow.png", 54, 75, 3, 3);
		new Spritesheet("assets/zombies/zomattack.png", Const.ZOMBIE_WIDTH + 10, Const.ZOMBIE_HEIGHT, 9, 9);
	
	}
	
	@Override
	public void update(double dt) {
		// get the nearest plant and check collision with all zombies on the same line
		GameObject updateObject, nearestPlant;
		for (int i = 1; i < 6; i++) {
			if (this.plants.get(i).size() > 0) {
				nearestPlant = this.plants.get(i).get(0);
				for (int j = 0; j < this.plants.get(i).size(); j++) {
					updateObject = this.plants.get(i).get(j);
					updateObject.update(dt);
					if (updateObject.isDead) {
						this.plants.get(i).remove(j);
						this.renderer.destroy(updateObject);
						j--;
					} else if (updateObject.transform.position.x > nearestPlant.transform.position.x) {
						nearestPlant = updateObject;
					}
				}
				if (this.zombies.get(i).size() > 0) {
					for (GameObject g : this.zombies.get(i)) {
						if (Bounds.checkCollision(nearestPlant.getComponent(Bounds.class), g.getComponent(Bounds.class))) {
							g.getComponent(Movement.class).setVelocity(new Vector2D());
							g.getComponent(StateMachine.class).trigger("attack");
						}
					}
				}
			}
			if (this.zombies.get(i).size() > 0) {
				for (int j = 0; j < this.zombies.get(i).size(); j++) {
					updateObject = this.zombies.get(i).get(j);
					updateObject.update(dt);
					if (updateObject.isDead) {
						this.zombies.get(i).remove(j);
						this.renderer.destroy(updateObject);
						j--;
					}
				}
			}
		}
		if (this.gameObjects.size() > 0) {
			for (int i = 0; i < this.gameObjects.size(); i++) {
				updateObject = this.gameObjects.get(i);
				updateObject.update(dt);
				if (updateObject.isDead) {
					this.gameObjects.remove(i);
					this.renderer.destroy(updateObject);
					i--;
				}
			}
		}
		
		this.mouseControl.update(dt);
		this.mainContainer.update(dt);
		// press Enter to save game
		if (KeyListener.get().is_keyPressed(KeyEvent.VK_ENTER)) {
			this.save();
		}
	}
	
	@Override
	public void draw(Graphics2D g2D) {
		this.renderer.render(g2D);
		this.mouseControl.draw(g2D);
	}
	
	@Override
	public void save() {
		Gson gson = new GsonBuilder().setPrettyPrinting()
				.registerTypeAdapter(Component.class, new ComponentDeserializer())
				.registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
				.enableComplexMapKeySerialization()
				.create();
		// add all objects to one list
		List<GameObject> saveList = new ArrayList<>();
		for (int i = 0; i < this.gameObjects.size(); i++) {
			saveList.add(this.gameObjects.get(i));
		}
		for (Integer i = 1; i < 6; i++) {
			for (int j = 0; j < this.plants.get(i).size(); j++) {
				saveList.add(this.plants.get(i).get(j));
			}
			for (int j = 0; j < this.zombies.get(i).size(); j++) {
				saveList.add(this.zombies.get(i).get(j));
			}
		}
		
		try {
			FileWriter writer = new FileWriter("data.txt");
			writer.write(gson.toJson(saveList));
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
				.enableComplexMapKeySerialization()
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
				StateMachine stateMachine = Objects[i].getComponent(StateMachine.class);
				if (stateMachine != null) {
					for (AnimationState state : stateMachine.states) {
						Spritesheet spritesheet = AssetPool.getSpritesheet(state.file_path);
						state.frames.clear();
						for (Sprite sprite : spritesheet.sprites) {
							state.addFrame(sprite.copy(), Const.DEFAULT_FRAME_TIME);
						}
					}
				} else {
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
				}
				if (Objects[i].objectType == Type.ZOMBIE) {
					this.addZombie(Objects[i]);
				} else if (Objects[i].objectType == Type.PLANT) {
					this.addPlant(Objects[i]);
				} else {
					this.addGameObject(Objects[i]);
				}
			}
			this.dataLoaded = true;
		}
	}
	
	@Override
	public void addZombie(GameObject zombie) {
		Bounds bounds = zombie.getComponent(Bounds.class);
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
		this.plants.get(plant.line).add(plant);
		if (this.isRunning) plant.start();
		this.renderer.submit(plant);
	}
}

