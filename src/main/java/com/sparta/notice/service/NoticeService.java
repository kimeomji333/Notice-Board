package com.sparta.notice.service;

import com.sparta.notice.dto.NoticeRequestDto;
import com.sparta.notice.dto.NoticeResponseDto;
import com.sparta.notice.entity.Notice;
import com.sparta.notice.repository.NoticeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    // 게시글 작성
    public NoticeResponseDto createNotice(NoticeRequestDto requestDto) {
        // RequestDto -> Entity
        Notice notice = new Notice(requestDto);
        // DB 저장
        Notice saveNotice = noticeRepository.save(notice);
        // Entity -> ResponseDto
        NoticeResponseDto noticeResponseDto = new NoticeResponseDto(notice);

        return noticeResponseDto;
    }

    // 게시글 전체 조회
    public List<NoticeResponseDto> getNotices() {
        // DB 조회
        return noticeRepository.findAllByOrderByCreatedAtDesc().stream().map(NoticeResponseDto::new).toList();
    }

    // 게시글 선택 조회
    public NoticeResponseDto getNotice(Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        Notice notice = findNotice(id);
        return new NoticeResponseDto(notice);
    }

    // 게시글 수정
    @Transactional
    public Long updateNotice(Long id, String password, NoticeRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        Notice notice = findNotice(id);
        // 비밀번호 같은 경우에 수정 가능
        if (!notice.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        notice.update(requestDto);
        return id;
    }

    // 게시글 삭제
    public Long deleteNotice(Long id, String password) {
        // 해당 메모가 DB에 존재하는지 확인
        Notice notice = findNotice(id);
        // 비밀번호 같은 경우에 수정 가능
        if (!notice.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        noticeRepository.delete(notice);
        return id;
    }

    // findNotice 메서드
    private Notice findNotice(Long id) {
        return noticeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 공지는 존재하지 않습니다.")
        );
    }
}
