package org.evgeny.Utill;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class GetPropertiesTest {


    @ParameterizedTest
    @CsvSource({
            "db.url, jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
            "db.username, sa"
            //"db.password, null"
    })
    void getProperties(String key, String value) {
        assertEquals(value, GetProperties.getProperties(key));
    }
}