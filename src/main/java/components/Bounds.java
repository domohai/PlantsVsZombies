package components;
import abc.Component;

public class Bounds extends Component {
	public float width, height;
	
	public void setValue(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void update(double dt) {
	
	}
}
