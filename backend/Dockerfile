FROM gradle:8.5-jdk17 AS build

WORKDIR /app

# Copy only the files needed for dependency installation
COPY build.gradle settings.gradle ./

# Install dependencies
RUN gradle dependencies --no-daemon

# Copy the rest of your application's source code
COPY . /app 

# Execute Gradle build, creating the JAR file
RUN gradle clean build --no-daemon

# Use OpenJDK 17 slim image for the runtime
FROM openjdk:17.0.1-jdk-slim

# Create the application's working directory
WORKDIR /app

# Create the directory for uploaded files
RUN mkdir -p /var/www/myapp/uploads

# Make sure the directory is writable by the container's user
# If your application runs as a non-root user, ensure that user owns the upload directory
# RUN adduser --disabled-password --gecos "" appuser && chown -R appuser:appuser /var/www/myapp/uploads
# USER appuser

# Copy the built JAR file from the build stage to the runtime container
COPY --from=build /app/build/libs/*.jar /app/B104.jar

# Expose the port the app runs on
EXPOSE 8080 

# Specify the entry point and default command
ENTRYPOINT ["java"] 
CMD ["-jar", "B104.jar"]
