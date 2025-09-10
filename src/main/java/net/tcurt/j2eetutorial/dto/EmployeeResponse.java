package net.tcurt.j2eetutorial.dto;

import lombok.Data;

@Data
public class EmployeeResponse {
    private int employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String hireDate;
    private double salary;
    private String jobCode;
    private String jobTitle;
    private String gradeCode;
    private double minSalary;
    private double maxSalary;
}
