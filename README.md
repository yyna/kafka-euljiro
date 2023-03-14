# 카프카 찍먹
where? 을지로  
when? 2023-03-14

## 로컬에서 실행해보기

#### 컨테이너 띄우기 (kafka, zookeeper)
```bash
docker-compose up
```

#### 토픽 만들기
```bash
docker-compose exec kafka kafka-topics --create --topic euljiro-kafka-jungin --bootstrap-server kafka:9092 --replication-factor 1 --partitions 1
```

#### 토픽 확인
```bash
docker-compose exec kafka kafka-topics --describe --topic euljiro-kafka-jungin --bootstrap-server kafka:9092 
```

#### 메시지 발행해보기
[kafka-euljiro.producer](./src/kafka_euljiro/producer.clj)

#### 메시지 받아보기
```bash
docker-compose exec kafka bash
kafka-console-consumer --topic euljiro-kafka-jungin --bootstrap-server kafka:9092
```
