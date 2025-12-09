package org.example.taskservice.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "labels", indexes = {
    @Index(name = "idx_label_entity", columnList = "entity_type, entity_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    Integer id;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "color", nullable = false)
    String color;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type", nullable = false)
    LabelEntityType entityType;

    @Column(name = "entity_id", nullable = false)
    Integer entityId;
}
