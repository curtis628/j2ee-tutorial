package net.tcurt.j2eetutorial.api.soap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import net.tcurt.j2eetutorial.api.TutorialITBase;
import net.tcurt.j2eetutorial.client.soap.EmployeeNotFoundException;
import net.tcurt.j2eetutorial.client.soap.EmployeeResponse;
import net.tcurt.j2eetutorial.client.soap.EmployeeService;
import net.tcurt.j2eetutorial.client.soap.EmployeeServiceEndpoint;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class EmployeeServiceEndpointIT extends TutorialITBase {

    private static final String ENDPOINT_URL = "http://localhost:8080/jboss-tutorial/EmployeeService";
    private static EmployeeServiceEndpoint clientEndpoint;


    @BeforeClass
    public static void setupSoapClient() {
        // This runs AFTER TutorialITBase.setup()
        // If WildFly isn’t available, this won’t run at all.
        EmployeeService service = new EmployeeService();
        clientEndpoint = service.getEmployeeServicePort();
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        int employeeId = 1; // known from import.sql
        EmployeeResponse response = clientEndpoint.getEmployeeById(employeeId);

        assertThat(response).isNotNull();
        assertThat(response.getEmployeeId()).isEqualTo(employeeId);
        assertThat(response.getFirstName()).isEqualTo("Alice");
        assertThat(response.getEmail()).contains("@");
    }

    @Test
    public void testGetEmployeeByBadIdThrowsSoapFault() {
        int badId = 9999;

        assertThatThrownBy(() -> clientEndpoint.getEmployeeById(badId))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessageContaining("Employee not found");
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        List<EmployeeResponse> employees = clientEndpoint.getAllEmployees();

        assertThat(employees).hasSizeGreaterThan(0);
        assertThat(employees).anySatisfy(employee -> {
            assertThat(employee.getEmployeeId()).isEqualTo(1);
            assertThat(employee.getFirstName()).isEqualTo("Alice");
        });
    }


}
