package juddy.transport.impl.source;

import juddy.transport.api.args.ArgsWrapper;
import juddy.transport.api.dto.StringApiCallArguments;
import juddy.transport.impl.args.ArgsWrapperImpl;
import juddy.transport.impl.common.ApiSerialilizer;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Path;
import java.util.function.Function;

public class JsonFileSource<T> extends FileSource {

    private final Class<T> objectClass;
    @Autowired
    @Getter(AccessLevel.PROTECTED)
    private ApiSerialilizer apiSerialilizer;

    public JsonFileSource(FileSourceProperties fileSourceProperties, Class<T> objectClass) {
        super(fileSourceProperties);
        this.objectClass = objectClass;
        withArgsConverter(getArgsConverter());
    }

    public JsonFileSource(String fileName, Class<T> objectClass) {
        super(fileName);
        this.objectClass = objectClass;
        withArgsConverter(getArgsConverter());
    }

    public JsonFileSource(Path filePath, Class<T> objectClass) {
        super(filePath);
        this.objectClass = objectClass;
        withArgsConverter(getArgsConverter());
    }

    public Function<ArgsWrapper, ArgsWrapper> getArgsConverter() {
        return argsWrapper -> ArgsWrapperImpl.of(apiSerialilizer.fromString(
                ((StringApiCallArguments) argsWrapper.getApiCallArguments()).getValue(),
                objectClass));
    }
}
