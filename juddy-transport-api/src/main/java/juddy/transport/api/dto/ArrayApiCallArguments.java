package juddy.transport.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import juddy.transport.api.args.ApiCallArguments;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ArrayApiCallArguments implements ApiCallArguments {

    private Object[] values;

    @Override
    public Object getResult() {
        return values;
    }

    @Override
    @JsonIgnore
    public Type getArgsType() {
        return Type.ARRAY;
    }
}
