package abc;

import util.AssetPool;

public class SpawnMachine {

	public static void spawnSun() {
		GameObject newSun = Prefabs.generateSun();
		Window.getScene().addGameObject(newSun);
	}
	
	public static void spawnZombie() {
		GameObject newZombie = Prefabs.generateZombie(AssetPool.getSpritesheet("assets/zombies/zombie_move.png"),
				AssetPool.getSpritesheet("assets/zombies/zomattack.png"));
		Window.getScene().addZombie(newZombie);
	}

}
