# Application Documentation
- Create a gogs secret

Using the Commandline:

oc create secret generic gogs-secret --from-literal='username=bobby-cicd' --from-literal='password=mypassword'

or use the GUI

- Import the template

oc create -f kafka-detection-template.yaml

- Copy the src/main/resources/application.properties to the main directory and edit to adjust the values
- Create a config map of this application.properties file.

```
oc create cm application-properties --from-file=application.properties
oc set volume dc kafka-detection --add --type configmap --configmap-name application-properties --mount-path /deployments/config
```

- Create the application

```
oc new-app microservice -p APP_NAME=kafka-detection -p GIT_URL=https://gogs.13.250.237.11.nip.io/bobby-cicd/kafka-detection.git
```

- Test the application

```
curl -v -XPOST -H "Content-Type: application/json" http://kafka-detection.<project name>.svc:9080/addKafkaRecord -d '{ "dummy": "data" }'
```

Configure environment variable PARAMETERS = 
{
  "aerospike_host": "aerospike-server.aerospike.svc",
  "aerospike_port": "3000",
  "QUARKUS_HTTP_PORT": "9080",
  "TOPUP_PROCESS_URL": "http://jbpm-server-full.workflow.svc:8080/kie-server/services/rest/server/containers/topup_1.0.0-SNAPSHOT/processes/topup.topup/instances",
  "COLLECTOR_PROCESS_URL": "http://jbpm-server-full.workflow.svc:8080/kie-server/services/rest/server/containers/topup_1.0.0-SNAPSHOT/processes/topup.collector/instances"
}

# query-aerospike project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application is packageable using `./mvnw package`.
It produces the executable `query-aerospike-1.0-SNAPSHOT-runner.jar` file in `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/query-aerospike-1.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or you can use Docker to build the native executable using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your binary: `./target/query-aerospike-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image-guide .
