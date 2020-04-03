package org.elsys.ip.fileshare;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import java.util.List;

@Entity
public class Directory extends File {
    @Column
    @OneToMany
    public List<Directory> directoryList;

    @Column
    @OneToMany
    public List<File> fileList;

}
