package com.hamitmizrak.security.jwt;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

// LOMBOK
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

// ENTITY
@Entity(name="UsersEntities")
@Table(name="usersEntities")
public class _4_UserEntity {

    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;


    // USERNAME
    @Column(nullable = false)
    private String username;

    // PASSWORD
    @Column(nullable = false)
    private String password;

    // ROLE
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private _3_Role role;

    // Default olarak Enum "user" olsun
    @PrePersist
    public void setDefaultRole(){
        if(this.role==null){
            //this.role=_3_Role.values()[0]; // 1.YOL
            this.role=_3_Role.USER; // 2.YOL
        }
    }

    // DATE
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

}
