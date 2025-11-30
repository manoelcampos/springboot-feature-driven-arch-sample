# App dependencies download stage
# https://vsupalov.com/5-tips-to-speed-up-docker-build/
FROM maven:3-amazoncorretto-23 AS deps
ENV WORKDIR=/api
ENV MAVEN_REPO=$WORKDIR/.m2
WORKDIR $WORKDIR
COPY pom.xml .
RUN mvn -Dmaven.repo.local=$MAVEN_REPO dependency:go-offline

# App build stage
FROM deps as build
WORKDIR $WORKDIR
COPY . .
COPY --from=deps $MAVEN_REPO .

# Ignore tests since they are run on the CI build workflow
RUN mvn -Dmaven.repo.local=$MAVEN_REPO package -DskipTests

# App copy stage
FROM amazoncorretto:23-alpine
WORKDIR $WORKDIR
RUN apk add curl
COPY --from=build /api/target/springboot-reusable-arch-sample-*.jar "./sample-api.jar"
EXPOSE 8080
CMD [ "java", "-jar", "sample-api.jar" ]
