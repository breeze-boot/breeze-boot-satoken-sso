package com.breeze.boot.flow.config;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 清洁动态跑步
 * 参考：<br>
 * https://huaweicloud.csdn.net/63357859d3efff3090b581b7.html?spm=1001.2101.3001.6650.7&utm_medium=distribute.pc_relevant.none-task-blog-2~default~BlogCommendFromBaidu~activity-7-125409865-blog-77507208.pc_relevant_3mothn_strategy_and_data_recovery&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2~default~BlogCommendFromBaidu~activity-7-125409865-blog-77507208.pc_relevant_3mothn_strategy_and_data_recovery&utm_relevant_index=13
 *
 * @author https://huaweicloud.csdn.net/63357859d3efff3090b581b7.html?spm=1001.2101.3001.6650.7&utm_medium=distribute.pc_relevant.none-task-blog-2~default~BlogCommendFromBaidu~activity-7-125409865-blog-77507208.pc_relevant_3mothn_strategy_and_data_recovery&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2~default~BlogCommendFromBaidu~activity-7-125409865-blog-77507208.pc_relevant_3mothn_strategy_and_data_recovery&utm_relevant_index=13
 * @date 2023-03-01
 */
@Slf4j
@Component
public class CleanDynamicRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        log.info("清除数据源");
        DynamicDataSourceContextHolder.clear();
    }

}
