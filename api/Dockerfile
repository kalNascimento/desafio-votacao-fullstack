FROM gradle:8.5-jdk21-alpine AS build

WORKDIR /app

COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle

RUN chmod +x gradlew

RUN ./gradlew dependencies --no-daemon || true

COPY . .

RUN ./gradlew clean bootJar -x test --no-daemon


FROM amazoncorretto:21-alpine3.21-jdk

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
