package UI;
import abc.GameObject;
import abc.Transform;
import components.Component;
import components.Sprite;
import components.Spritesheet;
import util.AssetPool;
import util.Const;
import util.Vector2D;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class MainContainer extends Component {
	private List<GameObject> menuItems;
	private Sprite containerSprite;
	
	public MainContainer() {
		this.menuItems = new ArrayList<>();
		containerSprite = AssetPool.getSprite("assets/ui/SeedBank.png");
		init();
	}
	
	public void init() {
		Spritesheet buttonSprites = AssetPool.getSpritesheet("assets/ui/seedrow.png");
		Spritesheet itemSprites = AssetPool.getSpritesheet("assets/plants/sunflower.png");
		Spritesheet itemSprites1 = AssetPool.getSpritesheet("assets/plants/pea.png");
		Spritesheet itemSprites2 = AssetPool.getSpritesheet("assets/plants/snowpea.png");
		
		int col = 0;
		for (int i = 0; i < buttonSprites.sprites.size(); i++) {
			Sprite currentSprite = buttonSprites.sprites.get(i);
			
			int x = 130 + col * Const.BUTTON_WIDTH + col * 5;
			int y = 5;
			
			Transform transform = new Transform(new Vector2D(x, y), new Vector2D(1.0f, 1.0f));
			GameObject newObject = new GameObject("Item", Const.CONTAINER_ZINDEX);
			newObject.transform = transform;
			newObject.addComponent(currentSprite.copy());
			
			MenuItem menuItem = new MenuItem(x, y, Const.BUTTON_WIDTH, Const.BUTTON_HEIGHT, currentSprite, null);
			if (col == 0) menuItem.itemSpritesheet = itemSprites;
			else if (col == 1) menuItem.itemSpritesheet = itemSprites1;
			else if (col == 2) menuItem.itemSpritesheet = itemSprites2;
			newObject.addComponent(menuItem);
			menuItems.add(newObject);
			col++;
		}
	
	}
	
	@Override
	public void start() {
		for (GameObject g : this.menuItems) {
			g.start();
		}
	}
	
	@Override
	public void update(double dt) {
		for (GameObject g : this.menuItems) {
			g.update(dt);
		}
	}
	
	@Override
	public void draw(Graphics2D g2D) {
		g2D.drawImage(containerSprite.image, (int) this.gameObject.transform.position.x, (int) this.gameObject.transform.position.y,
				this.containerSprite.width, this.containerSprite.height, null);
		for (GameObject g : this.menuItems) {
			g.draw(g2D);
		}
	}
	
	@Override
	public Component copy() {
		return null;
	}
}
