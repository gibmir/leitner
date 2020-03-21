package leitner.box.folder;

import leitner.box.Box;
import leitner.box.folder.card.Card;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "folder")
public class Folder {
    @Id
    @Column(name = "folder_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "box_id", nullable = false)
    private Box box;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "folder")
    private Set<Card> cards;

    public Folder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Folder folder = (Folder) o;
        return id.equals(folder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
