package components;
import java.awt.Graphics2D;

public class Ground extends Component {
	private Spritesheet groundSprite;
	
	public Ground(Spritesheet groundSprite) {
		this.groundSprite = groundSprite;
	}
	
	@Override
	public void draw(Graphics2D g2D) {
		g2D.drawImage(this.groundSprite.getSprite(0).image, (int) this.gameObject.transform.position.x,
					(int)this.gameObject.transform.position.y, 1400, 600, null);
	}
	
	@Override
	public Component copy() {
		return null;
	}
}
