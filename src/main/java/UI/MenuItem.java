package UI;

import abc.GameObject;
import abc.MouseListener;
import abc.Prefabs;
import abc.Window;
import components.Component;
import components.Shoot;
import components.Sprite;
import components.Spritesheet;
import scenes.PlayScene;
import util.AssetPool;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MenuItem extends Component {
	public Sprite lightSprite, darkSprite;
	public Spritesheet itemSpritesheet1 = null;
	public Spritesheet itemSpritesheet2 = null;
	private int x, y , width, height;
	public boolean isSelected;
	public PlantType type;
	
	public MenuItem(int x, int y, int width, int height, Sprite lightSprite, Sprite darkSprite, PlantType type) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.lightSprite = lightSprite;
		this.darkSprite = darkSprite;
		this.isSelected = false;
		this.type = type;
	}
	
	@Override
	public void start() {
	
	}
	
	@Override
	public void update(double dt) {
		// check if the mouse is within this item
		if (!this.isSelected) {
			if (MouseListener.getX() > this.x && MouseListener.getX() <= (this.x + this.width)
					&& MouseListener.getY() > this.y + 30 && MouseListener.getY() <= (this.y + this.height + 30)) {
				// check if player click left button
				if (MouseListener.mouseButtonDown(MouseEvent.BUTTON1)) {
					GameObject newObject = null;
					switch (this.type) {
						case SUNFLOWER:
							newObject = Prefabs.generatePeashooter(this.itemSpritesheet1);
							break;
						case PEASHOOTER:
							newObject = Prefabs.generatePeashooter(this.itemSpritesheet1, this.itemSpritesheet2);
							newObject.getComponent(Shoot.class).flySpritesheet = AssetPool.getSpritesheet("assets/bullet/ProjectilePea.png");
							newObject.getComponent(Shoot.class).explode = AssetPool.getSpritesheet("assets/bullet/pea_splats.png");
							break;
						case SNOWPEASHOOTER:
							newObject = Prefabs.generatePeashooter(this.itemSpritesheet1, this.itemSpritesheet2);
							newObject.getComponent(Shoot.class).flySpritesheet = AssetPool.getSpritesheet("assets/bullet/ProjectileSnowPea.png");
							newObject.getComponent(Shoot.class).explode = AssetPool.getSpritesheet("assets/bullet/SnowPea_splats.png");
							break;
						default:
							System.out.println("Unknown plant type in MenuItem update!");
							break;
					}
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
