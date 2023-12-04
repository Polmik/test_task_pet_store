package properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesResourceManager {
    private Properties properties;

    public PropertiesResourceManager() {
        this.properties = new Properties();
    }

    public PropertiesResourceManager(String resourceName) {
        this.properties = new Properties();
        this.properties = this.appendFromResource(this.properties, resourceName);
    }

    private Properties appendFromResource(Properties objProperties, String resourceName) {
        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);
        if (inStream != null) {
            try {
                objProperties.load(inStream);
                inStream.close();
            } catch (IOException var5) {
                System.out.println(var5.getMessage());
            }
        } else {
            System.out.println(String.format("Resource \"%1$s\" could not be found", resourceName));
        }

        return objProperties;
    }

    public String getProperty(String key) {
        return System.getProperty(key, this.properties.getProperty(key));
    }

    public String getProperty(String key, String defaultValue) {
        return System.getProperty(key, this.properties.getProperty(key, defaultValue));
    }
}
