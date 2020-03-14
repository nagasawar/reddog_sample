package reddog_sample.ignore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="rd_users")
public class RdUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    public int userId;

    @Column(name="login_id")
    public String loginId;

    @Column(name="password")
    public String password;

    @Column(name="user_name")
    public String userName;

    @Column(name="user_role_id")
    public int userRoleId;

    @ManyToOne
    @JoinColumn(name="user_role_id", referencedColumnName="user_role_id")
    public RdUserRole rdUserRole;
}
