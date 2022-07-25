package abc;
import util.Const;
import util.Time;

import javax.swing.JFrame;

public class Window extends JFrame implements Runnable {
	private static Window window = null;
	private boolean isRunning = false;
	
	private Window() {
		this.setSize(Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);
		this.setTitle(Const.SCREEN_TITLE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.isRunning = true;
	}
	
	public static Window get() {
		if (Window.window == null) {
			Window.window = new Window();
		}
		return Window.window;
	}
	
	public void init() {
	
	}
	
	public void update(double delta_time) {
	
	}
	
	@Override
	public void run() {
		double lastFrameTime = 0.0;
		try {
			while (isRunning) {
				double time = Time.getTime();
				double delta_time = time - lastFrameTime;
				lastFrameTime = time;
				update(delta_time);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
