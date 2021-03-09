package juddy.transport.impl.kafka.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import juddy.transport.impl.args.Message;
import juddy.transport.impl.common.json.ObjectMapperUtils;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

public class JsonDeserializer implements Deserializer<Message> {

    private final ObjectMapper objectMapper;

    public JsonDeserializer() {
        this(ObjectMapperUtils.createObjectMapper());
    }

    public JsonDeserializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Message deserialize(String topic, byte[] messageBytes) {
        return internalDeserialize(messageBytes);
    }

    @Override
    public Message deserialize(String topic, Headers headers, byte[] messageBytes) {
        return internalDeserialize(messageBytes);
    }

    private Message internalDeserialize(byte[] messageBytes) {
        try {
            return objectMapper.readValue(messageBytes, Message.class);
        } catch (Exception e) {
            throw new KafkaDeserializationException(e);
        }
    }
}
