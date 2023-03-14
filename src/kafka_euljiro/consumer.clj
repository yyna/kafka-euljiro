(ns kafka-euljiro.consumer
  (:require [clojure.data.json :as json]
            [clojure.walk :as walk])
  (:import [org.apache.kafka.clients.consumer KafkaConsumer]))

(defn consume!
  [consumer topic]
  (.subscribe consumer (java.util.ArrayList. [topic])))

(comment
  @(def consumer (KafkaConsumer. (-> {:bootstrap.servers "localhost:29092"
                                      :key.deserializer    "org.apache.kafka.common.serialization.StringDeserializer"
                                      :value.deserializer  "org.apache.kafka.common.serialization.StringDeserializer"
                                      :group.id "euljiro-kafka-jungin-1"}
                                     (walk/stringify-keys))))

  ;; topic 이름
  @(def topic-name "euljiro-kafka-jungin")


  ;; Message consume하기
  (consume! consumer topic-name)

  (for [record (.poll consumer 100)]
    (prn (json/read-str (.value record))))

  :rcf)
