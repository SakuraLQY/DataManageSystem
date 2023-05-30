package org.jeecg.modules.count.test;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.jeecg.JeecgSystemApplication;
import org.jeecg.common.api.vo.SdkResult;
import org.jeecg.common.game.vo.OpSubGameModel;
import org.jeecg.config.sign.context.SdkContext;
import org.jeecg.config.sign.context.SdkInfo;
import org.jeecg.modules.game.controller.sdk.SdkGameController;
import org.jeecg.modules.game.dto.SdkStartDataDto;
import org.jeecg.modules.users.controller.sdk.SdkUserController;
import org.jeecg.modules.users.dto.SdkAliveDto;
import org.jeecg.modules.users.dto.SdkLoginDto;
import org.jeecg.modules.users.vo.SdkUserLoginRes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.test
 * @className: LosTest
 * @author: fkh
 * @description: TODO
 * @date: 2023/4/23 16:36
 */
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = JeecgSystemApplication.class)
public class LosTest {

    @Autowired
    private SdkUserController sdkUserController;

    @Autowired
    private SdkGameController sdkGameController;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Test
    public void te(){
        System.out.println(7/10);
    }

    @Test
    public void testKafka() throws ExecutionException, InterruptedException {
        Properties pro = new Properties();

//kafka集群多个kafka,获取topic是按顺序获取的，比如第一个192.168.11.2是正常的就只会获取第一个kafka的topic，后面的kafka就不会去获取,如果第一个服务异常才会去获取第二个kafka
        pro.put("bootstrap.servers", "192.168.1.66:9092");
        pro.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        pro.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        pro.put("group.id", "idealighter");
        pro.put("max.poll.records","2");
        pro.put("enable.auto.commit", "true");
        pro.put("auto.commit.interval.ms", "1000");
        //KafkaUtils.getTopicNames(zkAddress)
//        ListTopicsResult result = KafkaAdminClient.create(pro).listTopics();
//        KafkaFuture<Set<String>> set = result.names();
//        System.out.println(set.get());
//        System.out.println(result.listings());
//        System.out.println(result.namesToListings());
        TopicPartition topicPartition = new TopicPartition("event-parse", 0);
        KafkaConsumer kafkaConsumer = new KafkaConsumer(pro);
        kafkaConsumer.assign(Collections.singletonList(topicPartition));
        long position = kafkaConsumer.position(topicPartition);
        System.out.println(position);
//        kafkaConsumer.seekToBeginning(Collections.singletonList(topicPartition));
        long startOffset = 0L; // 起始 offset
        int pageSize = 2; // 每页大小
        int pageNo = 0; // 当前页码
        while (true) {

            kafkaConsumer.seek(topicPartition,startOffset);
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
            if (records.isEmpty()) {
                break;
            }
            records.count();
            int count=0;
            for (ConsumerRecord<String, String> record : records) {
                // 处理每条消息
                count++;
                System.out.printf("topic = %s, partition = %d, offset = %d, key = %s, value = %s%n",
                    record.topic(), record.partition(), record.offset(), record.key(), record.value());
                if (count >= pageSize) {
                    break;
                }
            }
            if (count < pageSize) {
                break;
            }
            System.out.println("-----------------------");
            pageNo++;
            startOffset = pageNo*pageSize;
        }
//
//        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(pro);
//        consumer.subscribe(Collections.singleton("event-parse"));
//        ConsumerRecords<String, String> records = consumer.poll(100);
//
//        records.forEach(record ->{
//            System.out.println("offset {}"+record.offset());
//            System.out.println("offset {}"+record.value());
//            System.out.println("offset {}"+record.partition());
//            System.out.println("offset {}"+record.key());
//            System.out.println("-----------------------------------------------------------------");
//        });

//        List list = kafkaTemplate.partitionsFor("event-parse");
//        System.out.println(list);
    }

//    @Test
//    public static void test()
//    {
//        Properties properties = new Properties();
//        properties.setProperty("bootstrap.servers", "localhost:9092");
//        properties.setProperty("group.id", "idealighter");
//        properties.setProperty("key.deserializer",
//            "org.apache.kafka.common.serialization.StringDeserializer");
//        properties.setProperty("value.deserializer",
//            "org.apache.kafka.common.serialization.StringDeserializer");
//        properties.setProperty("auto.offset.reset", "latest");
//
//        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>(
//            "ia-label",
//            new SimpleStringSchema(),
//            properties
//        );
//        FlinkKafkaConsumer<MyConsumerRecord> consumer2 = new FlinkKafkaConsumer<>(
//            "ia-label",
//            new KafkaDeserializationSchema<MyConsumerRecord>() {
//                @Override
//                public boolean isEndOfStream(MyConsumerRecord s) {
//                    return false;
//                }
//
//                @Override
//                public MyConsumerRecord deserialize(ConsumerRecord<byte[], byte[]> consumerRecord)
//                    throws Exception {
//                    Headers headers = consumerRecord.headers();
//                    HashMap<String, String> headerMap = new HashMap<>();
//                    for (Header header : headers) {
//                        headerMap.put(header.key(), new String(header.value()));
//                    }
//                    byte[] key1 = consumerRecord.key();
//                    byte[] value1 = consumerRecord.value();
//                    String key = key1 == null ? null : new String(key1);
//                    String value = new String(value1);
//                    String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//                        .format(consumerRecord.timestamp());
//                    MyConsumerRecord myConsumerRecord = new MyConsumerRecord(key, value, timeStamp,
//                        headerMap);
//                    System.out.println(myConsumerRecord);
//                    return myConsumerRecord;
//                }
//
//                @Override
//                public TypeInformation<MyConsumerRecord> getProducedType() {
//                    return TypeInformation.of(MyConsumerRecord.class);
//                }
//            },
//            properties
//        );
//    }
    @Test
    public void newData() throws IOException {

        for (int i=1;i<5;i++){
                SdkInfo sdk = new SdkInfo();
                sdk.setIp("2");
                OpSubGameModel opSubGameModel = new OpSubGameModel();
                opSubGameModel.setGameId(18);
                sdk.setOpSubGameModel(opSubGameModel);
                SdkContext.setSdkInfo(sdk);

                SdkStartDataDto sdkStartDataDto = new SdkStartDataDto();
                sdkStartDataDto.setDealId(22);
                sdkStartDataDto.setSubGameId(22);
                sdkStartDataDto.setDeviceId(String.valueOf(i*i));
                sdkStartDataDto.setPkgId(9);
                Runtime.getRuntime().exec("cmd /c date 2023-02-19");
                Runtime.getRuntime().exec("cmd /c time 22:35:00");
                sdkStartDataDto.setUniqueId(String.valueOf(i*i));
                sdkStartDataDto.setAndroidId("1");
                sdkStartDataDto.setDevModel("2");
                sdkStartDataDto.setDevOs("3");
                sdkStartDataDto.setDevOsVer("4");
                sdkStartDataDto.setFirstActive(1);
                sdkStartDataDto.setPkgName("5");
                sdkStartDataDto.setPkgVersionCode("6");
                sdkStartDataDto.setPkgVersionName("7");
                sdkStartDataDto.setSerialId("89");
//                sdkGameController.start(sdkStartDataDto);

//                SdkRegisterDto sdkRegisterDto = new SdkRegisterDto();
//                sdkRegisterDto.setRegisterType(2);
//                sdkRegisterDto.setDealId(22);
//                sdkRegisterDto.setDevice(String.valueOf(i*i));
//                sdkRegisterDto.setSubGameId(22);
//                sdkRegisterDto.setUserName("a44444"+String.valueOf(i*i));
//                sdkRegisterDto.setUserPassword("1234567");
//                sdkRegisterDto.setPkgId(9);
//                SdkResult<SdkUserRegisterRes> resultRegister = sdkUserController.register(sdkRegisterDto);

                SdkLoginDto sdkLoginDto = new SdkLoginDto();
                sdkLoginDto.setDealId(22);
                sdkLoginDto.setDevice(String.valueOf(i*i));
                sdkLoginDto.setName("a44444"+String.valueOf(i*i));
                sdkLoginDto.setSubGameId(22);
                sdkLoginDto.setPkgId(9);

                SdkResult<SdkUserLoginRes> login = sdkUserController.login(sdkLoginDto);

                SdkAliveDto sdkAliveDto = new SdkAliveDto();
                sdkAliveDto.setDealId(22);
                sdkAliveDto.setDevice(String.valueOf(i*i));
                sdkAliveDto.setGameId(18);
                sdkAliveDto.setPkgId(9);
                sdkAliveDto.setRoleId(String.valueOf(i*i));
                sdkAliveDto.setRoleLevel(5);
                sdkAliveDto.setRoleName(String.valueOf(i*i));
                sdkAliveDto.setServerId(2);
                sdkAliveDto.setServerName("2福");
                sdkAliveDto.setUserId(login.getData().getUserId());
                sdkAliveDto.setSubGameId(22);
                sdkAliveDto.setChannelId(1);
                sdkAliveDto.setSubChannelId(1);
                sdkAliveDto.setUserName("a44444"+String.valueOf(i*i));

                sdkUserController.alive(sdkAliveDto);

        }
    }
}
