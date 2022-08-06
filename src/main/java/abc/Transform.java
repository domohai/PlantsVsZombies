package abc;
import util.Vector2D;

public class Transform {
	public Vector2D position = new Vector2D();
	public Vector2D scale = new Vector2D();
	
	public Transform() {
		this.position = new Vector2D();
		this.scale = new Vector2D();
	}
	
	public Transform(Vector2D position, Vector2D scale) {
		this.position = position;
		this.scale = scale;
	}
	
	public void setValue(Vector2D position) {
		this.position = position;
	}
	
	public void setValue(Vector2D position, Vector2D scale) {
		this.position = position;
		this.scale = scale;
	}
	
	public Transform copy() {
		Transform transform = new Transform();
		transform.setValue(this.position.copy(), this.scale.copy());
		return transform;
	}
}
