#!/bin/bash
KAFKA_DEFAULT_PORT=9092
SPARK_DEFAULT_PORT=7077
kubectl create -f 1_kafka_broker.yml
export BROKER_PORT=echo $(kubectl get svc kafka-broker-svc -o go-template='{{range.spec.ports}}{{.nodePort}}{{"-"}}{{.targetPort}}{{"\n"}}{{end}}') | tr " " "\n" | grep $KAFKA_DEFAULT_PORT | sed 's/-.*//'
export BROKER_HOST=echo $(minikube service kafka-broker-svc --url) | awk 'BEGIN{FS=OFS=" "}{print $1}' | sed 's/http:\/\///' | sed 's/:.*//'
echo "BROKER_HOST: $BROKER_HOST PORT: $BROKER_PORT"
kubectl create -f 2_spark_master.yml
export SPARK_MASTER_PORT=echo $(kubectl get svc spark-master-svc -o go-template='{{range.spec.ports}}{{.nodePort}}{{"-"}}{{.targetPort}}{{"\n"}}{{end}}') | tr " " "\n" | grep $SPARK_DEFAULT_PORT | sed 's/-.*//'
export SPARK_MASTER_HOST=echo $(minikube service spark-master-svc --url) | awk 'BEGIN{FS=OFS=" "}{print $1}' | sed 's/http:\/\///' | sed 's/:.*//'
echo "BROKER_HOST: $SPARK_MASTER_HOST PORT: $SPARK_MASTER_PORT"
kubectl create -f 3_spark_workers.yml
kubectl create -f 4_akka_producer.yml
kubectl create -f 5_akka_consumer.yml
