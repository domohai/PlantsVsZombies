package components;
import abc.GameObject;
import abc.MouseListener;
import abc.Window;
import util.Const;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class MouseControl extends Component {
	public GameObject holdingObject = null;
	private float x = 0, y = 0;
	
	
	public void pickUpObject(GameObject object) {
		this.holdingObject = object;
		
	}
	
	public void place() {
		Bounds bounds = new Bounds();
		bounds.setValue(Const.PLANT_WIDTH, Const.PLANT_HEIGHT);
		this.holdingObject.addComponent(bounds);
		if (MouseListener.getY() >= 30 && MouseListener.getY() < Const.LINE_1) {
			this.holdingObject.line = 1;
		} else if (MouseListener.getY() >= Const.LINE_1 && MouseListener.getY() < Const.LINE_2) {
			this.holdingObject.line = 2;
		} else if (MouseListener.getY() >= Const.LINE_2 && MouseListener.getY() < Const.LINE_3) {
			this.holdingObject.line = 3;
		} else if (MouseListener.getY() >= Const.LINE_3 && MouseListener.getY() < Const.LINE_4) {
			this.holdingObject.line = 4;
		} else {
			this.holdingObject.line = 5;
		}
		Window.getScene().addPlant(this.holdingObject);
		this.holdingObject = null;
	}
	
	@Override
	public void update(double dt) {
		if (this.holdingObject != null) {
			/*
			this.holdingObject.transform.position.x = MouseListener.getX() - 20;
			this.holdingObject.transform.position.y = MouseListener.getY() - 20;
			*/
			Sprite sprite = this.holdingObject.getComponent(Sprite.class);
			if (MouseListener.getX() > Const.YARD_X && MouseListener.getX() <= (Const.YARD_X + Const.YARD_WIDTH)
				&& MouseListener.getY() > Const.YARD_Y && MouseListener.getY() <= (Const.YARD_Y + Const.YARD_HEIGHT)) {
				this.x = (float)Math.floor((MouseListener.getX() + MouseListener.getDx()) / Const.GRID_W);
				//this.y = (float) Math.floor((MouseListener.getY() + MouseListener.getDy()) / Const.GRID_H);
				if (MouseListener.getY() >= 30 && MouseListener.getY() < Const.LINE_1) {
					this.y = Const.LINE_1 - sprite.height;
				} else if (MouseListener.getY() >= Const.LINE_1 && MouseListener.getY() < Const.LINE_2) {
					this.y = Const.LINE_2 - sprite.height;
				} else if (MouseListener.getY() >= Const.LINE_2 && MouseListener.getY() < Const.LINE_3) {
					this.y = Const.LINE_3 - sprite.height;
				} else if (MouseListener.getY() >= Const.LINE_3 && MouseListener.getY() < Const.LINE_4) {
					this.y = Const.LINE_4 - sprite.height;
				} else {
					this.y = Const.LINE_5 - sprite.height;
				}
			} else {
				this.x = -2000.0f;
				this.y = -2000.0f;
			}
			x = x * Const.GRID_W - 20;
			if (x <= 60) x = 60;
			this.holdingObject.transform.position.x = x;
			this.holdingObject.transform.position.y = y;
			if (MouseListener.mouseButtonDown(MouseEvent.BUTTON1)) {
				if (MouseListener.getY() >= 115) {
					this.place();
				}
			}
			
		}
	}
	
	@Override
	public void draw(Graphics2D g2D) {
		if (holdingObject != null) {
			Sprite sprite = this.holdingObject.getComponent(Sprite.class);
			if (sprite != null) {
				g2D.drawImage(sprite.image, (int)this.holdingObject.transform.position.x,
						(int)this.holdingObject.transform.position.y, sprite.width, sprite.height, null);
			}
		}
	}
	
	@Override
	public Component copy() {
		return null;
	}
	
}
