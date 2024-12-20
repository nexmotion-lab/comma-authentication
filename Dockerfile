FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /app

RUN apk add --no-cache dos2unix ca-certificates
COPY gradlew .
RUN dos2unix gradlew
RUN chmod +x gradlew
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
RUN chmod +x ./gradlew

RUN ./gradlew --version
RUN ./gradlew dependencies

COPY src src
RUN ./gradlew build --no-daemon

FROM eclipse-temurin:17-jdk-alpine as jre-build
COPY --from=build /app/build/libs/*.jar /app/app.jar
RUN jar xf /app/app.jar
RUN $JAVA_HOME/bin/jdeps \
    --ignore-missing-deps \
    --multi-release 17 \
    --recursive \
    --print-module-deps \
    --class-path 'BOOT-INF/lib/*' \
    /app/app.jar > /app/deps.info

RUN $JAVA_HOME/bin/jlink \
    --add-modules $(cat /app/deps.info),java.base,jdk.crypto.ec \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /javaruntime

FROM alpine:3.15
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"
ENV JAVA_OPTS="-Dhttps.protocols=TLSv1.2 -Djavax.net.debug=all"

RUN apk update && apk add --no-cache ca-certificates openjdk11-jre-headless

RUN update-ca-certificates

COPY --from=jre-build /javaruntime $JAVA_HOME
WORKDIR /app
COPY --from=jre-build /app/app.jar /app/app.jar
COPY opentelemetry-javaagent.jar /workspace/BOOT-INF/lib/opentelemetry-javaagent-1.33.3.jar

CMD ["java", "-jar", "/app/app.jar"]
