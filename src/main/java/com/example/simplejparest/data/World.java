package com.example.simplejparest.data;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class World {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

}
