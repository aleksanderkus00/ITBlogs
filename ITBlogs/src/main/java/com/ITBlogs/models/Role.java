package com.ITBlogs.models;

import com.ITBlogs.models.Enum.ERole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idrole;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
}
