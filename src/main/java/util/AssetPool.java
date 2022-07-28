package util;
import components.Sprite;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {
	private static Map<String, Sprite> sprites = new HashMap<>();
	
	public static Sprite getSprite(String file_path) {
		File file = new File(file_path);
		if (!AssetPool.sprites.containsKey(file.getAbsolutePath())) {
			Sprite sprite = new Sprite(file.getAbsolutePath());
			AssetPool.sprites.put(file.getAbsolutePath(), sprite);
		}
		return AssetPool.sprites.get(file.getAbsolutePath());
	}
	
	public static boolean hasSprite(String file_path) {
		File file = new File(file_path);
		return AssetPool.sprites.containsKey(file.getAbsolutePath());
	}

}
