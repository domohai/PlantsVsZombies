package abc;

import components.*;
import util.AssetPool;
import util.Const;
import util.Vector2D;

public class Prefabs {
	public static GameObject generateSpriteObject(Sprite sprite) {
		GameObject newObject = new GameObject("SpriteObject", 0);
		newObject.addComponent(sprite.copy());
		return newObject;
	}
	public static GameObject generatePeashooter(Spritesheet idleSpritesheet, Spritesheet shootSpritesheet) {
		GameObject newPlant = new GameObject("newPlant", new Transform(new Vector2D(), new Vector2D()), Const.MOUSE_ZINDEX, Type.PLANT, 1);
		newPlant.addComponent(new Bounds(Const.PLANT_WIDTH, Const.PLANT_HEIGHT));
		newPlant.addComponent(new Rigidbody(Const.PLANT_HP));
		newPlant.addComponent(new Shoot());
		StateMachine stateMachine = new StateMachine();
		AnimationState idleState = new AnimationState(idleSpritesheet.file_path);
		idleState.title = "idle";
		AnimationState shootState = new AnimationState(shootSpritesheet.file_path);
		shootState.title = "shoot";
		for (int i = 0; i < idleSpritesheet.sprites.size(); i++) {
			idleState.addFrame(idleSpritesheet.getSprite(i).copy(), Const.DEFAULT_FRAME_TIME);
		}
		for (int i = 0; i < shootSpritesheet.sprites.size(); i++) {
			shootState.addFrame(shootSpritesheet.getSprite(i).copy(), Const.DEFAULT_FRAME_TIME);
		}
		idleState.setLoop(true);
		shootState.setLoop(true);
		stateMachine.addState(idleState);
		stateMachine.addState(shootState);
		stateMachine.addStateTrigger(idleState.title, shootState.title, "shoot");
		stateMachine.addStateTrigger(shootState.title, idleState.title, "stopShooting");
		stateMachine.setDefaultState(idleState.title);
		newPlant.addComponent(stateMachine);
		return newPlant;
	}
	
	public static GameObject generatePeashooter(Spritesheet spritesheet) {
		GameObject newPlant = new GameObject("newPlant", new Transform(new Vector2D(), new Vector2D()), Const.MOUSE_ZINDEX, Type.PLANT, 1);
		newPlant.addComponent(new Bounds(Const.PLANT_WIDTH, Const.PLANT_HEIGHT));
		newPlant.addComponent(new Rigidbody(Const.PLANT_HP));
		StateMachine stateMachine = new StateMachine();
		AnimationState idleState = new AnimationState(spritesheet.file_path);
		idleState.title = "idle";
		for (int i = 0; i < spritesheet.sprites.size(); i++) {
			idleState.addFrame(spritesheet.getSprite(i).copy(), Const.DEFAULT_FRAME_TIME);
		}
		idleState.setLoop(true);
		stateMachine.addState(idleState);
		stateMachine.setDefaultState(idleState.title);
		newPlant.addComponent(stateMachine);
		return newPlant;
	}
	
	public static GameObject generateZombie(Spritesheet walk, Spritesheet attack) {
		GameObject newZombie = new GameObject("new Zombie", new Transform(new Vector2D(820.0f, 0.0f), new Vector2D()),
				Const.ZOMBIE_ZINDEX, Type.ZOMBIE, RandomMachine.randomInt(1, 5));
		newZombie.addComponent(new Movement(new Vector2D(Const.ZOMBIE_SPEED, 0.0f)));
		newZombie.addComponent(new Bounds(Const.ZOMBIE_WIDTH, Const.ZOMBIE_HEIGHT));
		newZombie.addComponent(new Rigidbody(Const.ZOMBIE_HP));
		newZombie.addComponent(new Zombie());
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
	
	public static GameObject generateBullet(Spritesheet bullet, Spritesheet explode) {
		GameObject newBullet = new GameObject("bullet", Const.BULLET_ZINDEX);
		newBullet.addComponent(new Bounds(24, 24));
		newBullet.addComponent(new Movement(new Vector2D(Const.BULLET_SPEED, 0.0f)));
		newBullet.addComponent(new Bullet());
		StateMachine stateMachine = new StateMachine();
		AnimationState flying = new AnimationState(bullet.file_path);
		AnimationState exploding = new AnimationState(explode.file_path);
		flying.title = "fly";
		exploding.title = "explode";
		for (Sprite sprite : bullet.sprites) {
			flying.addFrame(sprite.copy(), Const.DEFAULT_FRAME_TIME);
		}
		for (Sprite sprite : explode.sprites) {
			exploding.addFrame(sprite.copy(), Const.DEFAULT_FRAME_TIME);
		}
		flying.setLoop(false);
		exploding.setLoop(false);
		stateMachine.addState(flying);
		stateMachine.addState(exploding);
		stateMachine.addStateTrigger(flying.title, exploding.title, "explode");
		stateMachine.setDefaultState(flying.title);
		newBullet.addComponent(stateMachine);
		return newBullet;
	}
	
	public static GameObject generateSun() {
		Spritesheet spritesheet = AssetPool.getSpritesheet("assets/sun/sun_small.png");
		GameObject newSun = new GameObject("Sun", Const.SUN_ZINDEX);
		Transform transform = new Transform(new Vector2D((float)((RandomMachine.randomDouble() * Const.SCREEN_WIDTH) - Const.SUN_WIDTH),
				-40.0f), new Vector2D());
		newSun.transform = transform;
		newSun.addComponent(new Sun());
		StateMachine stateMachine = new StateMachine();
		AnimationState state = new AnimationState(spritesheet.file_path);
		state.title = "idle";
		state.addFrame(spritesheet.getSprite(0).copy(), Const.DEFAULT_FRAME_TIME);
		state.setLoop(false);
		stateMachine.addState(state);
		stateMachine.setDefaultState(state.title);
		newSun.addComponent(stateMachine);
		return newSun;
	}
}
