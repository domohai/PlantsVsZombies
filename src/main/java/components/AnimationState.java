package components;

import java.util.ArrayList;
import java.util.List;

public class AnimationState {
	public String title, file_path;
	public List<Frame> frames = new ArrayList<>();
	public static Sprite defaultSprite = new Sprite();
	private transient float timeTracker = 0.0f;
	private transient int currentSprite = 0;
	private boolean doesLoop = false;
	
	public AnimationState() {}
	
	public AnimationState(String file_path) {
		this.file_path = file_path;
	}
	
	public void addFrame(Sprite sprite, float frameTime) {
		this.frames.add(new Frame(sprite, frameTime));
	}
	
	public void setLoop(boolean doesLoop) {
		this.doesLoop = doesLoop;
	}
	
	public void update(double dt) {
		if (this.currentSprite < this.frames.size()) {
			this.timeTracker -= dt;
			if (timeTracker <= 0.0f) {
				if (this.currentSprite != this.frames.size() - 1 || this.doesLoop) {
					currentSprite = (currentSprite + 1) % this.frames.size();
				}
				timeTracker = this.frames.get(this.currentSprite).frameTime;
			}
		}
	}
	
	public Sprite getCurrentSprite() {
		if (this.currentSprite < this.frames.size()) {
			return this.frames.get(this.currentSprite).sprite;
		}
		return AnimationState.defaultSprite;
	}
}
