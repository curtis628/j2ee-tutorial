package net.tcurt.j2eetutorial;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class RestApplication extends Application {
    // no code needed — this activates JAX-RS in WildFly
}
