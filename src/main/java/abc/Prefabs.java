package abc;
import components.Sprite;
import util.Const;
import util.Vector2D;

public class Prefabs {

	public static GameObject generateSpriteObject(Sprite sprite, int width, int height) {
		GameObject newObject = new GameObject("SpriteObject", new Transform(new Vector2D(), new Vector2D()), Const.MOUSE_ZINDEX, Type.PLANT, 1);
		newObject.addComponent(sprite.copy());
		return newObject;
	}
	
}
