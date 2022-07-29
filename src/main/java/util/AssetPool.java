package util;
import components.Sprite;
import components.Spritesheet;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {
	private static Map<String, Sprite> sprites = new HashMap<>();
	private static Map<String, Spritesheet> spritesheets = new HashMap<>();
	
	public static Sprite getSprite(String file_path) {
		File file = new File(file_path);
		if (!AssetPool.sprites.containsKey(file.getAbsolutePath())) {
			Sprite sprite = new Sprite(file.getAbsolutePath());
			AssetPool.sprites.put(file.getAbsolutePath(), sprite);
		}
		return AssetPool.sprites.get(file.getAbsolutePath());
	}
	
	public static Spritesheet getSpritesheet(String file_path) {
		File file = new File(file_path);
		if (!AssetPool.spritesheets.containsKey(file.getAbsolutePath())) {
			System.out.println("Spritesheet has not been added yet!");
			System.exit(-1);
		}
		return AssetPool.spritesheets.get(file.getAbsolutePath());
	}
	
	public static void addSpritesheet(String file_path, Spritesheet newSpritesheet) {
		File file = new File(file_path);
		if (!AssetPool.spritesheets.containsKey(file.getAbsolutePath())) {
			AssetPool.spritesheets.put(file.getAbsolutePath(), newSpritesheet);
		}
	}
	
	public static boolean hasSprite(String file_path) {
		File file = new File(file_path);
		return AssetPool.sprites.containsKey(file.getAbsolutePath());
	}

}
