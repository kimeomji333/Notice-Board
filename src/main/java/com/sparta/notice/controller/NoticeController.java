package com.sparta.notice.controller;

import com.sparta.notice.dto.NoticeRequestDto;
import com.sparta.notice.dto.NoticeResponseDto;
import com.sparta.notice.service.NoticeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        return noticeService.createNotice(requestDto);
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
        return noticeService.updateNotice(id, requestDto.getPassword(), requestDto);
    }

    // 게시글 삭제
    @DeleteMapping("/notices/{id}")
    public ResponseEntity deleteNotice(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        String password = requestBody.get("password");
        try {
            noticeService.deleteNotice(id, password);
            return ResponseEntity.ok("게시글이 삭제되었습니다.");
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
        }
    }
}
