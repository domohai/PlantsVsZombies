package components;
import abc.Component;
import util.Const;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Ground extends Component {
	private Spritesheet groundSprite;
	
	public Ground(Spritesheet groundSprite) {
		this.groundSprite = groundSprite;
	}
	
	@Override
	public void draw(Graphics2D g2D) {
		g2D.drawImage(this.groundSprite.getSprite(0).image, (int) this.gameObject.transform.position.x,
					(int)this.gameObject.transform.position.y, 1400, 600, null);
		g2D.setColor(Color.BLACK);
		g2D.setStroke(new BasicStroke(1.0f));
		float lineHeight = 165.0f;
		for (int i = 0; i < 5; i++) {
			g2D.draw(new Line2D.Float(0.0f, lineHeight, Const.SCREEN_WIDTH, lineHeight));
			lineHeight += 99.0f;
		}
	}
}
