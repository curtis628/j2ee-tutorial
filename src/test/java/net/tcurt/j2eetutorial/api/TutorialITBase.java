package net.tcurt.j2eetutorial.api;

import io.restassured.RestAssured;
import org.junit.BeforeClass;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assume.assumeTrue;

public class TutorialITBase {
    private static final String BASE_URI = "http://localhost";
    private static final int PORT = 8080;
    private static final String BASE_PATH = "/jboss-tutorial/api";

    private static boolean isServerAvailable() {
        try {
            URL url = new URL(String.format("%s:%d%s/employees", BASE_URI, PORT, BASE_PATH));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(1000);
            conn.setReadTimeout(1000);
            conn.setRequestMethod("GET");
            int code = conn.getResponseCode();
            return code >= 200 && code < 500; // consider "available" if it responds
        } catch (Exception e) {
            return false;
        }
    }

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = PORT;
        RestAssured.basePath = BASE_PATH;

        assumeTrue("WildFly not running, skipping all tests", isServerAvailable());
    }

}
