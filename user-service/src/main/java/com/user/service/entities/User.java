package com.user.service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;

    @Column(length = 20)
    private String username;

    private String email;
    private String about;

    /*
     * @Transient -> will ignore the field to store in database.  */
    @Transient
    private List<Rating> ratings = new ArrayList<Rating>();
}
