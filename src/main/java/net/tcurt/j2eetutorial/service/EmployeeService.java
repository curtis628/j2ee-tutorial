package net.tcurt.j2eetutorial.service;

import net.tcurt.j2eetutorial.dto.EmployeeRequest;
import net.tcurt.j2eetutorial.entity.Employee;
import net.tcurt.j2eetutorial.entity.Grade;
import net.tcurt.j2eetutorial.entity.Job;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless // TODO: learn what this is.
public class EmployeeService {

    @PersistenceContext
    private EntityManager em; // TODO: learn what this is.

    // ---------- CREATE ----------
    public Employee create(EmployeeRequest request) {
        Job job = em.find(Job.class, request.getJobId());
        Grade grade = em.find(Grade.class, request.getGradeId());
        if (job == null || grade == null) {
            throw new IllegalArgumentException("Invalid jobId or gradeId");
        }

        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setSalary(request.getSalary());
        employee.setHireDate(java.sql.Date.valueOf(request.getHireDate()));
        employee.setJob(job);
        employee.setGrade(grade);

        em.persist(employee);
        return employee;
    }

    // ---------- READ ----------
    public List<Employee> findAll() {
        return em.createQuery(
                        "SELECT e FROM Employee e " +
                                "JOIN FETCH e.job " +
                                "JOIN FETCH e.grade", Employee.class)
                .getResultList();
    }

    public Employee findById(int id) {
        List<Employee> result = em.createQuery(
                        "SELECT e FROM Employee e " +
                                "JOIN FETCH e.job " +
                                "JOIN FETCH e.grade " +
                                "WHERE e.employeeId = :id", Employee.class)
                .setParameter("id", id)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    // ---------- UPDATE ----------
    public Employee update(int id, EmployeeRequest request) {
        Employee employee = em.find(Employee.class, id);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found with id: " + id);
        }

        Job job = em.find(Job.class, request.getJobId());
        Grade grade = em.find(Grade.class, request.getGradeId());
        if (job == null || grade == null) {
            throw new IllegalArgumentException("Invalid jobId or gradeId");
        }

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setSalary(request.getSalary());
        employee.setHireDate(java.sql.Date.valueOf(request.getHireDate()));
        employee.setJob(job);
        employee.setGrade(grade);

        return em.merge(employee);
    }

    // ---------- DELETE ----------
    public void delete(int id) {
        Employee employee = em.find(Employee.class, id);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found with id: " + id);
        }
        em.remove(employee);
    }
}
