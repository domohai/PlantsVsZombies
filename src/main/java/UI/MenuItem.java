package UI;
import abc.GameObject;
import abc.MouseListener;
import abc.Prefabs;
import abc.Window;
import components.Component;
import components.Sprite;
import scenes.PlayScene;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class MenuItem extends Component {
	public Sprite lightSprite, darkSprite, itemSprite;
	private int x, y , width, height;
	public boolean isSelected;
	
	public MenuItem(int x, int y, int width, int height, Sprite lightSprite, Sprite darkSprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.lightSprite = lightSprite;
		this.darkSprite = darkSprite;
		this.isSelected = false;
	}
	
	@Override
	public void start() {
	
	}
	
	@Override
	public void update(double dt) {
		// check if the mouse is within this item
		if (!this.isSelected) {
			if (MouseListener.getX() > this.x && MouseListener.getX() <= (this.x + this.width)
					&& MouseListener.getY() > this.y && MouseListener.getY() <= (this.y + this.height)) {
				// check if player click left button
				if (MouseListener.mouseButtonDown(MouseEvent.BUTTON1)) {
					/*
					GameObject newObject = this.gameObject.copy();
					MenuItem menuItem = this.gameObject.getComponent(MenuItem.class);
					Sprite sprite = menuItem.itemSprite.copy();
					newObject.removeComponent(MenuItem.class);
					PlayScene scene = (PlayScene) Window.getScene();
					MouseControl mouseControl = scene.mouseCursor.getComponent(MouseControl.class);
					newObject.addComponent(mouseControl);
					newObject.removeComponent(Sprite.class);
					newObject.addComponent(sprite);
					scene.mouseCursor = newObject;
					*/
					GameObject newObject = Prefabs.generateSpriteObject(this.itemSprite, this.width, this.height);
					PlayScene scene = (PlayScene) Window.getScene();
					scene.mouseControl.pickUpObject(newObject);
					this.isSelected = true;
				}
			}
		}
		
	}
	
	@Override
	public void draw(Graphics2D g2D) {
		g2D.drawImage(this.lightSprite.image, this.x, this.y, this.width, this.height, null);
	}
	
	@Override
	public Component copy() {
		return null;
	}
}
