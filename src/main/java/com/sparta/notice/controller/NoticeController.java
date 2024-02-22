package com.sparta.notice.controller;

import com.sparta.notice.dto.NoticeRequestDto;
import com.sparta.notice.dto.NoticeResponseDto;
import com.sparta.notice.entity.Notice;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class NoticeController {

    // DB 대신에 사용
    private final Map<Long, Notice> noticeList = new HashMap<>();

    @PostMapping("/notices")
    public NoticeResponseDto createNotice(@RequestBody NoticeRequestDto requestDto) {
        // RequestDto -> Entity
        Notice notice = new Notice(requestDto);


        // Notice Max ID Check
        Long maxId = noticeList.size() > 0 ? Collections.max(noticeList.keySet()) + 1 : 1;
        notice.setId(maxId);

        // DB 저장
        noticeList.put(notice.getId(), notice);

        // Entity -> ResponseDto
        NoticeResponseDto noticeResponseDto = new NoticeResponseDto(notice);

        return noticeResponseDto;
    }

    @GetMapping("/notices")
    public List<NoticeResponseDto> getNotices() {
        // Map To List
        List<NoticeResponseDto> responseList = noticeList.values().stream()
                .map(NoticeResponseDto::new).toList();

        return responseList;
    }

    @PutMapping("/notices/{id}")
    public Long updateNotice(@PathVariable Long id, @RequestBody NoticeRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        if (noticeList.containsKey(id)) {
            // 해당 메모 가져오기
            Notice notice = noticeList.get(id);

            // 공지 수정
            notice.update(requestDto);
            return notice.getId();
        } else {
            throw new IllegalArgumentException("선택한 공지는 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/notices/{id}")
    public Long deleteNotice(@PathVariable Long id) {
        // 해당 공지 DB에 존재하는지 확인
        if (noticeList.containsKey(id)) {
            // 해당 공지 삭제하기
            noticeList.remove(id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 공지는 존재하지 않습니다.");
        }
    }
}
