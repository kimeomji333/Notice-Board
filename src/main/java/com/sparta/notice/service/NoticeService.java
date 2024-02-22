package com.sparta.notice.service;

import com.sparta.notice.dto.NoticeRequestDto;
import com.sparta.notice.dto.NoticeResponseDto;
import com.sparta.notice.entity.Notice;
import com.sparta.notice.repository.NoticeRepository;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
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

    // 게시글 전체 조회
    public List<NoticeResponseDto> getNotices() {
        // DB 조회
         return noticeRepository.findAll();


        // 내림차순 정렬
        List<NoticeResponseDto> resopnseListDesc = getNoticesList.stream()
                .sorted(Comparator.comparing(NoticeResponseDto::getDate).reversed()).toList();
        return resopnseListDesc;
    }


    // 게시글 선택 조회
    public static NoticeResponseDto getNotice(Long id) {
        // 해당 게시글이 DB에 존재하는지 확인. 해당 id 가 없을 경우
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        // 해당 id 가 있을 경우
        return new NoticeResponseDto(notice);
    }


    // 게시글 수정
    public Long updateNotice(Long id, NoticeRequestDto requestDto) {
        // 해당 게시글이 DB에 존재하는지 확인
        Notice notice = noticeRepository.findById(id);

        // 비밀번호 일치 여부 확인
        if(notice.getPassword().equals(requestDto.getPassword())) {
            // 일치하면, notice 수정
            noticeRepository.update(id, requestDto);

            return id;
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }


    // 게시글 삭제
    public Long deleteNotice(Long id, NoticeRequestDto requestDto) {
        // 해당 게시글이 DB에 존재하는지 확인
        Notice notice = noticeRepository.findById(id);

        // 비밀번호 일치 여부 확인
        if(notice.getPassword().equals(requestDto.getPassword())) {
            //  일치하면, notice 삭제
            noticeRepository.delete(id, requestDto);

            return id;
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }


    
}
