package UI;
import abc.GameObject;
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
	private Sprite containerSprite = null;
	
	public MainContainer() {
		this.menuItems = new ArrayList<>();
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
			int x = 100 + col * Const.BUTTON_WIDTH + col * 5;
			int y = 5;
			
			Vector2D vector2D = new Vector2D();
			vector2D.setValue(x, y);
			GameObject newObject = new GameObject("Item", 4);
			newObject.transform.setValue(vector2D);
			newObject.addComponent(currentSprite.copy());
			MenuItem menuItem = new MenuItem(x, y, Const.BUTTON_WIDTH, Const.BUTTON_HEIGHT, currentSprite, null);
			if (col == 0) menuItem.itemSprite = itemSprites.getSprite(0);
			else if (col == 1) menuItem.itemSprite = itemSprites1.getSprite(0);
			else if (col == 2) menuItem.itemSprite = itemSprites2.getSprite(0);
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
		for (GameObject g : this.menuItems) {
			g.draw(g2D);
		}
	}
	
	@Override
	public Component copy() {
		return null;
	}
}
