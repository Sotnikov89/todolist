package ru.job4j.todolist.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private LocalDateTime created;
    private boolean done;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    private User user;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany (fetch = FetchType.EAGER)
    private List<Category> categories = new ArrayList<>();
}
