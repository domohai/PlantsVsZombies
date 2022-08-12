package components;

public class Rigidbody extends Component {
	public int HP, damage;
	public boolean hit;
	
	public Rigidbody() {
		this.HP = 0;
		this.damage = 0;
		this.hit = false;
	}
	
	public Rigidbody(int HP) {
		this.HP = HP;
		this.damage = 0;
		this.hit = false;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	@Override
	public void update(double dt) {
		if (this.hit) {
			this.HP -= damage * dt;
			if (this.HP <= 0) {
				this.gameObject.isDead = true;
			}
		}
	}
	
	@Override
	public Component copy() {
		return new Rigidbody(this.HP);
	}
	
}
