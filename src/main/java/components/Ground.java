package components;

import java.awt.*;

public class Ground extends Component {
	private Spritesheet groundSprite;
	
	public Ground(Spritesheet groundSprite) {
		this.groundSprite = groundSprite;
	}
	
	@Override
	public void draw(Graphics2D g2D) {
		g2D.drawImage(this.groundSprite.getSprite(0).image, (int) this.gameObject.transform.position.x,
					(int)this.gameObject.transform.position.y, 1400, 600, null);
		/*
		g2D.setColor(Color.BLACK);
		g2D.setStroke(new BasicStroke(1.0f));
		// HORIZONTAL LINES
		float line = 70.0f;
		for (int i = 0; i < 6; i++) {
			g2D.draw(new Line2D.Float(0.0f, line, Const.SCREEN_WIDTH, line));
			line += 99.0f;
		}
		// VERTICAL LINES
		line = 60.0f;
		for (int i = 0; i < 10; i++) {
			g2D.draw(new Line2D.Float(line, 0.0f, line, Const.SCREEN_HEIGHT));
			line += 80.0f;
		}
		*/
	}
	
	@Override
	public Component copy() {
		return null;
	}
}
