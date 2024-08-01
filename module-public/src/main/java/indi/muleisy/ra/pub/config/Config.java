package indi.muleisy.ra.pub.config;

import indi.muleisy.ra.pub.netty.utlis.Serializer;
import lombok.Getter;

import java.io.InputStream;
import java.util.Properties;

@Getter
public class Config {

    public static final Config INSTANCE = new Config("config.properties");

    private Serializer defaultSerializer = Serializer.DEFAULT;

    private String mongoUri;

    private String configDBName;

    private String appid;

    public Config(String configFileName) {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(configFileName)) {
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find " + configFileName);
                return;
            }
            prop.load(input);

            this.mongoUri = prop.getProperty("mongodb.uri");
            this.configDBName = prop.getProperty("mongodb.database");
            this.appid = prop.getProperty("oath2.appid ");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
