package au.org.massive.guacamole.auth;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.net.auth.Credentials;
import org.apache.guacamole.net.auth.simple.SimpleAuthenticationProvider;
import org.apache.guacamole.protocol.GuacamoleConfiguration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by simonyu on 5/03/15.
 */
public class CookiesAuthenticationProvider extends SimpleAuthenticationProvider {

    @Override
    public Map<String, GuacamoleConfiguration> getAuthorizedConfigurations(Credentials credentials) throws GuacamoleException {

        Map<String, GuacamoleConfiguration> configurations = new HashMap<String, GuacamoleConfiguration>();
        GuacamoleConfiguration configuration = new GuacamoleConfiguration();

        //get http servlet request from the Credentials object
        HttpServletRequest request = credentials.getRequest();
        Map<String,String> vncCredentials;
        Gson gson = new Gson();
        Cookie[] cookies = request.getCookies();
        
	//The null checking of the cookie below doesn't work
	//On the newer builds, somehow this always evaluate to null
	//And the desktop never displays

	//if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().startsWith("vnc-credentials")) {
                    //noinspection unchecked
                    try {
                        //noinspection unchecked
                        vncCredentials = gson.fromJson(URLDecoder.decode(c.getValue(), "utf-8"), Map.class);

                        configuration.setParameter("hostname", vncCredentials.get("hostname"));
                        configuration.setParameter("port", vncCredentials.get("port"));
                        configuration.setParameter("password", vncCredentials.get("password"));
                        configuration.setParameter("encodings", "ultra copyrect hextile zlib corre rre raw");
                        configuration.setParameter("color-depth", "16");
                        configuration.setProtocol(vncCredentials.get("protocol"));
                        configurations.put(vncCredentials.get("name"), configuration);

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JsonSyntaxException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        //}

        return configurations;
    }

    public String getIdentifier() {
        return "strudel-web-auth";
    }
}
