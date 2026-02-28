# ── 1-bosqich: Build ──────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Avval faqat pom.xml ko'chiramiz (cache uchun)
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Asosiy kodni ko'chiramiz va build qilamiz
COPY src ./src
RUN mvn package -DskipTests -B

# ── 2-bosqich: Run ─────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 4000

ENTRYPOINT ["java" , "-Xms64m" , "-Xmx256m" , "-jar" , "app.jar"]
