package com.example.demo.login.domain.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.WorkTime;
import com.example.demo.login.domain.repository.WorkTimeDao;

@Repository
public class WorkTimeDaoJdbcImpl implements WorkTimeDao {

    @Autowired
    JdbcTemplate jdbc;

    // WorkTimeテーブルにデータを1件insert.
    public int insertOne(WorkTime workTime) throws DataAccessException {

        //１件登録
        int job = jdbc.update("INSERT INTO workTime(workDay,"
                + " startTime,"
                + " breakTime,"
                + " endTime,"
                + " workTimeMinute)"
                + " VALUES(?, ?, ?, ?, ?)",
                workTime.getWorkDay(),
                workTime.getStartTime(),
                workTime.getBreakTime(),
                workTime.getEndTime(),
                workTime.getWorkTimeMinute());

        return job;
    }
}
