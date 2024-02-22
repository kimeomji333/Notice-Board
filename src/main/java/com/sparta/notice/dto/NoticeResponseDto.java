package com.sparta.notice.dto;

import com.sparta.notice.entity.Notice;
import lombok.Getter;

@Getter
public class NoticeResponseDto {
    private Long id;  // 게시글 구분
    private String title;
    private String name;
    //private String password; // 작성, 조회 반환시 비번 제외
    private String content;
    private Long date;

    public NoticeResponseDto(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.name = notice.getName();
        //this.password = notice.getPassword();
        this.content = notice.getContent();
        this.date = notice.getDate();
    }

    public NoticeResponseDto(Long id, String title, String name, String password, String content, Long date) {
        this.id = id;
        this.title = title;
        this.name = name;
        //this.password = password;
        this.content = content;
        this.date = date;
    }
}