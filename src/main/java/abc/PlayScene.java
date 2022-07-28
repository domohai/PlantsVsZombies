package abc;
import components.Sprite;
import util.AssetPool;
import util.Const;
import java.awt.Graphics2D;
import java.awt.Color;

public class PlayScene extends Scene{
	GameObject test;
	
	public PlayScene() {
	
	}
	
	@Override
	public void init() {
		test = new GameObject("new GameObject", new Transform());
		Sprite sprite = AssetPool.getSprite("assets/bg1.jpg");
		test.addComponent(sprite);
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
