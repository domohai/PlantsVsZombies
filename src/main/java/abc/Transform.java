package abc;
import util.Vector2D;

public class Transform {
	public Vector2D position;
	public Vector2D scale;
	
	public Transform() {
		init(new Vector2D(), new Vector2D());
	}
	
	public Transform(Vector2D position) {
		init(position, new Vector2D());
	}
	
	public Transform(Vector2D position, Vector2D scale) {
		init(position, scale);
	}
	
	public void init(Vector2D position, Vector2D scale) {
		this.position = position;
		this.scale = scale;
	}
	
	public Transform copy() {
		return new Transform(this.position.copy(), this.scale.copy());
	}
}
