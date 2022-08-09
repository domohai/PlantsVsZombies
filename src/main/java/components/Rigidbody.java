package components;

public class Rigidbody extends Component {
	public int HP;
	
	public Rigidbody() {
		this.HP = 0;
	}
	
	public Rigidbody(int HP) {
		this.HP = HP;
	}
	
	@Override
	public void update(double dt) {
		
	}
	
	@Override
	public Component copy() {
		return new Rigidbody(this.HP);
	}
	
}
