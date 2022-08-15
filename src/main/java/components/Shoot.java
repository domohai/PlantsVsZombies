package components;

import abc.*;
import util.Const;
import util.Vector2D;

public class Shoot extends Component {
	public boolean isShooting = false;
	public Spritesheet flySpritesheet, explode;
	public float debounceTime = 2.1f;
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
				Transform transform = new Transform(new Vector2D(this.gameObject.transform.position.x + Const.PLANT_WIDTH - 5,
						this.gameObject.transform.position.y + 12), new Vector2D());
				newBullet.transform = transform;
				newBullet.line = this.gameObject.line;
				newBullet.objectType = Type.BULLET;
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
