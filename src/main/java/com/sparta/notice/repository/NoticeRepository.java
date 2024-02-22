package com.sparta.notice.repository;

import com.sparta.notice.dto.NoticeRequestDto;
import com.sparta.notice.dto.NoticeResponseDto;
import com.sparta.notice.entity.Notice;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class NoticeRepository {

    private final JdbcTemplate jdbcTemplate;

    public NoticeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Notice save(Notice notice) {
        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        String sql = "INSERT INTO notice (title, name, password, contents, date) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update( con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, notice.getTitle());
                    preparedStatement.setString(2, notice.getName());
                    preparedStatement.setString(3, notice.getPassword());
                    preparedStatement.setString(4, notice.getContent());
                    preparedStatement.setLong(5, notice.getDate());
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        notice.setId(id);

        return notice;
    }

    // 전체 조회
    public List<NoticeResponseDto> findAll() {
        // DB 조회
        String sql = "SELECT * FROM notice";

        return jdbcTemplate.query(sql, new RowMapper<NoticeResponseDto>() {
            @Override
            public NoticeResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Notice 데이터들을 NoticeResponseDto 타입으로 변환해줄 메서드 //열
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String content = rs.getString("content");
                Long date = rs.getLong("date");
                return new NoticeResponseDto(id, title, name, password, content, date);
            }
        });
    }


    public void update(Long id, NoticeRequestDto requestDto) {
        // notice 내용 수정
        String sql = "UPDATE notice SET title = ?, name = ?, content = ? WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getTitle(), requestDto.getName(), requestDto.getContent(), id);
    }

    public void delete(Long id) {
        // notice 삭제
        String sql = "DELETE FROM notice WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


    // 찾기 메서드 만듦
    public Notice findById(Long id) {
        // DB 조회
        String sql = "SELECT * FROM notice WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                Notice notice = new Notice();
                notice.setTitle(resultSet.getString("title"));
                notice.setName(resultSet.getString("name"));
                notice.setPassword(resultSet.getString("password"));
                notice.setContent(resultSet.getString("content"));
                notice.setDate(resultSet.getLong("date"));
                return notice;
            } else {
                return null;
            }
        }, id);
    }
}
