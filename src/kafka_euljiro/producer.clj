(ns kafka-euljiro.producer
  (:require
   [clojure.data.json :as json]
   [medley.core :refer [update-existing]]
   [clojure.walk :as walk])
  (:import
   (org.apache.kafka.clients.producer KafkaProducer ProducerRecord)
   [org.apache.kafka.clients.consumer KafkaConsumer]))


(defn produce!
  [producer topic message]
  (.send producer (ProducerRecord. topic (json/write-str message))))

(defn consume!
  [consumer topic]
  (.subscribe consumer (java.util.ArrayList. [topic])))

(comment
  ;; KafkProducer 만들기
  @(def producer (KafkaProducer. (-> {:bootstrap.servers "localhost:29092"
                                      :key.serializer    "org.apache.kafka.common.serialization.StringSerializer"
                                      :value.serializer  "org.apache.kafka.common.serialization.StringSerializer"
                                      :acks              "all"
                                      :retries           3}
                                     (update-existing :retries int)
                                     (walk/stringify-keys))))

  @(def consumer (KafkaConsumer. (-> {:bootstrap.servers "localhost:29092"
                                      :key.deserializer    "org.apache.kafka.common.serialization.StringDeserializer"
                                      :value.deserializer  "org.apache.kafka.common.serialization.StringDeserializer"
                                      :group.id "euljiro-kafka-jungin-1"}
                                     (update-existing :retries int)
                                     (walk/stringify-keys))))

  ;; topic 이름
  @(def topic-name "euljiro-kafka-jungin")


  ;; Message produce하기
  (produce! producer topic-name {:hello :world})
  (consume! consumer topic-name)

  (for [record (.poll consumer 100)]
    (prn (json/read-str (.value record))))

  :rcf)
