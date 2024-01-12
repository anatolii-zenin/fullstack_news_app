package com.mjc.school.repository.model.implementation;

import com.mjc.school.repository.authentication.model.UserEntity;
import com.mjc.school.repository.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity(name = "author")
public class AuthorEntity implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    @OneToMany(mappedBy = "author", cascade = {CascadeType.REMOVE, CascadeType.REFRESH})
    private List<NewsEntity> news;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "author")
    private UserEntity user;

    @PrePersist
    public void prePersist() {
        createDate = LocalDateTime.now();
        lastUpdateDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdateDate = LocalDateTime.now();
    }
}
