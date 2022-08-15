package components;

import abc.GameObject;
import abc.Prefabs;
import abc.Transform;
import abc.Window;
import util.Vector2D;
import java.util.Timer;
import java.util.TimerTask;

public class SunFlower extends Component {
	public Timer timer = new Timer();
	public TimerTask timerTask = null;
	public float x, y;
	
	@Override
	public void start() {
		this.x = this.gameObject.transform.position.x + 10;
		this.y = this.gameObject.transform.position.y + 35;
		this.timerTask = new TimerTask() {
			@Override
			public void run() {
				GameObject newSun = Prefabs.generateSun();
				newSun.getComponent(Sun.class).applyGravity = false;
				Transform transform = new Transform(new Vector2D(x, y), new Vector2D());
				newSun.transform = transform;
				Window.getScene().addGameObject(newSun);
			}
		};
		this.timer.scheduleAtFixedRate(this.timerTask, 10000, 16000);
	}
	
	@Override
	public Component copy() {
		return null;
	}
}
