package net.therap.estaurant.entity;

import net.therap.estaurant.validator.CookingTimeGroup;
import net.therap.estaurant.validator.QuantityGroup;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Entity
@Table(name = "order_line_item")
@SQLDelete(sql = "UPDATE order_line_item SET access_status = 'DELETED' WHERE id = ? AND version = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "access_status <> 'DELETED' AND order_status <> 'SERVED' AND order_status <> 'CANCELED'")
@NamedQueries({
        @NamedQuery(name = "OrderLineItem.findAll", query = "SELECT o FROM OrderLineItem o"),
        @NamedQuery(name = "OrderLineItem.findActiveOrderByOrderId", query = "SELECT o FROM OrderLineItem o WHERE o.order.id = :orderId AND (orderStatus = 'PREPARED' OR orderStatus = 'PREPARING')"),
        @NamedQuery(name = "OrderLineItem.findNotServedByItemId", query = "SELECT o FROM OrderLineItem o WHERE o.item.id = :itemId AND o.orderStatus != 'SERVED'"),
        @NamedQuery(name = "OrderLineItem.findOrdersOnProcess", query = "SELECT o FROM OrderLineItem o WHERE (o.orderStatus = 'ORDERED' or o.orderStatus = 'PREPARING') and o.item in :itemList order by o.orderStatus")
})
public class OrderLineItem extends Persistent {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderLineSeq")
    @SequenceGenerator(name = "orderLineSeq", sequenceName = "order_line_seq", allocationSize = 1)
    private int id;

    @CreationTimestamp
    private Date acceptedAt;

    @Min(value = 0, message = "{input.number.estCookTime}", groups = CookingTimeGroup.class)
    @Max(value = 180, message = "{input.number.estCookTime}", groups = CookingTimeGroup.class)
    private int estCookingTime;

    @Min(value = 1, message = "{input.number.quantity}", groups = QuantityGroup.class)
    private int quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(optional = false)
    @JoinColumn(name = "itemId")
    private Item item;

    @ManyToOne(optional = false)
    @JoinColumn(name = "orderId")
    private Order order;

    public OrderLineItem() {
        this.quantity = 1;
    }

    public OrderLineItem(int id) {
        super();

        this.item = new Item(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(Date acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

    public int getEstCookingTime() {
        return estCookingTime;
    }

    public void setEstCookingTime(int estCookingTime) {
        this.estCookingTime = estCookingTime;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public boolean isNew() {
        return id == 0;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof OrderLineItem)) return false;
        OrderLineItem orderLineItem = (OrderLineItem) o;

        return getItem().getId() == orderLineItem.getItem().getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItem());
    }
}
