#!/bin/bash
KAFKA_DEFAULT_PORT=9092
SPARK_DEFAULT_PORT=7077
kubectl create -f 0_zookeeper.yml
kubectl create -f 1_kafka_broker_wurstmeister.yml
#kubectl create -f 1_kafka_broker_spotify.yml
export KAFKA_BROKER_PORT=$(echo $(kubectl get svc kafka-broker-svc -o go-template='{{range.spec.ports}}{{.nodePort}}{{"-"}}{{.targetPort}}{{"\n"}}{{end}}') | tr " " "\n" | grep $KAFKA_DEFAULT_PORT | sed 's/-.*//')
export KAFKA_BROKER_HOST=$(echo $(minikube service kafka-broker-svc --url) | awk 'BEGIN{FS=OFS=" "}{print $1}' | sed 's/http:\/\///' | sed 's/:.*//')
echo "KAFKA_BROKER_HOST: $KAFKA_BROKER_HOST PORT: $KAFKA_BROKER_PORT"
kubectl create -f 2_spark_master.yml
export SPARK_MASTER_PORT=$(echo $(kubectl get svc spark-master-svc -o go-template='{{range.spec.ports}}{{.nodePort}}{{"-"}}{{.targetPort}}{{"\n"}}{{end}}') | tr " " "\n" | grep $SPARK_DEFAULT_PORT | sed 's/-.*//')
export SPARK_MASTER_HOST=$(echo $(minikube service spark-master-svc --url) | awk 'BEGIN{FS=OFS=" "}{print $1}' | sed 's/http:\/\///' | sed 's/:.*//')
echo "SPARK_MASTER_HOST: $SPARK_MASTER_HOST PORT: $SPARK_MASTER_PORT"

cat 3_spark_workers.yml | \
sed "s/{{SPARK_MASTER_PORT}}/$SPARK_MASTER_PORT/g" | \
sed "s/{{SPARK_MASTER_HOST}}/$SPARK_MASTER_HOST/g" | \
kubectl apply -f -

cat 4_akka_producer.yml | \
sed "s/{{KAFKA_BROKER_PORT}}/$KAFKA_BROKER_PORT/g" | \
sed "s/{{KAFKA_BROKER_HOST}}/$KAFKA_BROKER_HOST/g" | \
kubectl apply -f -

cat create -f 5_akka_consumer.yml | \
sed "s/{{KAFKA_BROKER_PORT}}/$KAFKA_BROKER_PORT/g" | \
sed "s/{{KAFKA_BROKER_HOST}}/$KAFKA_BROKER_HOST/g" | \
kubectl apply -f -

