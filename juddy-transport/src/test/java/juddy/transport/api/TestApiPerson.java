package juddy.transport.api;

import juddy.transport.api.common.Api;
import lombok.Data;

@Api
public interface TestApiPerson {

    String getFio(Person person);

    @Data
    class Person {
        private String firstName;
        private String lastName;
        private String middleName;
    }
}
