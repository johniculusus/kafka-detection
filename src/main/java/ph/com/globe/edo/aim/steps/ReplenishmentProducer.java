package ph.com.globe.edo.aim.steps;

import java.util.Properties;

import javax.enterprise.event.Observes;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.StartupEvent;

@Path("/addKafkaRecord")
public class ReplenishmentProducer {

	@ConfigProperty(name = "mp.messaging.outgoing.replenishment1.bootstrap.servers")
	public String bootstrapServers;

	@ConfigProperty(name = "mp.messaging.outgoing.replenishment1.topic")
	public String topic;          //="replenishment";

	@ConfigProperty(name = "mp.messaging.outgoing.replenishment1.value.serializer")
	public String paymentsTopicValueSerializer;

	@ConfigProperty(name = "mp.messaging.outgoing.replenishment1.key.serializer")
	public String paymentsTopicKeySerializer;

	private Producer<String, String> producer;

	public static final Logger log = LoggerFactory.getLogger(ReplenishmentProducer.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public void handleCloudEvent(String cloudEventJson) {

		log.info("TopupProducer Sending to kafka topic: " + topic + ", message: " + cloudEventJson);
		producer.send(new ProducerRecord<String, String>(topic, cloudEventJson));

	}

	public void init(@Observes StartupEvent ev) {
		
//		String keystore_dir = Parameters.getenv("KEYSTORE_DIR");
//		String securityProtocol = Parameters.getenv("SECURITY_PROTOCOL");
//		boolean isSSL = false;
//		if(null != securityProtocol && "SSL".equals(securityProtocol)){
//			isSSL = true;
//		}
		Properties props = new Properties();
		props.put("bootstrap.servers", bootstrapServers);
		props.put("value.serializer", paymentsTopicValueSerializer);
		props.put("key.serializer", paymentsTopicKeySerializer);
//		if(isSSL) {
//			props.put("security.protocol", "SSL");
//			props.put("ssl.truststore.location", keystore_dir + "/kafka.keystore.jks");
//			props.put("ssl.truststore.password", "changeme");	
//		}
		
		producer = new KafkaProducer<String, String>(props);
		
	}

}
