package com.proj.Matjalal.domain.member.dto;

import com.proj.Matjalal.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class MemberDTO {
    private Long id;
    private String username;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedData;
    private String email;

    public MemberDTO(Member member){
        this.id = member.getId();
        this.username = member.getUsername();
        this.createdDate = member.getCreatedDate();
        this.modifiedData = member.getModifiedDate();
        this.email = member.getEmail();
    }
}
