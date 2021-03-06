package juddy.transport.api;

import juddy.transport.api.common.ApiBean;

@ApiBean(TestApiPersonFio.class)
public class TestApiPersonFioServer implements TestApiPersonFio {

    private static final char SPACE_DELIMITER = ' ';

    @Override
    public String getFio(String firstName, String lastName) {
        return firstName + SPACE_DELIMITER + lastName;
    }
}
