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
	public MouseControl mouseControl;
	public GameObject mainContainer = null, Bank;
	private GameObject nearestPlant = null, nearestZombie = null, nearestBullet = null;
	private SpawnMachine spawnMachine;
	
	public PlayScene(String name) {
		super(name);
		this.spawnMachine = new SpawnMachine();
		this.mouseControl = new MouseControl();
		this.objectsToRemove = new ArrayList<>();
		this.plants = new HashMap<>();
		this.zombies = new HashMap<>();
		this.bullets = new HashMap<>();
		for (Integer i = 1; i < 6; i++) {
			this.plants.put(i, new ArrayList<>());
			this.zombies.put(i, new ArrayList<>());
			this.bullets.put(i, new ArrayList<>());
		}
	}
	
	@Override
	public void start() {
		for (int i = 1; i < 6; i++) {
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
		this.mainContainer = new GameObject("Container", Const.CONTAINER_ZINDEX);
		this.mainContainer.transform = new Transform(new Vector2D(50.0f, 0.0f), new Vector2D());
		this.mainContainer.addComponent(new MainContainer());
		this.mainContainer.start();
		this.renderer.submit(this.mainContainer);
		this.spawnMachine.spawnSun();
		// add ground
		GameObject ground = new GameObject("Background", new Transform(new Vector2D(-200.0f, 0.0f), new Vector2D()),
				Const.GROUND_ZINDEX, Type.OTHER, 0);
		ground.addComponent(new Ground(AssetPool.getSpritesheet("assets/bg1.jpg")));
		// the ground will not change position or something so just draw it
		this.renderer.submit(ground);
		
		if (this.dataLoaded) return;
		
		this.Bank = new GameObject("SunBank", Const.CONTAINER_ZINDEX);
		this.Bank.addComponent(new SunBank());
		this.addGameObject(this.Bank);
		
		test = Prefabs.generateZombie(AssetPool.getSpritesheet("assets/zombies/zombie_move.png"),
				AssetPool.getSpritesheet("assets/zombies/zomattack.png"));
		this.addZombie(test);
		
	}
	
	@Override
	public void load_Resources() {
		// normal zombies
		new Spritesheet("assets/zombies/zombie_move.png", Const.ZOMBIE_WIDTH, Const.ZOMBIE_HEIGHT, 14, 14);
		new Spritesheet("assets/zombies/zomattack.png", Const.ZOMBIE_WIDTH + 10, Const.ZOMBIE_HEIGHT, 9, 9);
		// background
		new Spritesheet("assets/bg1.jpg", 1400, 600, 1, 1);
		// plants
		new Spritesheet("assets/plants/sunflower.png", Const.PLANT_WIDTH, Const.PLANT_HEIGHT, 6, 6);
		new Spritesheet("assets/plants/peashooter_idle.png", Const.PLANT_WIDTH, Const.PLANT_HEIGHT, 8, 8);
		new Spritesheet("assets/plants/peashooter_shoot.png", Const.PLANT_WIDTH, Const.PLANT_HEIGHT, 13, 13);
		new Spritesheet("assets/plants/snowpeashooter_idle.png", Const.PLANT_WIDTH, Const.PLANT_HEIGHT, 8, 8);
		new Spritesheet("assets/plants/snowpeashooter_shoot.png", Const.PLANT_WIDTH, Const.PLANT_HEIGHT, 12, 12);
		// ui
		new Spritesheet("assets/ui/seedrow.png", 54, 75, 3, 3);
		// bullet
		new Spritesheet("assets/bullet/ProjectilePea.png", 28, 28, 1, 1);
		new Spritesheet("assets/bullet/ProjectileSnowPea.png", 28, 28, 1, 1);
		new Spritesheet("assets/bullet/pea_splats.png", 24, 24, 4, 4);
		new Spritesheet("assets/bullet/SnowPea_splats.png", 24, 24, 4, 4);
		// sun
		new Spritesheet("assets/sun/sun_small.png", 50, 50, 1, 1);
		
	}
	
	@Override
	public void update(double dt) {
		// if there is plant on this line then
		// get the nearest plant and check collision with all zombies on the same line
		// also check if there is any zombies on this line then
		// change plant state to shoot
		// if the current game object is dead, put it in the queue
		// and remove it when we're done updating
		for (int i = 1; i < 6; i++) {
			if (this.plants.get(i).size() > 0) {
				this.nearestPlant = this.plants.get(i).get(0);
				for (GameObject updatingPlant : this.plants.get(i)) {
					Shoot shoot = updatingPlant.getComponent(Shoot.class);
					if (shoot != null) {
						if (this.zombies.get(i).size() > 0) {
							shoot.isShooting = true;
						} else {
							shoot.isShooting = false;
						}
					}
					updatingPlant.update(dt);
					if (updatingPlant.transform.position.x > this.nearestPlant.transform.position.x) {
						this.nearestPlant = updatingPlant;
					}
					if (updatingPlant.isDead) {
						this.objectsToRemove.add(updatingPlant);
					}
				}
			} else {
				this.nearestPlant = null;
			}
			if (this.zombies.get(i).size() > 0) {
				this.nearestZombie = this.zombies.get(i).get(0);
				for (GameObject updatingZombie : this.zombies.get(i)) {
					updatingZombie.update(dt);
					if (this.nearestPlant != null) {
						if (Bounds.checkCollision(this.nearestPlant.getComponent(Bounds.class), updatingZombie.getComponent(Bounds.class))) {
							Bounds.resolvePlantCollision(this.nearestPlant, updatingZombie);
						} else {
							updatingZombie.getComponent(Zombie.class).isAttacking = false;
						}
					} else {
						updatingZombie.getComponent(Zombie.class).isAttacking = false;
					}
					if (updatingZombie.transform.position.x < this.nearestZombie.transform.position.x) {
						this.nearestZombie = updatingZombie;
					}
					if (updatingZombie.isDead) {
						this.objectsToRemove.add(updatingZombie);
					}
				}
			} else {
				this.nearestZombie = null;
			}
			if (this.bullets.get(i).size() > 0) {
				this.nearestBullet = this.bullets.get(i).get(0);
				for (GameObject updatingBullet : this.bullets.get(i)) {
					updatingBullet.update(dt);
					if (updatingBullet.transform.position.x > this.nearestBullet.transform.position.x) {
						this.nearestBullet = updatingBullet;
					}
					if (this.nearestZombie != null) {
						if (Bounds.checkCollision(updatingBullet.getComponent(Bounds.class),
								this.nearestZombie.getComponent(Bounds.class))) {
							Bounds.resolveBulletCollision(updatingBullet, this.nearestZombie);
						}
					}
					if (updatingBullet.isDead) {
						this.objectsToRemove.add(updatingBullet);
					}
				}
			}
		}
		if (this.gameObjects.size() > 0) {
			for (GameObject g : this.gameObjects) {
				g.update(dt);
				if (g.isDead) {
					this.objectsToRemove.add(g);
				}
			}
		}
		this.mouseControl.update(dt);
		this.mainContainer.update(dt);
		if (this.objectsToRemove.size() > 0) {
			for (GameObject g : this.objectsToRemove) {
				this.remove(g);
			}
			this.objectsToRemove.clear();
		}
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
	public void remove(GameObject object) {
		if (object.objectType == Type.PLANT) {
			this.plants.get(object.line).remove(object);
		} else if (object.objectType == Type.ZOMBIE) {
			this.zombies.get(object.line).remove(object);
		} else if (object.objectType == Type.BULLET) {
			this.bullets.get(object.line).remove(object);
		} else {
			this.gameObjects.remove(object);
		}
		this.renderer.destroy(object);
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
				Shoot shoot = Objects[i].getComponent(Shoot.class);
				if (shoot != null) {
					String flySpriteFilePath = shoot.flySpritesheet.file_path;
					String explodeSpriteFilePath = shoot.explode.file_path;
					shoot.flySpritesheet = AssetPool.getSpritesheet(flySpriteFilePath);
					shoot.explode = AssetPool.getSpritesheet(explodeSpriteFilePath);
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
	public void addBullet(GameObject bullet) {
		this.bullets.get(bullet.line).add(bullet);
		if (this.isRunning) bullet.start();
		this.renderer.submit(bullet);
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

