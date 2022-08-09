package components;

public class Frame {
	public Sprite sprite;
	public float frameTime;
	
	public Frame() {
		this.sprite = null;
		this.frameTime = 0.0f;
	}
	
	public Frame(Sprite sprite, float frameTime) {
		this.sprite = sprite;
		this.frameTime = frameTime;
	}
	
}
