package net.tcurt.j2eetutorial.api.soap;

import net.tcurt.j2eetutorial.api.soap.exception.EmployeeNotFoundException;
import net.tcurt.j2eetutorial.dto.EmployeeResponse;
import net.tcurt.j2eetutorial.entity.Employee;
import net.tcurt.j2eetutorial.service.EmployeeService;
import net.tcurt.j2eetutorial.mapper.EmployeeMapper;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;
import java.util.stream.Collectors;

@WebService(
        serviceName = "EmployeeService",   // Friendly name in the WSDL
        portName = "EmployeeServicePort",  // Optional: clean port name
        targetNamespace = "http://soap.j2eetutorial.tcurt.net/" // Custom namespace
)
public class EmployeeServiceEndpoint {

    @EJB
    private EmployeeService employeeService;

    @WebMethod
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.findAll().stream()
                .map(EmployeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @WebMethod
    public EmployeeResponse getEmployeeById(@WebParam(name = "id") int id)
            throws EmployeeNotFoundException {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id, id);
        }
        return EmployeeMapper.toResponse(employee);
    }
}
