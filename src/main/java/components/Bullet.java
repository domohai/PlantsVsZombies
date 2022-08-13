package components;

import util.Const;

public class Bullet extends Component {
	
	@Override
	public void update(double dt) {
		if (this.gameObject.transform.position.x > Const.SCREEN_WIDTH) {
			this.gameObject.isDead = true;
		}
	}
	
	@Override
	public Component copy() {
		return null;
	}
}
