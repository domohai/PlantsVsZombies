package components;
import abc.Component;
import util.Vector2D;

public class Movement extends Component {
	public Vector2D velocity = new Vector2D();
	
	public void setValue(Vector2D velocity) {
		this.velocity = velocity;
	}
	
	@Override
	public void update(double dt) {
		this.gameObject.transform.position.x -= this.velocity.x * dt;
	}
	
}
