package reddog_sample.ignore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rd_page_auth")
public class RdPageAuth {

    @Id
    @Column(name="page_auth_id")
    public String pageAuthId;

    @Column(name="path")
    public String path;

    @Column(name="page_name")
    public String pageName;

    @Column(name="login_check")
    public boolean loginCheck;

    @Column(name="roles")
    public String roles;
}
