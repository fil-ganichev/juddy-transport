package juddy.transport.impl.kafka;

import juddy.transport.api.TestApi;
import juddy.transport.api.args.ArgsWrapper;
import juddy.transport.api.args.CallInfo;
import juddy.transport.config.kafka.KafkaSourceTestConfiguration;
import juddy.transport.impl.args.Message;
import juddy.transport.impl.common.ApiSerializer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.Duration;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(KafkaSourceTestConfiguration.class)
@EmbeddedKafka(
        partitions = 1,
        controlledShutdown = true,
        topics = "topic-test",
        brokerProperties = {
                "listeners=PLAINTEXT://localhost:3333",
                "port=3333"
        })
class KafkaSourceTest {

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;
    @Autowired
    private KafkaConsumer<String, Message> kafkaConsumer;
    @Autowired
    private ApiSerializer apiSerializer;

    @Test
    void simpleKafkaTest() {
        kafkaConsumer.subscribe(Collections.singletonList("topic-test"));
        kafkaTemplate.send("topic-test", createMessage());
        ConsumerRecords<String, Message> records = kafkaConsumer.poll(Duration.ofSeconds(1));
        assertThat(records.count()).isEqualTo(1);
        assertThat(records.iterator().next().value()).isEqualTo(createMessage());
    }

    private Message createMessage() {
        ArgsWrapper argsWrapper = ArgsWrapper.of("test message")
                .withCorrelationId("1322ab78-abd6-4015-b59d-230ffabe4817");
        argsWrapper.setCallInfo(CallInfo.<TestApi>builder()
                .apiClass(TestApi.class)
                .apiMethod(TestApi.class.getMethods()[0])
                .build());
        return apiSerializer.messageFromArgs(argsWrapper);
    }
}
