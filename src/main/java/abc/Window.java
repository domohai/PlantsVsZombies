package abc;
import util.Const;
import util.Time;
import javax.swing.JFrame;

enum SceneState {
	MENU, PLAY
}

public class Window extends JFrame implements Runnable {
	private static Window window = null;
	private boolean isRunning = false;
	private static Scene currentScene = null;
	
	private Window() {
		this.setSize(Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);
		this.setTitle(Const.SCREEN_TITLE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// add mouse and key listener
		this.addMouseListener(MouseListener.get());
		this.addMouseMotionListener(MouseListener.get());
		this.addKeyListener(KeyListener.get());
		
		this.setVisible(true);
		this.isRunning = true;
	}
	
	public static Window get() {
		if (Window.window == null) {
			Window.window = new Window();
		}
		return Window.window;
	}
	
	public static void changeScene(SceneState newScene) {
		switch (newScene) {
			case MENU:
				currentScene = new MenuScene();
				break;
			case PLAY:
				currentScene = new PlayScene();
				break;
			default:
				System.out.println("Not a valid scene" + newScene);
				System.exit(-1);
				break;
		}
		currentScene.init();
	}
	
	public void init() {
		changeScene(SceneState.PLAY);
	}
	
	public void update(double delta_time) {
		//System.out.println(1/delta_time + "fps");
		currentScene.update(delta_time);
	}
	
	@Override
	public void run() {
		double lastFrameTime = 0.0;
		double time = 0.0;
		double delta_time = 0.0;
		try {
			while (isRunning) {
				time = Time.getTime();
				delta_time = time - lastFrameTime;
				lastFrameTime = time;
				
				update(delta_time);
				
				Thread.sleep(16);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
