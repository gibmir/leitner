package leitner.box.model;

import leitner.box.model.folder.Folder;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "box")
public class Box {
    @Id
    @Column(name = "box_id")
    private Long id;
    @Column(name = "box_title")
    private String title;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "box")
    private Set<Folder> folders;

    public Box() {
    }

    public Box(Long id, String title, Set<Folder> folders) {
        this.id = id;
        this.title = title;
        this.folders = folders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Folder> getFolders() {
        return folders;
    }

    public void setFolders(Set<Folder> folders) {
        this.folders = folders;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Box{" +
                "id=" + id +
                ", categories=" + folders +
                '}';
    }
}

