package util;

public class Vector2D {
	public float x, y;
	
	public Vector2D() {
		this.x = 0;
		this.y = 0;
	}
	
	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setValue(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D copy() {
		Vector2D vector2D = new Vector2D();
		vector2D.setValue(this.x, this.y);
		return vector2D;
	}
	
}
