package net.tcurt.j2eetutorial.dto;

import lombok.Data;

@Data
public class EmployeeRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String hireDate; // as ISO string, parse later
    private double salary;
    private int jobId;
    private int gradeId;
}
