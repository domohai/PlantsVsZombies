package abc;
import components.*;
import util.Const;
import util.Vector2D;

public class Prefabs {
	public static GameObject generateSpriteObject(Sprite sprite) {
		GameObject newObject = new GameObject("SpriteObject", 0);
		newObject.addComponent(sprite.copy());
		return newObject;
	}
	public static GameObject generatePeashooter(Spritesheet idle, Spritesheet shoot) {
		GameObject newPlant = new GameObject("newPlant", new Transform(new Vector2D(), new Vector2D()), Const.MOUSE_ZINDEX, Type.PLANT, 1);
		newPlant.addComponent(new Bounds(Const.PLANT_WIDTH, Const.PLANT_HEIGHT));
		newPlant.addComponent(new Rigidbody(Const.PLANT_HP));
		StateMachine stateMachine = new StateMachine();
		AnimationState idleState = new AnimationState(idle.file_path);
		idleState.title = "idle";
		if (shoot != null) {
			AnimationState shootState = new AnimationState(shoot.file_path);
			
			shootState.title = "shoot";
			for (int i = 0; i < idle.sprites.size(); i++) {
				idleState.addFrame(idle.getSprite(i).copy(), Const.DEFAULT_FRAME_TIME);
			}
			for (int i = 0; i < shoot.sprites.size(); i++) {
				shootState.addFrame(shoot.getSprite(i).copy(), Const.DEFAULT_FRAME_TIME);
			}
			idleState.setLoop(true);
			shootState.setLoop(true);
			stateMachine.addState(idleState);
			stateMachine.addState(shootState);
			stateMachine.addStateTrigger(idleState.title, shootState.title, "shoot");
			stateMachine.addStateTrigger(shootState.title, idleState.title, "stopShooting");
		} else {
			for (int i = 0; i < idle.sprites.size(); i++) {
				idleState.addFrame(idle.getSprite(i).copy(), Const.DEFAULT_FRAME_TIME);
			}
			idleState.setLoop(true);
			stateMachine.addState(idleState);
		}
		stateMachine.setDefaultState(idleState.title);
		newPlant.addComponent(stateMachine);
		return newPlant;
	}
	
	public static GameObject generateZombie(Spritesheet walk, Spritesheet attack) {
		GameObject newZombie = new GameObject("new Zombie", new Transform(new Vector2D(850.0f, 0.0f), new Vector2D()),
				Const.ZOMBIE_ZINDEX, Type.ZOMBIE, 3);
		newZombie.addComponent(new Movement(new Vector2D(Const.ZOMBIE_SPEED, 0.0f)));
		newZombie.addComponent(new Bounds(Const.ZOMBIE_WIDTH, Const.ZOMBIE_HEIGHT));
		newZombie.addComponent(new Rigidbody(Const.ZOMBIE_HP));
		StateMachine stateMachine = new StateMachine();
		AnimationState walkState = new AnimationState(walk.file_path);
		AnimationState attackState = new AnimationState(attack.file_path);
		walkState.title = "walk";
		attackState.title = "attack";
		for (int i = 0; i < walk.sprites.size(); i++) {
			walkState.addFrame(walk.getSprite(i).copy(), Const.DEFAULT_FRAME_TIME);
		}
		for (int i = 0; i < attack.sprites.size(); i++) {
			attackState.addFrame(attack.getSprite(i).copy(), Const.DEFAULT_FRAME_TIME);
		}
		walkState.setLoop(true);
		attackState.setLoop(true);
		stateMachine.addState(walkState);
		stateMachine.addState(attackState);
		stateMachine.addStateTrigger(attackState.title, walkState.title, "walk");
		stateMachine.addStateTrigger(walkState.title, attackState.title, "attack");
		stateMachine.setDefaultState(walkState.title);
		newZombie.addComponent(stateMachine);
		return newZombie;
	}
	
}
