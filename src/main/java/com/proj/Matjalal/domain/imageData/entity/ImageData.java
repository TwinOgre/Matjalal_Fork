package com.proj.Matjalal.domain.imageData.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proj.Matjalal.domain.article.entity.Article;
import com.proj.Matjalal.domain.member.entity.Member;
import com.proj.Matjalal.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "imageData")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public class ImageData extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String type;
    @Column
    private String filePath;
    @Column
    private String uploadPath;

    @JsonIgnore
    @ManyToOne
    private Article article;
//
//    @JsonIgnore
//    @ManyToOne
//    private Member member;

}
