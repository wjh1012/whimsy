package cn.wangjiahang;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.db.Db;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created on 2023/10/11.
 *
 * @author jh.wang
 */
public class GenerateTableData {

    @Test
    public void cost_gps_diff_analysis() throws SQLException {
        genData2Table(100, "cost_gps_diff_analysis", e -> {
            e.set("GROUP_CODE", RandomUtil.randomString(5))
                    .set("DELIVERY_DATE", RandomUtil.randomDay(-20, 20).toDateStr())
                    .set("PROBLEM_DESCRIBE", RandomUtil.randomString(50))
                    .set("EFFECT_COST", RandomUtil.randomBigDecimal(BigDecimal.ZERO, BigDecimal.valueOf(200000)))
                    .set("REASON", RandomUtil.randomString(50))
                    .set("COUNTERMEASURE", RandomUtil.randomString(50))
                    .set("IMPROVE", RandomUtil.randomString(50))
                    .set("USE_SCENES", RandomUtil.randomString(50))
                    .set("PINFAN_CODE", RandomUtil.randomString(5));
        });
    }

    @Test
    public void cost_gps_if() throws SQLException {
        genData2Table(100, "cost_gps_if", e -> {
            e.set("GROUP_CODE", RandomUtil.randomString(5))
                    .set("OUT_LIBRARY_DATE", RandomUtil.randomDay(1, 200).toDateStr())
                    .set("BURDEN_GROUP_CODE", RandomUtil.randomString(5))
                    .set("PINFAN_CODE", RandomUtil.randomString(5))
                    .set("PINFAN_NAME", RandomUtil.randomString(5))
                    .set("PINFAN_TYPE", RandomUtil.randomString(5))
                    .set("PINFAN_NUM", RandomUtil.randomInt(5, 200))
                    .set("ACTUAL_PRICE", RandomUtil.randomBigDecimal(BigDecimal.ZERO, BigDecimal.valueOf(200000)))
                    .set("USE_SCENES", RandomUtil.randomString(50))
                    .set("REASON", RandomUtil.randomString(50))
                    .set("MONTH_NUM", RandomUtil.randomInt(5, 200));
        });
    }

    @Test
    public void clock_attendance_manage() throws SQLException {
        genData2Table(200, "clock_attendance_manage", e -> {
            e.set("WORK_DAY", RandomUtil.randomDay(-20, 20).toDateStr())
                    .set("EMPLOYEE_NO", RandomUtil.randomString(5))
                    .set("DEPT_CODE", RandomUtil.randomString(5))
                    .set("SHIFT_TYPE", RandomUtil.randomEle(Arrays.asList("1", "2")))
                    .set("CLOCK_TYPE", RandomUtil.randomEle(Arrays.asList("1", "0")))
                    .set("TOTAL_WORK_MIN", RandomUtil.randomInt(0, 1440))
                    .set("NORMAL_WORK_MIN", RandomUtil.randomInt(0, 1440))
                    .set("OVER_WORK_MIN", RandomUtil.randomInt(0, 1440))
                    .set("DELAYED_WORK_MIN", RandomUtil.randomInt(0, 1440))
                    .set("REST_WORK_MIN", RandomUtil.randomInt(0, 1440))
                    .set("LEGAL_WORK_MIN", RandomUtil.randomInt(0, 1440))
                    .set("REST_MIN", RandomUtil.randomInt(0, 1440))
                    .set("HOLIDAY_MIN", RandomUtil.randomInt(0, 1440))
                    .set("START_WORK_TIME", RandomUtil.randomDay(-20, 20))
                    .set("END_WORK_TIME", RandomUtil.randomDay(-20, 20));
        });
    }

    @Test
    public void group_day_attendance_info() throws SQLException {
        genData2Table(100, "group_day_attendance_info", e -> {
            e.set("ATTENDANCE_DAY", RandomUtil.randomDay(-20, 20))
                    .set("DEPT_CODE", RandomUtil.randomString(5))
                    .set("EMPLOYEE_NO", RandomUtil.randomString(5))
                    .set("SHIFT_TYPE", RandomUtil.randomEle(Arrays.asList("1", "2")))
                    .set("EMPLOYEE_TYPE", RandomUtil.randomEle(Arrays.asList("1", "2", "3", "4", "5")))
                    .set("REMARK", RandomUtil.randomString(256));
        });
    }

    @Test
    public void base_gps_info() throws SQLException {
        Db use = DbUtil.use();

        List<Entity> saveEntity = new ArrayList<>(3000);
        for (int j = 0; j < 2000; j++) {
            final Entity entity = Entity.create("base_gps_info")
                    .set("ID", IdUtil.getSnowflakeNextId())
                    .set("PINFAN_CODE", "code_" + j)
                    .set("PINFAN_NAME", "nama_" + j)
                    .set("UNIT", "个")
                    .set("SPECIFICATION", "1盒")
                    .set("PINFAN_TYPE", "用度品")
                    .set("PINFAN_TYPE_CODE", "01")
                    .set("CREATED_USER_ID", "-1")
                    .set("CREATED_TIME", DateUtil.now());
            saveEntity.add(entity);
        }
        use.insert(saveEntity);
    }

    private static void genData2Table(int w, String tableName, Consumer<Entity> e) throws SQLException {
        long startTime = System.currentTimeMillis();
        Db use = DbUtil.use();
        final int pageSize = 5000;
        final int page = w * 10000 / pageSize;

        for (int i = 0; i < page; i++) {
            List<Entity> saveEntity = new ArrayList<>((int) (pageSize / 0.75));
            for (int j = 0; j < pageSize; j++) {
                final Entity entity = Entity.create(tableName)
                        .set("ID", IdUtil.getSnowflakeNextId())
                        .set("CREATED_USER_ID", "-1")
                        .set("CREATED_TIME", DateUtil.now());
                e.accept(entity);
                saveEntity.add(entity);
            }
            use.insert(saveEntity);
        }

        Console.log("gen to {}. 耗时: {}毫秒", tableName, System.currentTimeMillis() - startTime);
    }


}
