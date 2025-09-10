package net.tcurt.j2eetutorial.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "jobs")
@Data
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private int jobId;

    @Column(name = "job_code", nullable = false, length = 10)
    private String jobCode;

    @Column(name = "job_title", nullable = false, length = 100)
    private String jobTitle;

    @OneToMany(mappedBy = "job")
    private List<Employee> employees;
}
