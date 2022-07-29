package util;

public class Vector2D {
	public float x, y;
	
	public Vector2D() {
		this.x = 0.0f;
		this.y = 0.0f;
	}
	
	public Vector2D(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}
	
}
