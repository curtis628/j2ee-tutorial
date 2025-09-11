package net.tcurt.j2eetutorial.api.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import net.tcurt.j2eetutorial.dto.EmployeeRequest;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmployeeApiTest {
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

    @Test
    public void testGetEmployeeById() {
        int employeeId = 1; // known from import.sql

        given()
                .when()
                .get("/employees/{id}", employeeId)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("employeeId", equalTo(employeeId))
                .body("firstName", equalTo("Alice"))
                .body("email", containsString("@")); // quick sanity check
    }

    @Test
    public void testGetEmployeesListGrowsAfterPost() {
        // 1. Initial list size
        List<?> beforeList = given()
                .when()
                .get("/employees")
                .then()
                .statusCode(200)
                .body("[0].firstName", equalTo("Alice"))
                .extract().jsonPath().getList("$");
        int beforeSize = beforeList.size();

        // 2. Create a new employee
        EmployeeRequest request = new EmployeeRequest();
        request.setFirstName("Bruce");
        request.setLastName("Wayne");
        request.setEmail("bruce.wayne@example.com");
        request.setHireDate("2022-09-15");
        request.setSalary(100000.0);
        request.setJobId(1);    // must exist
        request.setGradeId(2);  // must exist

        int newId = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/employees")
                .then()
                .statusCode(200)
                .body("firstName", equalTo("Bruce"))
                .body("employeeId", notNullValue())
                .extract().path("employeeId"); // capture ID

        // 3. Verify list grew
        List<?> afterList = given()
                .when()
                .get("/employees")
                .then()
                .statusCode(200)
                .extract().jsonPath().getList("$");
        int afterSize = afterList.size();

        assertEquals("List size should grow by 1 after POST", beforeSize + 1, afterSize);

        // 4. Delete newly created employee
        given()
                .when()
                .delete("/employees/{id}", newId)
                .then()
                .statusCode(204);

        // 5. TODO 404
    }
}
