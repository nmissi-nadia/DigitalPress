#!/bin/bash

echo "Building DigitalPress Microservices..."

# Build all services
cd /app
mvn clean package -DskipTests

echo "Build completed. Services are ready to run."
