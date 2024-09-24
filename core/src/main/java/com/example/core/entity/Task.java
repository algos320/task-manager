package com.example.core.entity;

import com.example.core.model.TaskStatus;
import com.example.core.usertype.LocalDateTimeType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Type(LocalDateTimeType.class)
    private LocalDateTime createdAt;

    @Type(LocalDateTimeType.class)
    private LocalDateTime updatedAt;
}
