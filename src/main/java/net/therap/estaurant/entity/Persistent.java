package net.therap.estaurant.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author nadimmahmud
 * @since 1/31/23
 */
@MappedSuperclass
public class Persistent implements Serializable {

    @Enumerated(EnumType.STRING)
    private AccessStatus accessStatus;

    @Version
    @Column(nullable = false)
    protected int version;

    @CreationTimestamp
    protected Date createdAt;

    @UpdateTimestamp
    protected Date updatedAt;

    public Persistent() {
        this.accessStatus = AccessStatus.ACTIVE;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public AccessStatus getAccessStatus() {
        return accessStatus;
    }

    public void setAccessStatus(AccessStatus accessStatus) {
        this.accessStatus = accessStatus;
    }

    @PreRemove
    private void preRemove() {
        this.accessStatus = AccessStatus.DELETED;
    }
}
