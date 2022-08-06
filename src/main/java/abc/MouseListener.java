package abc;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListener extends MouseAdapter implements java.awt.event.MouseListener {
	private static MouseListener mouseListener = null;
	public boolean mousePressed;
	public boolean mouseDragged;
	public boolean[] mouseButtonPressed;
	private float x, y;
	private float dx, dy;
	
	private MouseListener() {
		this.mousePressed = false;
		this.mouseDragged = false;
		this.mouseButtonPressed = new boolean[9];
		this.x = 0;
		this.y = 0;
		this.dx = 0;
		this.dy = 0;
	}
	
	public static MouseListener get() {
		if (MouseListener.mouseListener == null) {
			MouseListener.mouseListener = new MouseListener();
		}
		return MouseListener.mouseListener;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		this.mousePressed = true;
		this.mouseButtonPressed[e.getButton()] = true;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		this.mousePressed = false;
		this.mouseDragged = false;
		this.mouseButtonPressed[e.getButton()] = false;
		this.dx = 0.0f;
		this.dy = 0.0f;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		this.mouseDragged = true;
		this.mouseButtonPressed[e.getButton()] = true;
		this.dx = e.getX() - this.x;
		this.dy = e.getY() - this.y;
	}
	
	public static boolean mouseButtonDown(int mouseButton) {
		return get().mouseButtonPressed[mouseButton];
	}

	public static float getX() {
		return get().x;
	}
	
	public static float getY() {
		return get().y;
	}
	
	public static float getDx() {
		return get().dx;
	}
	
	public static float getDy() {
		return get().dy;
	}
	
	public static boolean isDragging() {
		return get().mouseDragged;
	}
	
	

}
