package reddog_sample.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="commons")
public class Common {

    @Id
    @Column(name="category")
    public String category;

    @Id
    @Column(name="no")
    public int no;

    @Column(name="cname")
    public String cname;

    @Column(name="value")
    public String value;
}
