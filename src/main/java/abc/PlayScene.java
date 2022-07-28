package abc;
import components.Sprite;
import components.Spritesheet;
import util.AssetPool;
import util.Const;
import util.Vector2D;

import java.awt.Graphics2D;


public class PlayScene extends Scene{
	GameObject test;
	
	public PlayScene() {
	
	}
	
	@Override
	public void init() {
		test = new GameObject("new GameObject", new Transform(new Vector2D(100.0f, 100.0f)));
		Spritesheet spritesheet = new Spritesheet("assets/zombies/zombie_move.png", 85, 142, 14, 14);
		test.addComponent(spritesheet.sprites.get(0));
		this.addGameObject(test);
	}
	
	@Override
	public void update(double dt) {
		for (GameObject g : this.gameObjects) {
			g.update(dt);
		}
	}
	
	@Override
	public void draw(Graphics2D g2D) {
		test.draw(g2D);
		/*
		for (GameObject g : this.gameObjects) {
			g.draw(g2D);
		}
		*/
	}
}
