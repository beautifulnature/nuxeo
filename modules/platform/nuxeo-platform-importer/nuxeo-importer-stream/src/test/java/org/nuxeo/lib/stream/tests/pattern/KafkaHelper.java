/*
 * (C) Copyright 2016 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.nuxeo.lib.stream.tests.pattern;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.nuxeo.lib.stream.log.kafka.KafkaUtils;

public class KafkaHelper {

    public static final String TOPIC_PREFIX = "nuxeo-test";

    public static String getPrefix() {
        return TOPIC_PREFIX + "-" + System.currentTimeMillis() + "-";
    }

    public static Properties getProducerProps() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaUtils.getBootstrapServers());
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 10_000);
        return props;
    }

    public static Properties getConsumerProps() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaUtils.getBootstrapServers());
        props.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 30_000);
        // consumer are removed from a group if there more than this interval between poll
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 120_000);
        // session timeout, consumer is removed from a group if there is no heartbeat on this interval
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10000);
        // short ht interval so that rebalance don't take for ever
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 400);
        // keep number low to reduce time interval between poll
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);
        return props;
    }

}
