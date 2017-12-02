# spring-kafka-demo
demo spring boot app with kafka;
открыть папку с кафкой в терминале;
в терминале: ./bin/zookeeper-server-start.sh configookeeper.properties   //запускаем zookeeper;
открыть папку с кафкой в терминале;
в терминале: ./bin/kafka-server-start.sh config/server.properties        //запускаем кафка сервер;
открыть папку с кафкой в терминале;
в терминале: ./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
      //создали топик test;
прописать (проверить,прописан ли он) этот топик в spring boot в application.properties (spring.kafka.template.default-topic=test);
в открытом терминале: ./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test -- from-begining
     //запускаем consumer;
проверить в spring boot в application.properties (spring.kafka.bootstrap-servers=localhost:9092);
запускаем приложение;
через Postman отправить сообщение;
наслаждаемся им в консоли;
