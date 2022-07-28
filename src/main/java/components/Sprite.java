package components;
import abc.Component;
import util.AssetPool;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

public class Sprite extends Component {
	
	public int width, height;
	public BufferedImage image;

	public Sprite(String file_path) {
		File file = new File(file_path);
		try {
			if (AssetPool.hasSprite(file.getAbsolutePath())) {
				throw new Exception("Sprite already exist!");
			}
			this.image = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("Cannot load image!");
			System.exit(-1);
		}
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
	}
	
	@Override
	public void draw(Graphics2D g2D) {
		g2D.drawImage(this.image, (int)this.gameObject.transform.position.x, (int)this.gameObject.transform.position.y, this.width, this.height, null);
	}

}
