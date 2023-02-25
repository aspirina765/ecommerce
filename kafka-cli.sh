#!/usr/bin/env bash

# mvn compile -DskipTests=true

current_path="$(pwd)" ;
cd "${current_path}" ;

conduktor_bootstrap_server=cluster.playground.cdkt.io:9092 ;
confluent_cloud_bootstrap_server=pkc-ymrq7.us-east-2.aws.confluent.cloud:9092 ;
conduktor_config_file=conduktor-client.properties ;
confluent_cloud_config_file=confluent_cloud-client.properties ;


#kafka-topics --command-config "${current_path}/conduktor-client.properties" --bootstrap-server cluster.playground.cdkt.io:9092 --list ;

#kafka-topics --command-config "${current_path}/conduktor-client.properties" --bootstrap-server cluster.playground.cdkt.io:9092 --create --topic ECOMMERCE_NEW_ORDER --partitions 3 ;
#kafka-topics --command-config "${current_path}/conduktor-client.properties" --bootstrap-server cluster.playground.cdkt.io:9092 --create --topic ECOMMERCE_ORDER_APPROVED --partitions 3 ;
#kafka-topics --command-config "${current_path}/conduktor-client.properties" --bootstrap-server cluster.playground.cdkt.io:9092 --create --topic ECOMMERCE_ORDER_REJECTED --partitions 3 ;
#kafka-topics --command-config "${current_path}/conduktor-client.properties" --bootstrap-server cluster.playground.cdkt.io:9092 --create --topic ECOMMERCE_SEND_EMAIL --partitions 3 ;


#kafka-topics --command-config "${current_path}/conduktor-client.properties" --bootstrap-server cluster.playground.cdkt.io:9092 --alter --topic ECOMMERCE_NEW_ORDER --partitions 3 ;
#kafka-topics --command-config "${current_path}/conduktor-client.properties" --bootstrap-server cluster.playground.cdkt.io:9092 --alter --topic ECOMMERCE_ORDER_APPROVED --partitions 3 ;
#kafka-topics --command-config "${current_path}/conduktor-client.properties" --bootstrap-server cluster.playground.cdkt.io:9092 --alter --topic ECOMMERCE_ORDER_REJECTED --partitions 3 ;
#kafka-topics --command-config "${current_path}/conduktor-client.properties" --bootstrap-server cluster.playground.cdkt.io:9092 --alter --topic ECOMMERCE_SEND_EMAIL --partitions 3 ;

kafka-topics --command-config "${current_path}/conduktor-client.properties" --bootstrap-server cluster.playground.cdkt.io:9092 --list
#    ECOMMERCE_NEW_ORDER
#    ECOMMERCE_ORDER_APPROVED
#    ECOMMERCE_ORDER_REJECTED
#    ECOMMERCE_SEND_EMAIL

