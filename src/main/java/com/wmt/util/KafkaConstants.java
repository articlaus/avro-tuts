package com.wmt.util;

/**
 * Created by: Arti
 * On: 6/7/2019
 * Project: avrotest
 */
public interface KafkaConstants {
    public static String KAFKA_BROKERS = "kafka:9092";
    public static Integer MESSAGE_COUNT = 1000;
    public static String CLIENT_ID = "client1";
    public static String TOPIC_NAME = "serde";
    public static String GROUP_ID_CONFIG = "foo";
    public static Integer MAX_NO_MESSAGE_FOUND_COUNT = 100;
    public static String OFFSET_RESET_LATEST = "latest";
    public static String OFFSET_RESET_EARLIER = "earliest";
    public static Integer MAX_POLL_RECORDS = 1;
}
