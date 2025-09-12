package net.tcurt.j2eetutorial.api.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(
        serviceName = "HelloService",
        portName = "HelloServicePort",
        targetNamespace = "http://soap.j2eetutorial.tcurt.net/"
)
public class HelloServiceEndpoint {

    @WebMethod
    public String sayHello(@WebParam(name = "name") String name) {
        return "Hello, " + name + " from SOAP!";
    }
}

