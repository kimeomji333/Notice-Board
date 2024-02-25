package com.sparta.notice.entity;

import com.sparta.notice.dto.NoticeRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity  //JPA 가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
@Table(name = "notice") // 매핑할 테이블의 이름 저장
@NoArgsConstructor
public class Notice extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // auto-increment 사용
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "content", nullable = false, length = 500)
    private String content;

    // 클라이언트에서 get 받아온 requestDto 를 위의 필드값에 넣어줌
    public Notice(NoticeRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.name = requestDto.getName();
        this.password = requestDto.getPassword();
        this.content = requestDto.getContent();
    }

    public void update(NoticeRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.name = requestDto.getName();
        this.content = getContent();
    }
}