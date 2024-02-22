package com.sparta.notice.controller;

import com.sparta.notice.dto.NoticeRequestDto;
import com.sparta.notice.dto.NoticeResponseDto;
import com.sparta.notice.service.NoticeService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    // 게시글 작성
    @PostMapping("/notices")
    public NoticeResponseDto createNotice(@RequestBody NoticeRequestDto requestDto) {
        // 인스턴스화
        return noticeService.createNotice(requestDto); // 컨트롤러와 서비스 메서드 이름 같게 만들어 전달받은 비지니스로직인걸 알 수 있음.
        // 서비스 클래스 createNotice 메서드에서 -> return 해서 client로 바로 보냄
    }

    // 게시글 전체 조회
    @GetMapping("/notices")
    public List<NoticeResponseDto> getNotices() {
        return noticeService.getNotices();
    }

    // 게시글 선택 조회
    @GetMapping("/notices/{id}")
    public NoticeResponseDto getNotice(@PathVariable Long id) {
        return noticeService.getNotice(id);
    }

    // 게시글 수정
    @PutMapping("/notices/{id}")
    public Long updateNotice(@PathVariable Long id, @RequestBody NoticeRequestDto requestDto) {
        return noticeService.updateNotice(id, requestDto); // 파라미터로 id, 수정할 데이터
    }

    // 게시글 삭제
    @DeleteMapping("/notices/{id}")
    public Long deleteNotice(@PathVariable Long id, @RequestBody NoticeRequestDto requestDto) {
        return noticeService.deleteNotice(id, requestDto);
    }
}