package com.sparta.notice.dto;

import com.sparta.notice.entity.Notice;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeResponseDto {
    //private Long id;
    private String title;
    private String name;
    //private String password;
    private String content;
    private LocalDateTime createdAt;

    public NoticeResponseDto(Notice notice) {
        //this.id = notice.getId();
        this.title = notice.getTitle();
        this.name = notice.getName();
        this.content = notice.getContent();
        //this.password = notice.getPassword();  // 작성, 조회 반환시 비번 제외
        this.createdAt = notice.getCreatedAt();
    }
}