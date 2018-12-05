package phal.front.example.ecsite;


import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("webapi")
public class AppConfig extends ResourceConfig {

	public static final String DB_JNDI = "java:comp/env/jdbc/PostgreSQL";

	public AppConfig() {
		packages(true, this.getClass().getPackage().getName() + ".site.webapi");
	}

}
