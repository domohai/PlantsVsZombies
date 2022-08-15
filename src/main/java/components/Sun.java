package components;

import abc.MouseListener;
import util.Const;
import java.awt.event.MouseEvent;

public class Sun extends Component {
	public transient int OFFSET_Y = Const.CONTAINER_OFFSET_Y - Const.TOOLBAR_TOP;
	public boolean applyGravity = true;
	
	@Override
	public void update(double dt) {
		if (MouseListener.mouseButtonDown(MouseEvent.BUTTON1)) {
			if (this.gameObject.transform.position.y >= (this.OFFSET_Y)) {
				if (MouseListener.getX() >= this.gameObject.transform.position.x
						&& MouseListener.getX() <= (this.gameObject.transform.position.x + Const.SUN_WIDTH)
						&& (MouseListener.getY()) >= (this.gameObject.transform.position.y + Const.TOOLBAR_TOP)
						&& (MouseListener.getY()) <= (this.gameObject.transform.position.y + Const.SUN_HEIGHT + Const.TOOLBAR_TOP)) {
					this.gameObject.isDead = true;
					SunBank.BALANCE += 25;
				}
			}
		} else if (this.gameObject.transform.position.y > Const.SCREEN_HEIGHT) {
			this.gameObject.isDead = true;
		}
		if (this.applyGravity) {
			this.gameObject.transform.position.y += Const.GRAVITY * dt;
		}
	}
	
	@Override
	public Component copy() {
		return null;
	}
}
