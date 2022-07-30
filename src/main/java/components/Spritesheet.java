package components;
import util.AssetPool;
import java.util.ArrayList;
import java.util.List;

public class Spritesheet {
	private List<Sprite> sprites;
	public int tileW, tileH;
	
	public Spritesheet(String file_path, int tileW, int tileH, int columns, int size) {
		this.tileW = tileW;
		this.tileH = tileH;
		this.sprites = new ArrayList<>();
		Sprite parent = AssetPool.getSprite(file_path);
		int count = 0;
		while (count < size) {
			for (int col = 0; col < columns; col++) {
				int imgX = (col * tileW);
				int imgY = 0;
				Sprite sprite = new Sprite();
				sprite.createSpriteFromImage(parent.image.getSubimage(imgX, imgY, tileW, tileH));
				sprites.add(sprite);
				count++;
				if (count > size - 1) break;
			}
		}
		AssetPool.addSpritesheet(file_path, this);
	}
	
	public Sprite getSprite(int index) {
		return this.sprites.get(index);
	}
	
}
