package abc;
import components.*;
import util.Const;
import util.Vector2D;

public class Prefabs {
	public static GameObject generatePlant(Spritesheet spritesheet) {
		GameObject newPlant = new GameObject("SpriteObject", new Transform(new Vector2D(), new Vector2D()), Const.MOUSE_ZINDEX, Type.PLANT, 1);
		newPlant.addComponent(new Bounds(Const.PLANT_WIDTH, Const.PLANT_HEIGHT));
		newPlant.addComponent(new Rigidbody(Const.PLANT_HP));
		AnimationState idleState = new AnimationState(spritesheet.file_path);
		idleState.title = "idle";
		//float defaultFrameTime = 0.18f;
		for (int i = 0; i < spritesheet.sprites.size(); i++) {
			idleState.addFrame(spritesheet.getSprite(i), Const.DEFAULT_FRAME_TIME);
		}
		idleState.setLoop(true);
		StateMachine stateMachine = new StateMachine();
		stateMachine.addState(idleState);
		stateMachine.setDefaultState(idleState.title);
		newPlant.addComponent(stateMachine);
		return newPlant;
	}
	
	public static GameObject generateZombie(Sprite sprite) {
		GameObject newZombie = new GameObject("new Zombie", new Transform(new Vector2D(850.0f, 0.0f), new Vector2D()),
				Const.ZOMBIE_ZINDEX, Type.ZOMBIE, 1);
		newZombie.addComponent(sprite.copy());
		newZombie.addComponent(new Movement(new Vector2D(Const.ZOMBIE_SPEED, 0.0f)));
		newZombie.addComponent(new Bounds(Const.ZOMBIE_WIDTH, Const.ZOMBIE_HEIGHT));
		newZombie.addComponent(new Rigidbody(Const.ZOMBIE_HP));
		return newZombie;
	}
	
}
