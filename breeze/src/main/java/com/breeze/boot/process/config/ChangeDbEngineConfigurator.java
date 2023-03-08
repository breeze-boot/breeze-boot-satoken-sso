package com.breeze.boot.process.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.AbstractEngineConfiguration;
import org.flowable.common.engine.impl.EngineConfigurator;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 改变db引擎配置器
 * 参考：<br>
 * https://huaweicloud.csdn.net/63357859d3efff3090b581b7.html?spm=1001.2101.3001.6650.7&utm_medium=distribute.pc_relevant.none-task-blog-2~default~BlogCommendFromBaidu~activity-7-125409865-blog-77507208.pc_relevant_3mothn_strategy_and_data_recovery&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2~default~BlogCommendFromBaidu~activity-7-125409865-blog-77507208.pc_relevant_3mothn_strategy_and_data_recovery&utm_relevant_index=13
 *
 * @author https://huaweicloud.csdn.net/63357859d3efff3090b581b7.html?spm=1001.2101.3001.6650.7&utm_medium=distribute.pc_relevant.none-task-blog-2~default~BlogCommendFromBaidu~activity-7-125409865-blog-77507208.pc_relevant_3mothn_strategy_and_data_recovery&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2~default~BlogCommendFromBaidu~activity-7-125409865-blog-77507208.pc_relevant_3mothn_strategy_and_data_recovery&utm_relevant_index=13
 * @date 2023-03-01
 */
@Slf4j
@Component
public class ChangeDbEngineConfigurator implements EngineConfigurator {

    private static final AtomicBoolean initialized = new AtomicBoolean();

    @Override
    public void beforeInit(AbstractEngineConfiguration abstractEngineConfiguration) {
        if (initialized.compareAndSet(false, true)) {
            DataSource dataSource = abstractEngineConfiguration.getDataSource();
            if (dataSource instanceof TransactionAwareDataSourceProxy) {
                dataSource = ((TransactionAwareDataSourceProxy) dataSource).getTargetDataSource();
            }
            if (dataSource instanceof DynamicRoutingDataSource) {
                DataSource master = ((DynamicRoutingDataSource) dataSource).getDataSource("flowable");
                abstractEngineConfiguration.setDataSource(master);
            }
            log.info("切换数据源");
        }
    }

    @Override
    public void configure(AbstractEngineConfiguration abstractEngineConfiguration) {

    }

    @Override
    public int getPriority() {
        return 0;
    }
}
