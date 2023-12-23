package com.JavaTech.PointOfSales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;


@Entity
@Table(name="confirmationToken")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationToken {

    public long getTokenid() {
        return tokenid;
    }

    public void setTokenid(long tokenid) {
        this.tokenid = tokenid;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Date getCreatedDate() {
        return createdDate;
    }




    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id")
    private long tokenid;

    @Column(name="confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User userEntity;

    public ConfirmationToken(User userEntity) {
        this.userEntity = userEntity;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(User userEntity) {
        this.userEntity = userEntity;
    }

}
