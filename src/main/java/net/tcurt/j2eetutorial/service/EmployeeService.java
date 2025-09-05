package net.tcurt.j2eetutorial.service;

import net.tcurt.j2eetutorial.entity.Employee;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class EmployeeService {

    @PersistenceContext
    private EntityManager em;

    public List<Employee> findAll() {
        return em.createQuery("SELECT e FROM Employee e", Employee.class)
                .getResultList();
    }

    public Employee findById(int id) {
        return em.find(Employee.class, id);
    }

    public Employee create(Employee employee) {
        em.persist(employee);
        return employee;
    }

    public Employee update(Employee employee) {
        return em.merge(employee);
    }

    public void delete(int id) {
        Employee e = em.find(Employee.class, id);
        if (e != null) {
            em.remove(e);
        }
    }
}
