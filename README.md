# Ride-Sharing App

A Java Swing desktop ride-sharing application for IMAMU students and drivers.

## Features
- Student and driver registration with password validation
- Driver application and admin approval workflow
- Ride posting, booking, cancellation, and payment
- Emergency alert system
- Driver rating and review system

## Build & Run

**Requirements:** Java 11+, Maven 3.6+

```bash
# Compile and run tests
mvn test

# Build runnable JAR
mvn package

# Run the app
java -jar target/ride-sharing-jar-with-dependencies.jar
```

## CI/CD Pipeline

GitHub Actions runs on every push to `main`:

| Step | Description |
|------|-------------|
| Compile | `mvn compile` |
| Functional Tests | JUnit 5 — User, Ride, SecurityProvider behaviour |
| Non-Functional Tests | Performance under 1 000-item bulk load |
| Package | Fat-JAR with all dependencies bundled |
| Artifacts | JAR + Surefire test reports uploaded |

## Test Classes

| Class | Type | Coverage |
|-------|------|----------|
| `UserTest` | Functional | Registration, passwords, driver status, ratings |
| `RideTest` | Functional | Booking, cancellation, completion, ride requests |
| `SecurityProviderTest` | Functional | AES encryption correctness and Base64 output |
| `PerformanceTest` | Non-Functional | Bulk creation, search, and encryption timing |

## Default Demo Credentials

| Role | ID | Password |
|------|----|----------|
| Driver | `44110001` | `Pass123!` |
| Student | `44110002` | `Pass123!` |
| Admin | `admin` | `admin123` |
