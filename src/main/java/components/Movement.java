package components;
import util.Vector2D;

public class Movement extends Component {
	public Vector2D velocity;
	
	public Movement() {
		this.velocity = new Vector2D();
	}
	
	public Movement(Vector2D velocity) {
		this.velocity = velocity;
	}
	
	public void setVelocity(float x, float y) {
		this.velocity.x = x;
		this.velocity.y = y;
	}
	
	@Override
	public void update(double dt) {
		this.gameObject.transform.position.x -= this.velocity.x * dt;
	}
	
	@Override
	public Component copy() {
		return null;
	}
	
}
