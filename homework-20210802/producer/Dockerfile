FROM openjdk:16-alpine3.13
ENV TZ=Europe/Moscow
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ADD target/producer-*.jar /app/
WORKDIR /app
RUN ln -s producer-*.jar producer.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "producer.jar"]