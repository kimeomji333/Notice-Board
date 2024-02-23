package com.sparta.notice.service;

import com.sparta.notice.dto.NoticeRequestDto;
import com.sparta.notice.dto.NoticeResponseDto;
import com.sparta.notice.entity.Notice;
import com.sparta.notice.repository.NoticeRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
        Notice notice = new Notice(requestDto); // Notice 객체 하나가 열 1개가 된다.

        // DB 저장
        Notice saveNotice = noticeRepository.save(notice); // notice 이용해 Notice 객체 하나가 열 1개 저~장~
        // 저장한 Notice 객체를 Entity -> ResponseDto 반환하려면, 저장된 공지를 saveNotice 로 받아와야함.

        // (저장이 완료된)Entity -> ResponseDto
        NoticeResponseDto noticeResponseDto = new NoticeResponseDto(saveNotice);

        return noticeResponseDto;
    }


    // 게시글 선택 조회
    public NoticeResponseDto getNotice(Long id) {
        // 해당 게시글이 DB에 존재하는지 확인.
        Notice notice = noticeRepository.findById(id);
        // 게시글이 없는 경우
        if (notice == null) {
            throw new RuntimeException("해당 게시글이 없습니다.");
        }
        // 게시글이 있는 경우
        return new NoticeResponseDto(notice);
    }


    // 게시글 전체 조회
    public List<NoticeResponseDto> getNotices() {
        // DB 조회
        List<NoticeResponseDto> getNoticesList = noticeRepository.findAll();

        // 내림차순 정렬
        List<NoticeResponseDto> resopnseListDesc = getNoticesList.stream()
                .sorted(Comparator.comparing(NoticeResponseDto::getDate).reversed()).toList();
        return resopnseListDesc;
    }


    // 게시글 수정
    public NoticeResponseDto updateNotice(Long id, String password, NoticeRequestDto requestDto) {
        // 해당 게시글이 DB에 존재하는지 확인
        Notice notice = noticeRepository.findById(id);
        // 게시글이 없는 경우
        if (notice == null) {
            throw new RuntimeException("해당 게시글이 없습니다.");
        }
        // 게시글이 있는 경우. 비밀번호 일치 여부 확인
        if(!notice.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        } else {
            noticeRepository.update(id, requestDto);
            return new NoticeResponseDto(notice);
        }
    }

    // 게시글 삭제
    public Long deleteNotice(Long id, String password) {
        // 해당 게시글이 DB에 존재하는지 확인
        Notice notice = noticeRepository.findById(id);
        if (notice == null) {
              throw new RuntimeException("해당 게시글이 없습니다.");
        }
        // 게시글이 있는 경우, 비밀번호 일치 여부 확인
        if(!notice.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        noticeRepository.delete(id);
        return id;
    }
}
