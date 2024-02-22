package com.sparta.notice.service;

import com.sparta.notice.dto.NoticeRequestDto;
import com.sparta.notice.dto.NoticeResponseDto;
import com.sparta.notice.entity.Notice;
import com.sparta.notice.repository.NoticeRepository;

import java.util.List;

public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

//    public static NoticeResponseDto getNotice(Long id) {
//        // 해당 id 가 없을 경우
//        Notice notice = notice.Repository.findById(id).or
//    }

    public NoticeResponseDto createNotice(NoticeRequestDto requestDto) {
        // RequestDto -> Entity
        Notice notice = new Notice(requestDto); // Notice 객체 하나가 열 1개가 된다.

        // DB 저장
        Notice saveNotice = noticeRepository.save(notice); // notice 이용해 Notice 객체 하나가 열 1개 저~장~
        // 저장한 Notice 객체를 Entity -> ResponseDto 반환하려면, 저장된 공지를 saveNotice 로 받아와야함.

        // (저장이 완료된)Entity -> ResponseDto
        NoticeResponseDto noticeResponseDto = new NoticeResponseDto(notice);

        return noticeResponseDto;
    }

    public List<NoticeResponseDto> getNotices() {
        // DB 조회
        return noticeRepository.findAll();
    }

    // 선택 조회


    public Long updateNotice(Long id, NoticeRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        Notice notice = noticeRepository.findById(id);
        if(notice != null) {
            // notice 내용 수정
            noticeRepository.update(id, requestDto);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }


    public Long deleteNotice(Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        Notice notice = noticeRepository.findById(id);
        if(notice != null) {
            // notice 삭제
            noticeRepository.delete(id);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }
}
