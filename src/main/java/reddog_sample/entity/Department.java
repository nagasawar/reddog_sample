package reddog_sample.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="departments")
public class Department {

    @Id
    @Column(name="department_id")
    public String departmentId;

    @Column(name="department_name")
    public String departmentName;

    @OneToMany(mappedBy="department")
    public List<Employee> employees;
}
