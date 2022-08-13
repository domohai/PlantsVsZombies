package components;

import abc.GameObject;
import abc.Prefabs;
import abc.Transform;
import abc.Window;
import util.Const;
import util.Vector2D;

public class Shoot extends Component {
	public boolean isShooting = false;
	public Spritesheet flySpritesheet, explode;
	public float debounceTime = 2.5f;
	public float debounceTimeLeft = 0.0f;
	
	public Shoot() {
		this.flySpritesheet = null;
		this.explode = null;
	}
	
	public Shoot(Spritesheet bulletSpritesheet, Spritesheet explode) {
		this.flySpritesheet = bulletSpritesheet;
		this.explode = explode;
		
	}
	
	@Override
	public void update(double dt) {
		if (this.isShooting) {
			this.debounceTimeLeft -= dt;
			this.gameObject.getComponent(StateMachine.class).trigger("shoot");
			if (this.debounceTimeLeft <= 0.0f) {
				GameObject newBullet = Prefabs.generateBullet(this.flySpritesheet, this.explode);
				Transform transform = new Transform(new Vector2D(this.gameObject.transform.position.x + Const.PLANT_WIDTH, this.gameObject.transform.position.y + 15),
						new Vector2D());
				newBullet.transform = transform;
				newBullet.line = this.gameObject.line;
				Window.getScene().addBullet(newBullet);
				this.debounceTimeLeft = this.debounceTime;
			}
		} else {
			this.gameObject.getComponent(StateMachine.class).trigger("stopShooting");
		}
	}
	
	@Override
	public Component copy() {
		return null;
	}
}
