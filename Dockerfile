FROM eclipse-temurin:17-alpine

ARG PORT=9090
ARG ARTIFACT=exampleapi
ARG VERSION=0.0.1-SNAPSHOT
ARG ENVIRONMENT=develop
ARG TIMEZONE=UTC

ENV APP_ARTIFACT=$ARTIFACT
ENV APP_VERSION=$VERSION
ENV APP_ENVIRONMENT=$ENVIRONMENT
ENV APP_TIMEZONE=$TIMEZONE

VOLUME /tmp
RUN apk -U upgrade
RUN apk add curl #Added curl for container debugging
EXPOSE $PORT
ADD /target/$ARTIFACT-$VERSION.jar app.jar

ENTRYPOINT ["java","-jar"]
CMD ["-Dspring.profiles.active=${APP_ENVIRONMENT}","-Duser.timezone=${APP_TIMEZONE}","/app.jar"]
