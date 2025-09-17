package net.tcurt.j2eetutorial.api.soap.exception;

import java.io.Serializable;

public class EmployeeNotFoundFault implements Serializable {
    private String message;
    private int employeeId;

    public EmployeeNotFoundFault() {}

    public EmployeeNotFoundFault(String message, int employeeId) {
        this.message = message;
        this.employeeId = employeeId;
    }

    public String getMessage() {
        return message;
    }

    public int getEmployeeId() {
        return employeeId;
    }
}
