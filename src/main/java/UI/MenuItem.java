package UI;

import abc.GameObject;
import abc.MouseListener;
import abc.Prefabs;
import abc.Window;
import components.*;
import components.Component;
import scenes.PlayScene;
import util.AssetPool;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MenuItem extends Component {
	public Sprite lightSprite, darkSprite;
	public Spritesheet itemSpritesheet1 = null;
	public Spritesheet itemSpritesheet2 = null;
	private int x, y , width, height, actualY, combineW, combineH, price;
	public PlantType type;
	public float debounceTime = 10.0f;
	public float debounceTimeLeft = 0.0f;
	
	public MenuItem(int x, int y, int width, int height, Sprite lightSprite, Sprite darkSprite,
					PlantType type, int price) {
		this.x = x;
		this.y = y;
		this.actualY = y + 30;
		this.combineH = y + 30 + height;
		this.combineW = x + width;
		this.width = width;
		this.height = height;
		this.lightSprite = lightSprite;
		this.darkSprite = darkSprite;
		this.type = type;
		this.price = price;
	}
	
	@Override
	public void start() {
	
	}
	
	@Override
	public void update(double dt) {
		// check if the mouse is within this item
		this.debounceTimeLeft -= dt;
		if (this.debounceTimeLeft <= 0.0f && SunBank.BALANCE >= this.price) {
			if (MouseListener.getX() > this.x && MouseListener.getX() <= (this.combineW)
				&& MouseListener.getY() > this.actualY && MouseListener.getY() <= (this.combineH)) {
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
					SunBank.BALANCE -= this.price;
					this.debounceTimeLeft = this.debounceTime;
				}
			}
		}
	}
	
	@Override
	public void draw(Graphics2D g2D) {
		if (this.debounceTimeLeft <= 0.0f) {
			g2D.drawImage(this.lightSprite.image, this.x, this.y, this.width, this.height, null);
		} else {
		
		}
	}
	
	@Override
	public Component copy() {
		return null;
	}
}
