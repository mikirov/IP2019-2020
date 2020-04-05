package org.elsys.ip.fileshare;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Users {
    public Users() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Id
    public int id;

    @Column
    public String name;

    @Column
    public String password;

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Column
    public boolean enabled = false;
}
