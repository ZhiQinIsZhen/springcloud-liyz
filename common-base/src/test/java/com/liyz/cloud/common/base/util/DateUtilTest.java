package com.liyz.cloud.common.base.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2020/4/18 1:14
 */
@Slf4j
@Ignore
public class DateUtilTest {

    @Test
    public void betweenTest() {
        long between = DateUtil.between(DateUtil.currentDate(),
                DateUtil.parse("2023-01-01 12:12:12", DateUtil.PATTERN_DATE_TIME),
                ChronoUnit.MONTHS);
        log.info("between : {}", between);
    }

    @Test
    public void addTest() {
        String date = DateUtil.formatDate(DateUtil.add(
                DateUtil.parse("2020-01-01 12:12:12", DateUtil.PATTERN_DATE_TIME),
                1000,
                Calendar.DATE));
        log.info("add : {}", date);
    }

    @Test
    public void dayOfMonthTest() {
        Date date = DateUtil.parse("2020-12-21 12:12:12", DateUtil.PATTERN_DATE_TIME);
        String dateStr = DateUtil.formatDate(DateUtil.firstDayOfMonth(date));
        log.info("firstDayOfMonth : {}", dateStr);
        dateStr = DateUtil.formatDate(DateUtil.lastDayOfMonth(date));
        log.info("lastDayOfMonth : {}", dateStr);
        dateStr = DateUtil.formatDate(DateUtil.firstDayOfPreMonth(date));
        log.info("firstDayOfPreMonth : {}", dateStr);
        dateStr = DateUtil.formatDate(DateUtil.lastDayOfPreMonth(date));
        log.info("lastDayOfPreMonth : {}", dateStr);
        dateStr = DateUtil.formatDate(DateUtil.firstDayOfNextMonth(date));
        log.info("firstDayOfNextMonth : {}", dateStr);
        dateStr = DateUtil.formatDate(DateUtil.lastDayOfNextMonth(date));
        log.info("lastDayOfNextMonth : {}", dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        int month = calendar.get(Calendar.MONTH);
        log.info("month : {}", month);
    }

    @Test
    public void dayOfQuarterTest() {
        Date date = DateUtil.parse("2020-12-21 12:12:12", DateUtil.PATTERN_DATE_TIME);
        String dateStr = DateUtil.formatDate(DateUtil.firstDayOfQuarter(date));
        log.info("firstDayOfQuarter : {}", dateStr);
        dateStr = DateUtil.formatDate(DateUtil.lastDayOfQuarter(date));
        log.info("lastDayOfQuarter : {}", dateStr);
        dateStr = DateUtil.formatDate(DateUtil.firstDayOfPreQuarter(date));
        log.info("firstDayOfPreQuarter : {}", dateStr);
        dateStr = DateUtil.formatDate(DateUtil.lastDayOfPreQuarter(date));
        log.info("lastDayOfPreQuarter : {}", dateStr);
        dateStr = DateUtil.formatDate(DateUtil.firstDayOfNextQuarter(date));
        log.info("firstDayOfNextQuarter : {}", dateStr);
        dateStr = DateUtil.formatDate(DateUtil.lastDayOfNextQuarter(date));
        log.info("lastDayOfNextQuarter : {}", dateStr);
    }

    @Test
    public void dayOfYearTest() {
        Date date = DateUtil.parse("2020-12-21 12:12:12", DateUtil.PATTERN_DATE_TIME);
        String dateStr = DateUtil.formatDate(DateUtil.firstDayOfYear(date));
        log.info("firstDayOfYear : {}", dateStr);
        dateStr = DateUtil.formatDate(DateUtil.lastDayOfYear(date));
        log.info("lastDayOfYear : {}", dateStr);
        dateStr = DateUtil.formatDate(DateUtil.firstDayOfPreYear(date));
        log.info("firstDayOfPreYear : {}", dateStr);
        dateStr = DateUtil.formatDate(DateUtil.lastDayOfPreYear(date));
        log.info("lastDayOfPreYear : {}", dateStr);
        dateStr = DateUtil.formatDate(DateUtil.firstDayOfNextYear(date));
        log.info("firstDayOfNextYear : {}", dateStr);
        dateStr = DateUtil.formatDate(DateUtil.lastDayOfNextYear(date));
        log.info("lastDayOfNextYear : {}", dateStr);
    }
}