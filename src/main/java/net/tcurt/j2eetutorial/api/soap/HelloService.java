package net.tcurt.j2eetutorial.api.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class HelloService {

    @WebMethod
    public String sayHello(@WebParam(name = "name") String name) {
        return "Hello, " + name + " from SOAP!";
    }
}

