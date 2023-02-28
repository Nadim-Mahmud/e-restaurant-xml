package net.therap.estaurant.entity;

import net.therap.estaurant.util.Encryption;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Entity
@Table(name = "user")
@SQLDelete(sql = "UPDATE user SET access_status = 'DELETED' WHERE id = ? AND version = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "access_status <> 'DELETED'")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
        @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE email= :email"),
        @NamedQuery(name = "User.isDuplicateEmail", query = "SELECT u FROM User u WHERE email = :email AND id != :id"),
        @NamedQuery(name = "User.findChef", query = "SELECT u FROM User u WHERE u.type = 'CHEF'"),
        @NamedQuery(name = "User.findWaiter", query = "SELECT u FROM User u WHERE u.type = 'WAITER'")
})
public class User extends Persistent {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
    @SequenceGenerator(name = "userSeq", sequenceName = "user_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private int id;

    @NotNull(message = "{input.text}")
    @Size(min = 1, max = 45, message = "{input.text}")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "{input.text}")
    @Size(min = 1, max = 45, message = "{input.text}")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "{input.date}")
    @Column(name = "date_of_birth", nullable = false, updatable = false)
    private Date dateOfBirth;

    @Size(min = 1, max = 45, message = "{input.email}")
    @Email(message = "{input.email}")
    @Column(name = "email")
    private String email;

    @NotNull(message = "{input.text}")
    @Size(min = 1, max = 45, message = "{input.text}")
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private UserType type;

    @NotNull(message = "{input.date}")
    @Column(name = "joining_date")
    private Date joiningDate;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<RestaurantTable> restaurantTableList;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "chef_item",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id")}
    )
    private List<Item> itemList;

    public User() {
        itemList = new ArrayList<>();
        restaurantTableList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.password = Encryption.getPBKDF2(password);
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public List<RestaurantTable> getRestaurantTableList() {
        return restaurantTableList;
    }

    public void setRestaurantTableList(List<RestaurantTable> restaurantTableList) {
        this.restaurantTableList = restaurantTableList;
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
