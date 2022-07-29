package abc;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter implements java.awt.event.KeyListener {
	private static KeyListener keyListener = null;
	private boolean[] keyPressed;
	
	private KeyListener() {
		keyPressed = new boolean[350];
	}
	
	public static KeyListener get() {
		if (KeyListener.keyListener == null) {
			KeyListener.keyListener = new KeyListener();
		}
		return KeyListener.keyListener;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		keyPressed[e.getKeyCode()] = true;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		keyPressed[e.getKeyCode()] = false;
	}
	
	public boolean is_keyPressed(int keycode) {
		return keyPressed[keycode];
	}

}
