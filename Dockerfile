# ==========================================
# Stage 1: Build
# ==========================================
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy Maven wrapper and pom.xml first (better layer caching —
# dependencies re-download only when pom.xml changes, not on every code change)
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

# Now copy the rest of the source and build
COPY src ./src
RUN ./mvnw clean package -DskipTests -B

# ==========================================
# Stage 2: Run
# ==========================================
FROM eclipse-temurin:21-jre AS run

WORKDIR /app

# Run as non-root user for security
RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring:spring

# Copy only the built jar from the build stage (keeps final image small)
COPY --from=build /app/target/*.jar app.jar

EXPOSE 4001

ENTRYPOINT ["java", "-jar", "app.jar"]