package net.tcurt.j2eetutorial.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employees")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(unique = true, length = 100)
    private String email;

    @Temporal(TemporalType.DATE)
    @Column(name = "hire_date", nullable = false)
    private Date hireDate;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    @JsonManagedReference // avoid backreference JSON cycle issues
    private Job job;

    @ManyToOne
    @JoinColumn(name = "grade_id", nullable = false)
    @JsonManagedReference
    private Grade grade;

    @Column(nullable = false)
    private double salary;
}
