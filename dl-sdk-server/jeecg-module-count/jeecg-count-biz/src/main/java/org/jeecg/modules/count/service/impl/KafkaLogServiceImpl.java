package org.jeecg.modules.count.service.impl;

import com.alibaba.fastjson.JSONObject;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.jeecg.modules.count.service.IKafkaLogService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.service.impl
 * @className: KafkaLogServiceImpl
 * @author: fkh
 * @description: TODO
 * @date: 2023/4/25 10:21
 */
@Slf4j
@Service
public class KafkaLogServiceImpl implements IKafkaLogService {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.key-deserializer}")
    private String keyDeserializer;

    @Value("${spring.kafka.consumer.value-deserializer}")
    private String valueDeserializer;

    @Override
    public JSONObject searchKafkaLog(Long position,Integer pageNo, Integer pageSize){

        ArrayList<Object> result = new ArrayList<>();
        JSONObject resultJson = new JSONObject();
        resultJson.put("size",pageSize);
        resultJson.put("pages",pageNo);

        if (position==null){
            position=searchKafkaSum();
        }
        resultJson.put("total",position);

        long startOffset = position-(pageNo*pageSize); // 起始 offset
        if (pageSize>position){
            startOffset=0;
        }
        if (startOffset<0){
            startOffset=0;
            pageSize= Math.toIntExact(position - ((pageNo - 1) * pageSize));
        }

        Properties pro = new Properties();

        pro.put("bootstrap.servers", bootstrapServers);
        pro.put("key.deserializer", keyDeserializer);
        pro.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        pro.put("group.id", groupId);
        pro.put("max.poll.records",pageSize);
        pro.put("enable.auto.commit", "true");
        pro.put("auto.commit.interval.ms", "1000");

        TopicPartition topicPartition = new TopicPartition("event-parse", 0);
        KafkaConsumer kafkaConsumer = new KafkaConsumer(pro);
        kafkaConsumer.assign(Collections.singletonList(topicPartition));

            kafkaConsumer.seek(topicPartition,startOffset);
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
            if (records.isEmpty()) {
                resultJson.put("records",result);
                return  resultJson;
            }
            records.count();
            for (ConsumerRecord<String, String> record : records) {
                // 处理每条消息
                HashMap<Object, Object> receviceRerecord = new HashMap<>();
                JSONObject jsonObject = JSONObject.parseObject(record.value());

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time=simpleDateFormat.format(jsonObject.get("time"));

                receviceRerecord.put("topic",record.key());
                receviceRerecord.put("time",time);
                receviceRerecord.put("data", record.value());
                result.add(0,receviceRerecord);
            }

        resultJson.put("records",result);
        return resultJson;
    }

    @Override
    public Long searchKafkaSum(){
        Properties pro = new Properties();
        pro.put("bootstrap.servers", bootstrapServers);
        pro.put("key.deserializer", keyDeserializer);
        pro.put("value.deserializer", valueDeserializer);
        pro.put("group.id", groupId);

        TopicPartition topicPartition = new TopicPartition("event-parse", 0);
        KafkaConsumer kafkaConsumer = new KafkaConsumer(pro);
        kafkaConsumer.assign(Collections.singletonList(topicPartition));
        long position = kafkaConsumer.position(topicPartition);

        return position;
    }
}
