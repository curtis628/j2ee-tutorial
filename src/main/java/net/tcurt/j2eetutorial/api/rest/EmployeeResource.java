package net.tcurt.j2eetutorial.api.rest;

import net.tcurt.j2eetutorial.entity.Employee;
import net.tcurt.j2eetutorial.service.EmployeeService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class EmployeeResource {

    @Inject
    private EmployeeService employeeService;

    @GET
    public List<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    @GET
    @Path("/{id}")
    public Employee getEmployeeById(@PathParam("id") int id) {
        return employeeService.findById(id);
    }

    @POST
    public Employee createEmployee(Employee employee) {
        return employeeService.create(employee);
    }

    @PUT
    @Path("/{id}")
    public Employee updateEmployee(@PathParam("id") int id, Employee employee) {
        employee.setEmployeeId(id); // enforce ID
        return employeeService.update(employee);
    }

    @DELETE
    @Path("/{id}")
    public void deleteEmployee(@PathParam("id") int id) {
        employeeService.delete(id);
    }
}
