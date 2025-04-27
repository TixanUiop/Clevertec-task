package org.evgeny.Utill;


import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;


import java.io.InputStream;
import java.util.Properties;

@Slf4j
@UtilityClass
public class GetProperties {

    private static Properties properties = new Properties();

    static {
        try {
            loadProperties();
            log.info("Loaded properties");
        }
        catch (Exception e) {
            log.error("Fail to load property file" ,e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private static void loadProperties() {

        InputStream resourceAsStream = GetProperties.class.getResourceAsStream("/application.properties");
        properties.load(resourceAsStream);
    }

    public static String getProperties(String key) {
        return properties.getProperty(key);
    }

}
