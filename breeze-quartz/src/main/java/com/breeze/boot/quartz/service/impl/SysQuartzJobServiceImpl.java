package com.breeze.boot.quartz.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.quartz.domain.SysQuartzJob;
import com.breeze.boot.quartz.manager.QuartzManager;
import com.breeze.boot.quartz.mapper.SysQuartzJobMapper;
import com.breeze.boot.quartz.query.JobQuery;
import com.breeze.boot.quartz.service.SysQuartzJobService;
import com.breeze.core.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

import static com.breeze.core.constants.QuartzConstants.JOB_NAME;

/**
 * Quartz任务调度Impl
 *
 * @author gaoweixuan
 */
@Service
public class SysQuartzJobServiceImpl extends ServiceImpl<SysQuartzJobMapper, SysQuartzJob> implements SysQuartzJobService {

    /**
     * quartz 管理器
     */
    @Autowired
    private QuartzManager quartzManager;

    /**
     * 负载任务
     */
    @PostConstruct
    private void loadJobs() {
        List<SysQuartzJob> quartzJobs = this.list();
        for (SysQuartzJob quartzJob : quartzJobs) {
            this.quartzManager.addOrUpdateJob(quartzJob);
        }
    }

    /**
     * 列表页面
     *
     * @param jobQuery 任务查询
     * @return {@link Page}<{@link SysQuartzJob}>
     */
    @Override
    public Page<SysQuartzJob> listPage(JobQuery jobQuery) {
        return this.baseMapper.listPage(new Page<>(jobQuery.getCurrent(), jobQuery.getSize()), jobQuery);
    }

    /**
     * 保存任务
     *
     * @param sysQuartzJob quartz任务
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> saveJob(SysQuartzJob sysQuartzJob) {
        sysQuartzJob.insert();
        this.quartzManager.addOrUpdateJob(sysQuartzJob);
        return Result.ok(Boolean.TRUE);
    }

    /**
     * 更新任务通过id
     *
     * @param sysQuartzJob quartz任务
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> updateJobById(SysQuartzJob sysQuartzJob) {
        if (this.checkExists(sysQuartzJob)) {
            return Result.fail(Boolean.FALSE, "修改失败");
        }
        sysQuartzJob.updateById();
        this.quartzManager.addOrUpdateJob(sysQuartzJob);
        return Result.ok(Boolean.TRUE);
    }

    /**
     * 暂停任务
     *
     * @param jobId 任务id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> pauseJob(Long jobId) {
        SysQuartzJob quartzJob = this.getById(jobId);
        if (Objects.isNull(quartzJob)) {
            return Result.fail(Boolean.FALSE, "任务不存在");
        }
        quartzJob.setStatus(0);
        quartzJob.updateById();
        this.quartzManager.pauseJob(quartzJob.getId() + ":" + JOB_NAME, quartzJob.getJobGroupName());
        return Result.ok(Boolean.TRUE);
    }

    /**
     * 恢复任务
     *
     * @param jobId 任务id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> resumeJob(Long jobId) {
        SysQuartzJob quartzJob = this.getById(jobId);
        if (Objects.isNull(quartzJob)) {
            return Result.fail(Boolean.FALSE, "任务不存在");
        }
        quartzJob.setStatus(1);
        quartzJob.updateById();
        this.quartzManager.resumeJob(quartzJob.getId() + ":" + JOB_NAME, quartzJob.getJobGroupName());
        return Result.ok(Boolean.TRUE);
    }

    /**
     * 删除任务
     *
     * @param jobIds 任务ids
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> deleteJob(List<Long> jobIds) {
        for (Long jobId : jobIds) {
            SysQuartzJob quartzJob = this.getById(jobId);
            if (Objects.isNull(quartzJob)) {
                continue;
            }
            quartzJob.deleteById();
            this.quartzManager.deleteJob(quartzJob.getId() + ":" + JOB_NAME, quartzJob.getJobGroupName());
        }
        return Result.ok(Boolean.TRUE);
    }

    /**
     * 立刻运行
     *
     * @param jobId 任务ID
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> runJobNow(Long jobId) {
        SysQuartzJob quartzJob = this.getById(jobId);
        this.quartzManager.runJobNow(quartzJob.getId() + ":" + JOB_NAME, quartzJob.getJobGroupName());
        return Result.ok(Boolean.TRUE);
    }

    /**
     * 检查是否存在
     *
     * @param sysQuartzJob quartz任务
     * @return boolean
     */
    private boolean checkExists(SysQuartzJob sysQuartzJob) {
        return Objects.isNull(this.getById(sysQuartzJob.getId()));
    }

}




