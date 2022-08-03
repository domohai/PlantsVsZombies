package components;

import abc.GameObject;
import abc.MouseListener;
import abc.Window;
import util.Const;

import java.awt.*;
import java.awt.event.MouseEvent;

public class SnapToGrid extends Component {
	private Sprite sprite = null;
	private float alpha = 1.0f;
	private float x = 0, y = 0;
	
	@Override
	public void update(double dt) {
		sprite = this.gameObject.getComponent(Sprite.class);
		if (sprite != null) {
			if (MouseListener.getX() > Const.YARD_X && MouseListener.getX() <= (Const.YARD_X + Const.YARD_WIDTH)
					&& MouseListener.getY() > Const.YARD_Y && MouseListener.getY() <= (Const.YARD_Y + Const.YARD_HEIGHT)) {
				this.x = (float) Math.floor((MouseListener.getX() + MouseListener.getDx()) / Const.GRID_W);
				//this.y = (float) Math.floor((MouseListener.getY() + MouseListener.getDy()) / Const.GRID_H);
				if (MouseListener.getY() >= 70 && MouseListener.getY() < Const.LINE_1) {
					this.y = Const.LINE_1 - sprite.height;
					this.gameObject.line = 1;
				} else if (MouseListener.getY() >= Const.LINE_1 && MouseListener.getY() < Const.LINE_2) {
					this.y = Const.LINE_2 - sprite.height;
					this.gameObject.line = 2;
				} else if (MouseListener.getY() >= Const.LINE_2 && MouseListener.getY() < Const.LINE_3) {
					this.y = Const.LINE_3 - sprite.height;
					this.gameObject.line = 3;
				} else if (MouseListener.getY() >= Const.LINE_3 && MouseListener.getY() < Const.LINE_4) {
					this.y = Const.LINE_4 - sprite.height;
					this.gameObject.line = 4;
				} else {
					this.y = Const.LINE_5 - sprite.height;
					this.gameObject.line = 5;
				}
			} else {
				this.x = 60.0f;
				this.y = 70.0f;
			}
			this.gameObject.transform.position.x = (x * Const.GRID_W - 20);
			if (this.gameObject.transform.position.x < 60) this.gameObject.transform.position.x = 60;
			this.gameObject.transform.position.y = y;
			if (MouseListener.mouseButtonDown(MouseEvent.BUTTON1)) {
				GameObject newGameObject = this.gameObject.copy();
				Window.getScene().addGameObject(newGameObject);
			}
		}
	}
	
	@Override
	public void draw(Graphics2D g2D) {
		if (sprite != null) {
			alpha = 0.1f;
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
			g2D.setComposite(ac);
			g2D.drawImage(sprite.image, (int)this.gameObject.transform.position.x,
					(int)this.gameObject.transform.position.y, sprite.width, sprite.height, null);
			alpha = 1.0f;
			ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
			g2D.setComposite(ac);
		}
	}
	
	@Override
	public Component copy() {
		return null;
	}
	
}
