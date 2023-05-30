package org.jeecg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.*;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;
/**
 * @Author: Scott
 * @Date: 2018/2/7
 * @description: kafka 配置类
 */
@Configuration
public class KafkaConfig{

    @Bean
    @Primary
    public ErrorHandler kafkaErrorHandler(KafkaTemplate<?, ?> template) {
        // <1> 创建 DeadLetterPublishingRecoverer 对象 异常存在死信队列 以DLT结尾
        ConsumerRecordRecoverer recoverer = new DeadLetterPublishingRecoverer(template);
        // <2> 创建 FixedBackOff 对象   设置重试间隔 3秒 次数为 3次
        BackOff backOff = new FixedBackOff(10000L, 3L);
        // <3> 创建 SeekToCurrentErrorHandler 对象
        return new SeekToCurrentErrorHandler(recoverer, backOff);
    }

}