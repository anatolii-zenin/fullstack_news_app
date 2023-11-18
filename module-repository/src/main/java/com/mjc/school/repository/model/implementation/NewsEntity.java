package com.mjc.school.repository.model.implementation;

import com.mjc.school.repository.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity(name = "news")
public class NewsEntity implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 355)
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private AuthorEntity author;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
        name = "news_tags",
        joinColumns = @JoinColumn(name = "news_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private List<TagEntity> tags;
    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private List<CommentEntity> comments;

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
