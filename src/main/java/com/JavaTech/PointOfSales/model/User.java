package com.JavaTech.PointOfSales.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User implements Serializable {

    @Size(max = 50)
    @Email
    @Column(nullable = true, name = "email")
    private String email;

    @Column(nullable = true, name = "gender")
    private String gender;

    @NotBlank
    @Column(name = "username")
    private String username;

    @Size(max = 120)
    @Column(name = "password")
    private String password;

    @Column(name = "activated")
    private boolean activated;

    @Column( name = "unlocked")
    private boolean unlocked;

    @Column(name = "first_login")
    private boolean firstLogin = true;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonBackReference
    private Set<Role> roles = new HashSet<>();

    @Column(name = "verification_code", length = 255)
    private String verificationCode;

    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName = "branch_id")
    private Branch branch;

    public User(String username, String password, boolean activated, boolean unlock, String avatar, boolean firstLogin) {
        this.username = username;
        this.password = password;
        this.activated = activated;
        this.unlocked = unlock;
        this.avatar = avatar;
        this.firstLogin = firstLogin;
    }

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "avatar", columnDefinition = "TEXT")
    private String avatar;

    @Column(nullable = true, name = "full_name")
    private String fullName;

    @Column(nullable = true, name = "phone")
    private String phone;

}
