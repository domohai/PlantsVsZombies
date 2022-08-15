package components;

import util.Const;

public class Zombie extends Component {
	public boolean isAttacking = false;
	
	@Override
	public void update(double dt) {
		if (this.isAttacking) {
			this.gameObject.getComponent(Movement.class).setVelocity(0.0f, 0.0f);
			this.gameObject.getComponent(StateMachine.class).trigger("attack");
		} else {
			this.gameObject.getComponent(Movement.class).setVelocity(Const.ZOMBIE_SPEED, 0.0f);
			this.gameObject.getComponent(StateMachine.class).trigger("walk");
		}
	}
	
	@Override
	public Component copy() {
		return null;
	}
}
