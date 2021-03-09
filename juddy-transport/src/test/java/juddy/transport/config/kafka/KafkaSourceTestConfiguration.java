package juddy.transport.config.kafka;

import juddy.transport.impl.config.StartConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({StartConfiguration.class, EmbeddedKafkaConfiguration.class})
public class KafkaSourceTestConfiguration {
}
