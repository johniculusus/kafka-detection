package ph.com.globe.edo.aim.steps;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class ReplenishmentListener {

	private static final Logger LOG = LoggerFactory.getLogger(ReplenishmentListener.class);
	//private String topupProcessUrl = Parameters.getenv("TOPUP_PROCESS_URL");
	//private String authorization = Parameters.getenv("AUTHORIZATION");

	@Incoming("replenishment")
	public CompletionStage<Void> onMessage(KafkaMessage<String, String> message) throws IOException {

		LOG.info("Kafka order message with value = {} arrived from topic {} ", message.getPayload(),
				message.getTopic());

		JsonObject event = new JsonObject(message.getPayload());

		try {
			if (true) {
				LOG.info("Kafka message: " + message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return message.ack();
	}


	void onStart(@Observes StartupEvent ev) {
		
		LOG.info("Initializing from environment variables.");
		
		

	}

	void onStop(@Observes ShutdownEvent ev) {

	}
}
