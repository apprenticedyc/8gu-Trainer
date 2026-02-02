package com.hex.mianshi.job.schedule;

import cn.hutool.core.collection.CollUtil;
import com.hex.mianshi.esdao.QuestionEsDao;
import com.hex.mianshi.mapper.QuestionMapper;
import com.hex.mianshi.model.dto.question.QuestionEsDTO;
import com.hex.mianshi.model.entity.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// @Component
@Slf4j
public class IncSyncQuestionToEs {

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private QuestionEsDao questionEsDao;

    /**
     * 每分钟执行一次
     */
    @Scheduled(fixedRate = 60 * 1000)
    public void run() {
        long FIVE_MINUTES = 5 * 60 * 1000L;
        // 5分钟前的时间
        Date fiveMinutesAgoDate = new Date(new Date().getTime() - FIVE_MINUTES);
        // 查询更新时间大于5分钟前的所有题目
        List<Question> questionList = questionMapper.listQuestionWithDelete(fiveMinutesAgoDate);
        if (CollUtil.isEmpty(questionList)) {
            log.info("no inc question");
            return;
        }
        // 转换为适配ES索引的类型
        List<QuestionEsDTO> questionEsDTOList = questionList.stream()
                .map(QuestionEsDTO::objToDto)
                .collect(Collectors.toList());
        final int pageSize = 500;
        int total = questionEsDTOList.size();
        log.info("IncSyncQuestionToEs start, total {}", total);
        for (int i = 0; i < total; i += pageSize) {
            int end = Math.min(i + pageSize, total);
            log.info("sync from {} to {}", i, end);
            // 使用ElasticsearchRepository提供的现成方法写入ES
            questionEsDao.saveAll(questionEsDTOList.subList(i, end));
        }
        log.info("IncSyncQuestionToEs end, total {}", total);
    }
}
