package org.elsys.ip.fileshare;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class File {
    @Id
    public int id;

    @Column
    public String name;

}
