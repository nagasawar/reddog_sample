package reddog_sample.ignore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rd_user_roles")
public class RdUserRole {

    @Id
    @Column(name="user_role_id")
    public String userRoleId;

    @Column(name="user_role_name")
    public String userRoleName;
}
