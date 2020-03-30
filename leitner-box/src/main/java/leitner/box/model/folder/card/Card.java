package leitner.box.model.folder.card;

import leitner.box.model.folder.Folder;

import javax.persistence.*;

@Entity
@Table(name = "card")
public class Card {
    @Id
    @Column(name = "card_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }
}
