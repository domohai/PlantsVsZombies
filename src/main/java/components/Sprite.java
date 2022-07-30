package components;
import abc.Component;
import util.AssetPool;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

public class Sprite extends Component {
	public int width = 0, height = 0;
	public String file_path = "";
	public boolean isSubImage = false;
	public transient BufferedImage image = null;
	
	public void loadSprite(String file_path) {
		File file = new File(file_path);
		this.file_path = file.getAbsolutePath();
		try {
			if (AssetPool.hasSprite(file.getAbsolutePath())) {
				throw new Exception("Sprite already exist!");
			}
			this.image = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cannot load image!");
		}
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
	}
	
	public void createSpriteFromImage(BufferedImage image) {
		this.image = image;
		this.isSubImage = true;
		this.width = image.getWidth();
		this.height = image.getHeight();
	}
	
	@Override
	public void draw(Graphics2D g2D) {
		g2D.drawImage(this.image, (int)this.gameObject.transform.position.x, (int)this.gameObject.transform.position.y, this.width, this.height, null);
	}

}
