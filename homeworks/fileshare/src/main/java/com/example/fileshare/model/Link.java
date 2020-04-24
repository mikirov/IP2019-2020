package com.example.fileshare.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class Link {

    @Id
    public int id;

    @OneToOne
    @JoinColumn(name = "file_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public File file;

    @Column
    public String generatedName;

    public void setGeneratedName(String generatedName) {
        this.generatedName = generatedName;
    }

    public Link(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
