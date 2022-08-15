package abc;

public class SpawnMachine {

	public void spawnSun() {
		GameObject newSun = Prefabs.generateSun();
		Window.getScene().addGameObject(newSun);
	}


}
