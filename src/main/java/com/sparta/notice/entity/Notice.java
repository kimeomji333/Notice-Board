package com.sparta.notice.entity;

import com.sparta.notice.dto.NoticeRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Notice {
    private Long id;  // 공지 구분하기 위해서
    private String title;
    private String name;
    private String password;
    private String content;
    private Long date;

    // 클라이언트에서 get 받아온 requestDto 를 위의 필드값에 넣어줌
    public Notice(NoticeRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.name = requestDto.getName();
        this.password = requestDto.getPassword();
        this.content = requestDto.getContent();
        this.date = requestDto.getDate();


    }

    public void update(NoticeRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.name = requestDto.getName();
        this.content = getContent();
    }
}