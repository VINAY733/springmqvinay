mvn clean install

mvn clean install -DskipTests

docker build -t spring-kafka-consumer:latest .

docker build -f .\DockerfileSimple -t spring-rabbit-consumer:latest .
docker build -f .\DockerfileComplex -t spring-rabbit-consumer:latest .
---
kubectl apply -f spring-rabbitmq-sidecar.yaml
kubectl get pods
kubectl get svc
kubectl logs -f deployment/spring-rabbit-sidecar -c spring-app

kubectl delete -f spring-rabbitmq-sidecar.yaml


---
springboot version and spring cloud version 		
<version>3.4.5</version>
<spring-cloud.version>2024.0.3</spring-cloud.version>


<version>3.5.9</version>
<spring-cloud.version>2025.0.1</spring-cloud.version>
---
rabbit UI 

http://localhost:31672/

sample message:
{
"id": 1,
"name": "Vinay"
}
