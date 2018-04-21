FROM ubuntu:16.04
MAINTAINER besthack

# JDK
RUN apt-get update -qq && \
    apt-get install -qq -y openjdk-8-jdk-headless maven

# copy to Docker container
ENV WORK /opt/taskmanger
ADD ./ $WORK/

# build
WORKDIR $WORK
RUN mvn package

# port
EXPOSE 8081

# start
CMD java -Xmx300M -Xmx300M -jar $WORK/target/taskmanger-0.0.1-SNAPSHOT.jar
