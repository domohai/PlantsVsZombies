package abc;
import com.google.gson.*;
import java.lang.reflect.Type;

public class GameObjectDeserializer implements JsonDeserializer<GameObject> {
	@Override
	public GameObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		String name = jsonObject.get("name").getAsString();
		JsonArray components = jsonObject.getAsJsonArray("components");
		Transform transform = context.deserialize(jsonObject.get("transform"), Transform.class);
		int zIndex = context.deserialize(jsonObject.get("zIndex"), int.class);
		int line = context.deserialize(jsonObject.get("line"), int.class);
		abc.Type type = context.deserialize(jsonObject.get("objectType"), abc.Type.class);
		GameObject gameObject = new GameObject(name, transform, zIndex, type, line);
		for (JsonElement e : components) {
			Component c = context.deserialize(e, Component.class);
			gameObject.addComponent(c);
		}
		return gameObject;
	}
}
