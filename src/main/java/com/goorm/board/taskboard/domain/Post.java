package com.goorm.board.taskboard.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id @GeneratedValue
    private Long id;

    private String title;
    private String content;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comment = new ArrayList<>();


    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;


    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @Column(name = "is_deleted")
    @Setter
    private Boolean softDeleted = false;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
