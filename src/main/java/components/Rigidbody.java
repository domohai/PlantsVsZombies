package components;

public class Rigidbody extends Component {
	public int HP;
	
	public void setValue(int HP) {
		this.HP = HP;
	}
	
	@Override
	public void update(double dt) {
		
	}
	
	@Override
	public Component copy() {
		Rigidbody copy = new Rigidbody();
		copy.setValue(this.HP);
		return copy;
	}
	
}
