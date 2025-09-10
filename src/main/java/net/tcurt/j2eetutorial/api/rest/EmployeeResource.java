package net.tcurt.j2eetutorial.api.rest;

import net.tcurt.j2eetutorial.dto.EmployeeRequest;
import net.tcurt.j2eetutorial.dto.EmployeeResponse;
import net.tcurt.j2eetutorial.entity.Employee;
import net.tcurt.j2eetutorial.mapper.EmployeeMapper;
import net.tcurt.j2eetutorial.service.EmployeeService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
public class EmployeeResource {

    @Inject
    private EmployeeService employeeService;

    @GET
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.findAll().stream().map(EmployeeMapper::toResponse).collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public EmployeeResponse getEmployeeById(@PathParam("id") int id) {
        Employee employee =  employeeService.findById(id);
        return EmployeeMapper.toResponse(employee);
    }

    @POST
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        Employee employee = employeeService.create(request);
        return EmployeeMapper.toResponse(employee);
    }

    @PUT
    @Path("/{id}")
    public EmployeeResponse updateEmployee(@PathParam("id") int id, EmployeeRequest employee) {
        Employee updated = employeeService.update(id, employee);
        return EmployeeMapper.toResponse(updated);
    }

    @DELETE
    @Path("/{id}")
    public void deleteEmployee(@PathParam("id") int id) {
        employeeService.delete(id);
    }
}
