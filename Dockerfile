ARG ALPINE_VERSION=3.13
ARG GECKO_DRIVER_VERSION=0.29.1
ARG EXTRACTOR_VERSION=1.0.0

FROM alpine:$ALPINE_VERSION
ARG GECKO_DRIVER_VERSION
ARG EXTRACTOR_VERSION

RUN apk add --no-cache openjdk8-jre
RUN apk add --no-cache firefox
RUN mkdir /extractor
ADD https://github.com/mozilla/geckodriver/releases/download/v$GECKO_DRIVER_VERSION/geckodriver-v$GECKO_DRIVER_VERSION-linux64.tar.gz /extractor/
WORKDIR /extractor
RUN tar -xzvf ./geckodriver-v$GECKO_DRIVER_VERSION-linux64.tar.gz
RUN rm ./geckodriver-v$GECKO_DRIVER_VERSION-linux64.tar.gz
ADD target/sot-rat-extractor-$EXTRACTOR_VERSION-jar-with-dependencies.jar /extractor/sot-rat-extractor.jar

ENTRYPOINT ["java", "-jar", "/extractor/sot-rat-extractor.jar"]