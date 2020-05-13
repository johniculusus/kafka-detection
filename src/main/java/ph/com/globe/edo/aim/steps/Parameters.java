package ph.com.globe.edo.aim.steps;

import java.util.Properties;

import io.vertx.core.json.JsonObject;

public class Parameters {

	private static final Properties props = new Properties();
	private static final JsonObject json;
	static {
		String parameters = System.getenv("PARAMETERS");
		json = new JsonObject(parameters);
	}
	
	public static String getenv(String key) {
		return json.getString(key);
	}
	
	
}
