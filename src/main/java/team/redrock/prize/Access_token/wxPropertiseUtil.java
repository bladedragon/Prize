package team.redrock.prize.Access_token;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class wxPropertiseUtil {

    private static Properties properties = new Properties();

    static {
        InputStream in = wxPropertiseUtil.class.getClassLoader().getResourceAsStream("wxconfig.properties");
        try {
            properties.load(in);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void UpdateProperty(String key, String value) {
        properties.setProperty(key, value);
    }
}
