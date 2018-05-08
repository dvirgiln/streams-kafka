# Spark Scala template

The Spark Scala template image serves as a base image to build your own Scala
application to run on a Spark cluster. See
[big-data-europe/docker-spark README](https://github.com/big-data-europe/docker-spark)
for a description how to setup a Spark cluster.

#### Steps to extend the Spark Scala template

1. Create a Dockerfile in the root folder of your project (which also contains
   a `build.sbt`)
2. Extend the Spark Scala template Docker image
3. Configure the following environment variables (unless the default value
   satisfies):
  * `SPARK_MASTER_NAME` (default: spark-master)
  * `SPARK_MASTER_PORT` (default: 7077)
  * `SPARK_APPLICATION_MAIN_CLASS` (default: Application)
  * `SPARK_APPLICATION_ARGS` (default: "")
