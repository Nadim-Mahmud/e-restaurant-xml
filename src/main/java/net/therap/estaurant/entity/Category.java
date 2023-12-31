package net.therap.estaurant.entity;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Entity
@Table(name = "category")
@SQLDelete(sql = "UPDATE category SET access_status = 'DELETED' WHERE id = ? AND version = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "access_status <> 'DELETED'")
@NamedQueries({
        @NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c"),
        @NamedQuery(name = "Category.findCategoryByName", query = "SELECT c FROM Category c WHERE c.id != :id AND c.name = :name")
})
public class Category extends Persistent {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categorySeq")
    @SequenceGenerator(name = "categorySeq", sequenceName = "category_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private int id;

    @NotNull(message = "{input.text}")
    @Size(min = 1, max = 50, message = "{input.text}")
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Item> itemList;

    public Category() {
        itemList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public boolean isNew() {
        return id == 0;
    }
}
