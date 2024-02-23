package com.sparta.notice.repository;

import com.sparta.notice.dto.NoticeRequestDto;
import com.sparta.notice.dto.NoticeResponseDto;
import com.sparta.notice.entity.Notice;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

@Repository
public class NoticeRepository {

    private final JdbcTemplate jdbcTemplate;

    public NoticeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // DB 저장
    public Notice save(Notice notice) {

        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        String sql = "INSERT INTO notices (title, name, password, content, date) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update( con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, notice.getTitle());
                    preparedStatement.setString(2, notice.getName());
                    preparedStatement.setString(3, notice.getPassword());
                    preparedStatement.setString(4, notice.getContent());
                    preparedStatement.setDate(5, Date.valueOf(notice.getDate()));
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        notice.setId(id);

        return notice;
    }

    // DB 조회
    public List<NoticeResponseDto> findAll() {
        String sql = "SELECT * FROM notices";

        return jdbcTemplate.query(sql, new RowMapper<NoticeResponseDto>() {
            @Override
            public NoticeResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Notice 데이터들을 NoticeResponseDto 타입으로 변환해줄 메서드 //열
                //Long id = rs.getLong("id");
                String title = rs.getString("title");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String content = rs.getString("content");
                LocalDate date = rs.getDate("date").toLocalDate();

                return new NoticeResponseDto(title, name, password, content, date);
            }
        });
    }

    // 찾기 메서드 만듦
    public Notice findById(Long id) {
        // DB 조회
        String sql = "SELECT * FROM notices WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                Notice notice = new Notice();
                notice.setTitle(resultSet.getString("title"));
                notice.setName(resultSet.getString("name"));
                notice.setPassword(resultSet.getString("password"));
                notice.setContent(resultSet.getString("content"));
                notice.setDate(resultSet.getDate("date").toLocalDate());
                return notice;
            } else {
                return null;
            }
        }, id);
    }

    // notice 내용 수정
    public void update(Long id, NoticeRequestDto requestDto) {
        String sql = "UPDATE notices SET title = ?, name = ?, content = ? WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getTitle(), requestDto.getName(), requestDto.getContent(), id);
    }

    // notice 삭제
    public void delete(Long id) {
        String sql = "DELETE FROM notices WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}
