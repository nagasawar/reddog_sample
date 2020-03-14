package reddog_sample.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import reddog_sample.service.SF;

@Entity
@Table(name="employees")
public class Employee {

    @Id
    @Column(name="employee_id")
    public String employeeId;

    @Column(name="fullname")
    public String fullname;

    @Column(name="kana")
    public String kana;

    @Column(name="maiden_name_flg")
    public boolean maidenNameFlg;

    @Column(name="gender")
    public String gender;

    @Temporal(TemporalType.DATE)
    @Column(name="birthday")
    public Date birthday;

    @Column(name="post_code")
    public String postCode;

    @Column(name="address1")
    public String address1;

    @Column(name="address2")
    public String address2;

    @Column(name="address3")
    public String address3;

    @Column(name="tel")
    public String tel;

    @Column(name="note")
    public String note;

    @Column(name="department_id")
    public String departmentId;

    @ManyToOne
    @JoinColumn(name="department_id", referencedColumnName="department_id")
    public Department department;

    public String getGenderName() {
        Common c = SF.common.getGender(this.gender);
        return (c == null)? "": c.cname;
    }
}
