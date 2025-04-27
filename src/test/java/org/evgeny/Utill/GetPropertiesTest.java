package org.evgeny.Utill;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class GetPropertiesTest {


    @ParameterizedTest
    @CsvSource({
            "db.url, jdbc:postgresql://localhost:5432/clevertec_repository",
            "db.username, postgres",
            "db.password, postgres"
    })
    void getProperties(String key, String value) {
        assertEquals(value, GetProperties.getProperties(key));
    }
}