#Weather Reporting

Weather Reporting App is majorly built on the top of following frameworks:

* **Spring** - Spring is used for dependency injection.
* **Apache CXF** - Apache CXF is used to create REST aware services and clients.
* **Jackson** - Jackson is used to serialize/deserialize JSON structure.
* **Jetty** - Jetty is used as embedded HTTP server to serve REST aware services.
* **Kafka** - Kafka is used for pub-sub model.

# Build

**Java 8** and **Maven 3.x** should be available on the system to build the artifact.

Run the following command to build the project.

```
mvn clean install
```

## How to Configure

Project follow the YAML based configuration to load the properties in object oriented manner. Below is the default configuration located at _**config/main/resources/app-config.yaml**_

```yaml
    kafka:
      - id: weather-queue
        zookeeper-quorum: ["0.0.0.0:9092"]
        kafka-brokers: ["0.0.0.0:9092"]

    wunderground:
      api-url: http://api.wunderground.com/api
      api-key: 12345

    rdbms:
      - id: weather-reporting-db
        host: localhost
        port: 3307
        db-name: weather
        username: su
        password:
        min-pool-size: 50
        max-pool-size: 100

    weather-config:
      countries:
        - name: US
          topic-name: us-weather
          cities:
            - name: San_Fransisco
              polling-interval: 60000
            - name: San_Fransisco
              polling-interval: 60000
        - name: INDIA
          topic-name: india-weather
          cities:
            - name: Gurgaon
              polling-interval: 60000
            - name: Delhi
              polling-interval: 60000

```

# Project Structure

Application follows multi module architecture to decouple the components at runtime.

## Platform - platform

This module is base of the weather-reporting application. It build the YAML config and help in following IoC.
* Kafka pub-sub
* HTTP Rest Client
* Spring bootstrap to start the application in standalone mode.

### Config Loader

**Entry Point:** ConfigReader.java

This component is responsible to load the YAML config. Loader convert the YAML into an OO view.

### Rest Client

**Entry Point:** HTTPClientProxy.java & DefaultHTTPClientProxy.java

This component is used to create the REST client for REST aware service.

#### How to use

Create the ClientProxyBuilder and provide the URL and Type of the interface name.

```java
        proxy.createProxy(wunderground.getUrl(), WundergroundApi.class);
```

## db-repository
This layer is responsible to provide to DB specific operation over MYSQL database. It makes use of Spring provided simple JdbcTemplate. Later it can be improved further with hibernate or JPA.

## Wunderground-client
This component implement the HTTP REST client for Wunderground API. Look at WundergroundApi.java and WundergroundClientConfig.java for more detail.

## Supervisor
This component expose the following interfaces and provide the respective implementation.

### SupervisorScheduler
* It's implementation is *DefaultSupervisorScheduler*.
* It is responsible to schedule/reschedule a supervisor specific to a country and city.
* It makes use of JDK timer task to periodic schedule the supervisor.

### WeatherSupervisor
* It's implementation is *WundergroundSupervisor*
* Each supervisor object poll the Wunderground API specific to a country and City.
* Wunderground Response is pushed into Kafka queue/topic.

### Runner
* Runner starts all configured in supervisors in standalone mode.
* Runner loads spring java config *SupervisorConfig*  to start all the configured supervisors.
* Number of countries and cities are hardcoded and loaded from the configuration to create all respective supervisors.

## Worker
* This component is responsible to create and execute country specific worker.
* Multiple runtime of this component can be created to process the conutry specific weather.
* It takes the country from System property to consume the country specific weather response by *CountryWeatherWorker*.

## ui
This component expose the REST interface as well as UI layer to display the weather details on UI.

* WeatherAPI: It is exposed using CXF JaX-RS configuration in ui-bootstrap.xml.
* index.jsp: TODO



