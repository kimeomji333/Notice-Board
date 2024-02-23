package com.sparta.notice.dto;

import com.sparta.notice.entity.Notice;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class NoticeResponseDto {
    //private Long id;  // 게시글 구분
    private String title;
    private String name;
    //private String password; // 작성, 조회 반환시 비번 제외
    private String content;
    private LocalDate date;

    public NoticeResponseDto(Notice notice) {
        //this.id = notice.getId();
        this.title = notice.getTitle();
        this.name = notice.getName();
        //this.password = getPassword();
        this.content = notice.getContent();
        this.date = notice.getDate();
    }

    public NoticeResponseDto(String title, String name, String password, String content, LocalDate date) {
        //this.id = getId();
        this.title = title;
        this.name = name;
        //this.password = password;
        this.content = content;
        this.date = date;
    }
}