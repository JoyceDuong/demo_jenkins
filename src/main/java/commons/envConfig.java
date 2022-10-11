package commons;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.*;
import org.aeonbits.owner.Config.Key;

@Sources({"classpath:${env}.properties"})
public interface envConfig  extends Config {

    @Key("app.url")
    String appUrl();

    @Key("app.username")
    String appUserName();

    @Key("app.password")
    String appPassword();

}
