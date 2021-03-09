package juddy.transport.impl.kafka.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import juddy.transport.impl.args.Message;
import juddy.transport.impl.common.json.ObjectMapperUtils;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

public class JsonSerializer implements Serializer<Message> {

    private final ObjectMapper objectMapper;

    public JsonSerializer() {
        this(ObjectMapperUtils.createObjectMapper());
    }

    public JsonSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public byte[] serialize(String topic, Message message) {
        return internalSerialize(message);
    }

    @Override
    public byte[] serialize(String topic, Headers headers, Message message) {
        return internalSerialize(message);
    }

    private byte[] internalSerialize(Message message) {
        try {
            return objectMapper.writeValueAsBytes(message);
        } catch (Exception e) {
            throw new KafkaSerializationException(e);
        }
    }
}
