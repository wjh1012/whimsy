package cn.wangjiahang;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jh.wang
 * @since 2024/7/3
 */

@NoArgsConstructor
@Data
public class LogicTestQuestions {
    LogRecord logRecord = null;


    @Before
    public void before() {
        // 对应流程 读取配置文件→获取变更日志数组

        try {
            logRecord = JSONUtil.toBean(ResourceUtil.readUtf8Str("logRecord.json"), LogRecord.class);
        } catch (Exception e) {
            throw new RuntimeException("日志文件读取失败", e);
        }
    }

    @Test
    public void test() {
        // <id,record-list>
        final Map<Long, List<LogRecord.ChangeLogDTO>> logRecordIdMap = logRecord.getChangeLog()
                .stream()
                .collect(Collectors.groupingBy(LogRecord.ChangeLogDTO::getId));

        final List<LogRecord.VerifySubjectDTO> resultChildren = logRecord.getVerifySubject().stream().peek(item -> {
                    final List<LogRecord.VerifySubjectDTO> children = item.getChildren().stream().peek(innerItem -> {

                        // 遍历第3层"校验对象"
                        innerItem.getChildren().forEach(subject -> {
                            List<LogRecord.ChangeLogDTO> show;

                            // 判断捕获的日志数组不为空？ 更新”校验对象"的“使能
                            if ((show = logRecordIdMap.get(subject.getShowId())) != null && !show.isEmpty()) {
                                subject.setShow(CollUtil.getLast(show).getShow());

                                // 判断”校验对象”是否使能？ 更新"校验对象"的"状态
                                List<LogRecord.ChangeLogDTO> status;
                                if ((status = logRecordIdMap.get(subject.getStatusId())) != null) {

                                    // todo 说的不明确: 判断”校验对象”是否使能？   故取最后一条数据作为是否使能的标准
                                    subject.setStatus(CollUtil.getLast(status).getStatus());
                                }
                            }
                        });

                        // 遍历第2层"校验分类”
                        innerItem.full(true);
                    }).filter(LogRecord.VerifySubjectDTO::isValidData).collect(Collectors.toList());

                    item.setChildren(children);

                    // 遍历第1层"阶段
                    item.full(false);
                }).filter(item -> CollUtil.isNotEmpty(item.getChildren()))
                .collect(Collectors.toList());

        // 构建返回值
        final LogRecord.VerifySubjectDTO result = new LogRecord.VerifySubjectDTO();
        result.setChildren(resultChildren);

        // 不是必要的
        genId(result);

        System.out.println("result: " + JSONUtil.toJsonPrettyStr(JSONUtil.parse(result)));

        // todo 结果查看 resource -> result.json
    }

    public void genId(LogRecord.VerifySubjectDTO result) {
        result.setParentId(null);
        result.setId(IdUtil.getSnowflakeNextId());

        genChildrenId(result.getId(), result.getChildren());
    }

    public void genChildrenId(Long parentId, List<LogRecord.VerifySubjectDTO> children) {
        if (CollUtil.isEmpty(children)) {
            return;
        }
        children.forEach(item -> {
            item.setParentId(parentId);
            item.setId(IdUtil.getSnowflakeNextId());

            this.genChildrenId(item.getId(), item.getChildren());
        });
    }
}

@Data
class LogRecord {
    private List<ChangeLogDTO> changeLog;
    private List<VerifySubjectDTO> verifySubject;

    @NoArgsConstructor
    @Data
    public static class ChangeLogDTO {
        private Long id;

        /**
         * 使能的值
         */
        private String show;
        /**
         * 状态的值
         */
        private String status;
    }

    @NoArgsConstructor
    @Data
    public static class VerifySubjectDTO {
        private Long id;
        private Long parentId;

        /**
         * 变更日志中的id
         */
        // @JsonIgnore
        private Long showId;
        // @JsonIgnore
        private Long statusId;

        private String show;
        private String status;

        private List<VerifySubjectDTO> children;

        private boolean validData = true;

        public void full(boolean verifyType) {
            if (verifyType) {
                if (children.stream().anyMatch(item -> "使能".equals(item.getShow()))) {
                    show = "使能";
                } else {
                    show = "不使能";
                    validData = false;
                }
            }

            if (children.stream().anyMatch(item -> "初始化".equals(item.getStatus()))) {
                status = "初始化";
            } else if (children.stream().allMatch(item -> "成功".equals(item.getStatus()))) {
                status = "成功";
            } else if (children.stream().filter(item -> "失败".equals(item.getStatus())).count() == 1) {
                status = "失败";
            }
        }
    }
}
