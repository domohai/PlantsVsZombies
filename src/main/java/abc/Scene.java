package abc;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.Graphics2D;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
	private String name;
	protected boolean isRunning = false;
	protected List<GameObject> gameObjects;
	protected Renderer renderer;
	
	public Scene(String name) {
		this.name = name;
		this.gameObjects = new ArrayList<>();
		this.renderer = new Renderer();
	}
	
	public void init() {}
	
	public void start() {
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
			GameObject[] gameObjects1 = gson.fromJson(inFile, GameObject[].class);
			// todo: find a way to separate zombies and plants
			
		}
	}
	
	public void save() {
		Gson gson = new GsonBuilder().setPrettyPrinting()
				.registerTypeAdapter(Component.class, new ComponentDeserializer())
				.registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
				.create();
		try {
			FileWriter writer = new FileWriter("data.txt");
			writer.write(gson.toJson(this.gameObjects));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
