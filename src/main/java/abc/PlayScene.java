package abc;
import components.Ground;
import components.Movement;
import components.Spritesheet;
import util.AssetPool;
import util.Const;
import util.Vector2D;
import java.awt.Graphics2D;

public class PlayScene extends Scene{
	GameObject test;
	
	public PlayScene(String name) {
		super(name);
	}
	
	@Override
	public void init() {
		load_Resources();
		// add ground
		GameObject ground = new GameObject("Background", new Transform(new Vector2D(-200.0f, 0.0f)));
		ground.addComponent(new Ground(AssetPool.getSpritesheet("assets/bg1.jpg")));
		// the ground will not change position or something so just need to draw it
		renderer.submit(ground);
		
		test = new GameObject("new GameObject", new Transform(new Vector2D(600.0f, 100.0f)));
		Spritesheet spritesheet = AssetPool.getSpritesheet("assets/zombies/zombie_move.png");
		test.addComponent(spritesheet.getSprite(0));
		test.addComponent(new Movement(new Vector2D(Const.ZOMBIE_SPEED, 0.0f)));
		this.addGameObject(test);
	}
	
	public void load_Resources() {
		new Spritesheet("assets/zombies/zombie_move.png", Const.ZOMBIE_WIDTH, Const.ZOMBIE_HEIGHT, 14, 14);
		new Spritesheet("assets/bg1.jpg", 1400, 600, 1, 1);
	
	}
	
	@Override
	public void update(double dt) {
		for (GameObject g : this.gameObjects) {
			g.update(dt);
		}
	}
	
	@Override
	public void draw(Graphics2D g2D) {
		renderer.render(g2D);
	}
}
