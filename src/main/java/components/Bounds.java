package components;

public class Bounds extends Component {
	public float width, height;
	
	public void setValue(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void update(double dt) {
	
	}
	
	@Override
	public Component copy() {
		Bounds copy = new Bounds();
		copy.setValue(this.width, this.height);
		return copy;
	}
}
