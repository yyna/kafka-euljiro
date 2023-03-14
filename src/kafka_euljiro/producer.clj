(ns kafka-euljiro.producer
  (:require
   [clojure.data.json :as json]
   [medley.core :refer [update-existing]]
   [clojure.walk :as walk])
  (:import
   (org.apache.kafka.clients.producer KafkaProducer ProducerRecord)))


(defn produce!
  [producer topic message]
  (.send producer (ProducerRecord. topic (json/write-str message))))

(comment
  ;; KafkProducer 만들기
  @(def producer (KafkaProducer. (-> {:bootstrap.servers "localhost:29092"
                                      :key.serializer    "org.apache.kafka.common.serialization.StringSerializer"
                                      :value.serializer  "org.apache.kafka.common.serialization.StringSerializer"
                                      :acks              "all"
                                      :retries           3}
                                     (update-existing :retries int)
                                     (walk/stringify-keys))))

  ;; topic 이름
  @(def topic-name "kafka-euljiro-jungin")


  ;; Message produce하기
  (produce! producer topic-name {:hello :world})

  :rcf)
