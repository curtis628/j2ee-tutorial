package net.tcurt.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "grades")
@Data
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private int gradeId;

    @Column(name = "grade_code", nullable = false, length = 10)
    private String gradeCode;

    @Column(name = "min_salary", nullable = false)
    private double minSalary;

    @Column(name = "max_salary", nullable = false)
    private double maxSalary;

    @OneToMany(mappedBy = "grade")
    private List<Employee> employees;
}
