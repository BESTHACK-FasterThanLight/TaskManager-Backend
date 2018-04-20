FROM ubuntu:16.04

MAINTAINER besthack

# UPDATE_POST package list
RUN apt-get -y update

#postgresql
ENV PGVER 9.5
RUN apt-get install -y postgresql-$PGVER

# Run the rest of the commands as the ``postgres`` user created by the ``postgres-$PGVER`` package when it was ``apt-get installed``
USER postgres

RUN /etc/init.d/postgresql start &&\
    psql --command "CREATE USER besthack WITH SUPERUSER PASSWORD 'besthack';" &&\
    createdb -E UTF8 -T template0 -O besthack besthack &&\
    /etc/init.d/postgresql stop

# Adjust PostgreSQL configuration so that remote connections to the
# database are possible.
RUN echo "host all  all    0.0.0.0/0  md5" >> /etc/postgresql/$PGVER/main/pg_hba.conf

# And add ``listen_addresses`` to ``/etc/postgresql/$PGVER/main/postgresql.conf``
RUN echo "listen_addresses='*'" >> /etc/postgresql/$PGVER/main/postgresql.conf
RUN echo "synchronous_commit = off" >> /etc/postgresql/$PGVER/main/postgresql.conf

# Expose the PostgreSQL port
EXPOSE 5432

# Add VOLUMEs to allow backup of config, logs and databases
VOLUME  ["/etc/postgresql", "/var/log/postgresql", "/var/lib/postgresql"]

# Back to the root user
USER root

# JDK
RUN apt-get install -y openjdk-8-jdk-headless
RUN apt-get install -y maven

# copy to Docker container
ENV WORK /opt/taskmanger
ADD ./ $WORK/

# build and run
WORKDIR $WORK
RUN mvn package

# port
EXPOSE 5000

#
# start
#
CMD service postgresql start && java -Xmx300M -Xmx300M -jar $WORK/target/taskmanger-0.0.1-SNAPSHOT.jar