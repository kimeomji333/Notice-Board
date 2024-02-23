package com.sparta.notice.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class NoticeRequestDto {
    private String title;
    private String name;
    private String password;
    private String content;
    private LocalDate date;

    public NoticeRequestDto(String title, String name, String password, String content, LocalDate date) {
        this.title = title;
        this.name = name;
        this.password = password;
        this.content = content;
        this.date = date;
    }
}
