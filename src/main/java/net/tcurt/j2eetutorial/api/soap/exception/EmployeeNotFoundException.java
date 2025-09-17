package net.tcurt.j2eetutorial.api.soap.exception;

import javax.xml.ws.WebFault;

@WebFault(name = "EmployeeNotFoundFault",
        targetNamespace = "http://soap.j2eetutorial.tcurt.net/")
public class EmployeeNotFoundException extends Exception {
  private final EmployeeNotFoundFault faultInfo;

  public EmployeeNotFoundException(String message, int employeeId) {
    super(message);
    this.faultInfo = new EmployeeNotFoundFault(message, employeeId);
  }

  public EmployeeNotFoundFault getFaultInfo() {
    return faultInfo;
  }
}
