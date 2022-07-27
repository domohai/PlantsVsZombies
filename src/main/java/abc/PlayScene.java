package abc;
import util.Const;
import java.awt.Graphics2D;
import java.awt.Color;

public class PlayScene extends Scene{
	
	public PlayScene() {
	
	}
	
	@Override
	public void init() {
	
	}
	
	@Override
	public void update(double dt) {
		for (GameObject g : this.gameObjects) {
			g.update(dt);
		}
	}
	
	@Override
	public void draw(Graphics2D g2D) {
		g2D.setColor(Color.BLACK);
		g2D.fillRect(0,0, Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);
	}
}
