package net.tcurt.j2eetutorial.api.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

import io.restassured.http.ContentType;
import java.util.List;
import net.tcurt.j2eetutorial.api.TutorialITBase;
import net.tcurt.j2eetutorial.dto.EmployeeRequest;
import org.junit.Test;

public class EmployeeResourceIT extends TutorialITBase {

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
    public void testGetEmployeeByBadIdGives404() {
        int employeeId = 9999; // employee id doesn't exist

        given()
                .when()
                .get("/employees/{id}", employeeId)
                .then()
                .statusCode(404)
                .contentType(ContentType.JSON)
                .body("error", equalTo("Employee not found with id: " + employeeId));
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
    }
}
