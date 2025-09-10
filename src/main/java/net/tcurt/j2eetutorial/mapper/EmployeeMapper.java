package net.tcurt.j2eetutorial.mapper;

import net.tcurt.j2eetutorial.dto.EmployeeResponse;
import net.tcurt.j2eetutorial.entity.Employee;

public class EmployeeMapper {

    public static EmployeeResponse toResponse(Employee employee) {
        EmployeeResponse dto = new EmployeeResponse();
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setHireDate(employee.getHireDate().toString());
        dto.setSalary(employee.getSalary());

        if (employee.getJob() != null) {
            dto.setJobCode(employee.getJob().getJobCode());
            dto.setJobTitle(employee.getJob().getJobTitle());
        }

        if (employee.getGrade() != null) {
            dto.setGradeCode(employee.getGrade().getGradeCode());
            dto.setMinSalary(employee.getGrade().getMinSalary());
            dto.setMaxSalary(employee.getGrade().getMaxSalary());
        }

        return dto;
    }
}
