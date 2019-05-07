import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.util.Properties;

public class BaseTest {

    public static String BASE_URI;

    @BeforeAll
    private static void setPropertiesFromFile() {
        Properties prop = new Properties();
        try {
            prop.load(BaseTest.class.getClassLoader().getResourceAsStream("config.properties"));
            BASE_URI = prop.getProperty("host.url");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
