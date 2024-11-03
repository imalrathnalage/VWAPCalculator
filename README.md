# VWAPCalculator

VWAPCalculator is a Java Spring Boot application that calculates the Volume-Weighted Average Price (VWAP) over an hourly window for multiple currency pairs based on a stream of price data. 

### Features
- **Real-time VWAP calculation**: Calculates VWAP for each unique currency pair based on an hourly window.
- **Token-based authorization**: Ensures secure access to the API.

### Technologies Used
- Java
- Spring Boot
- Maven
- JUnit for testing

### Prerequisites
- **Java 17** or higher
- **Maven** for dependency management
- **Git** for cloning the repository

## Setup and Installation

#### Step 1: Clone the Repository
To clone the repository, use:
```bash
git clone https://github.com/imalrathnalage/VWAPCalculator.git

#### Step 2: Navigate to the Project Directory
cd VWAPCalculator

#### Step 3: Install Dependencies
Ensure dependencies are installed with Maven:
mvn clean install

## Configuration

Edit the application.properties file in src/main/resources to configure the following settings:

# Server port
server.port=9090

# VWAP time window in minutes
vwap.time-window.minutes=60

# Request-response logging toggle
logging.request-response.enabled=false

# Authorization tokens for the API
vwap.api.tokens=token1,token2,token3,token4,token5


Authorization
This API uses token-based authorization. Include a valid token in the Authorization header of each request:

Authorization: Bearer token1

## How to Run

#### Step 1: Start the Application
To run the application, use:
mvn spring-boot:run

Alternatively, you can build a JAR file and run it:

mvn clean package
java -jar target/VWAPCalculator-0.0.1-SNAPSHOT.jar

#### Step 2: Access the Application
Once running, the application is accessible at:

http://localhost:9090

## API Documentation
Calculate VWAP
Endpoint: POST /api/vwap/calculate
Description: Accepts price data and returns the VWAP for a given currency pair.

Request Body Example:

{
    "timestamp": "2024-11-03T12:00:00",
    "currencyPair": "AUD/USD",
    "price": 0.75,
    "volume": 100
}

## Reset VWAP Data
Endpoint: POST /api/vwap/reset
Description: Resets VWAP data for a specific currency pair.

{
    "currencyPair": "AUD/USD"
}

## Response Example:

{
    "message": "VWAP data reset for AUD/USD"
}

## Testing
To run the unit tests, use:

mvn test
This will execute JUnit tests for boundary cases, invalid data handling, and authorization checks.
