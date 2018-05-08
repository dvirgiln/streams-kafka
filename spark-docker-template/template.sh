#!/bin/bash

SPARK_APPLICATION_JAR_LOCATION=$SPARK_APPLICATION_JAR_LOCATION
export SPARK_APPLICATION_JAR_LOCATION

if [ -z "$SPARK_APPLICATION_JAR_LOCATION" ]; then
	echo "Can't find a file *-assembly-*.jar"
	exit 1
fi
echo "before submit"
cd /
ls
chmod +x /submit.sh
/submit.sh
