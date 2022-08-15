package components;

import util.Const;

public class Bullet extends Component {
	public float debounceTimeLeft = 0.2f;
	public boolean hitZombie = false;
	
	@Override
	public void update(double dt) {
		if (this.hitZombie) {
			debounceTimeLeft -= dt;
			this.gameObject.getComponent(Movement.class).setVelocity(0.0f, 0.0f);
			this.gameObject.getComponent(StateMachine.class).trigger("explode");
			if (debounceTimeLeft <= 0.0f) {
				this.gameObject.isDead = true;
			}
		}
		if (this.gameObject.transform.position.x > Const.SCREEN_WIDTH) {
			this.gameObject.isDead = true;
		}
	}
	
	@Override
	public Component copy() {
		return null;
	}
}
