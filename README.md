# Spring Messaging Playground (RabbitMQ Sidecar + Kafka Local)

This repository demonstrates **Spring Boot message consumers** using:

* 🐰 **RabbitMQ as a sidecar container** (Kubernetes – single Pod)
* 📨 **Kafka via Docker Compose** (local development only)

The goal is to clearly separate:

* **RabbitMQ → Kubernetes (sidecar / multi‑pod)**
* **Kafka → Local Docker Compose**

---

## 🛠 Prerequisites

* Java 17+
* Maven 3.9+
* Docker Desktop
* Kubernetes enabled in Docker Desktop
* kubectl

---

## 🔧 Build the Spring Boot Application

### Standard build

```bash
mvn clean install
```

### Skip tests (recommended for Docker builds)

```bash
mvn clean install -DskipTests
```

> ✅ Ensure the JAR is generated under `target/`

---

## 🐳 Docker Image Build

### Default Dockerfile

```bash
docker build -t spring-cloud-consumer:latest .
```

### Custom Dockerfiles

```bash
docker build -f .\\DockerfileSimple -t spring-cloud-consumer:latest .
docker build -f .\\DockerfileComplex -t spring-cloud-consumer:latest .

# Alternative tags
docker build -f .\\DockerfileSimple -t spring-rabbit-consumer:latest .
docker build -f .\\DockerfileComplex -t spring-rabbit-consumer:latest .
```

---

## ☸️ Kubernetes – RabbitMQ Sidecar Setup

RabbitMQ runs **inside the same Pod** as the Spring Boot application.

### Deploy

```bash
kubectl apply -f spring-cloud-sidecar.yaml
```

### Verify

```bash
kubectl get pods
kubectl get svc
```

### View Spring Boot logs

```bash
kubectl logs -f deployment/spring-cloud-sidecar -c spring-app
```

### Cleanup

```bash
kubectl delete -f spring-cloud-sidecar.yaml
```

---

## 🐰 RabbitMQ Management UI

### Option 1: NodePort (current setup)

```text
http://localhost:31672/
```

Credentials:

* Username: `guest`
* Password: `guest`

---

### Option 2: Port‑forward (recommended)

```bash
kubectl port-forward svc/spring-cloud-sidecar 15672:15672
```

Access:

```text
http://localhost:15672
```

✔ No NodePort exposure
✔ Stops automatically when terminal closes

---

## 📩 Sample RabbitMQ Message

```json
{
  "id": 1,
  "name": "Vinay"
}
```

---

## 🧪 RabbitMQ – Two Separate Pods (No Sidecar)

### Deploy

```bash
kubectl apply -f rabbitmq.yaml
kubectl apply -f spring-app.yaml
```

### View logs

```bash
kubectl logs -f deployment/spring-rabbit-consumer -c spring-consumer
```

### Cleanup

```bash
kubectl delete -f rabbitmq.yaml
kubectl delete -f spring-app.yaml
```

---

## 🐘 Kafka – Local Setup (Docker Compose Only)

⚠️ Kafka is **NOT** deployed as a sidecar. It runs **locally using Docker Compose**.

---

### Step 1: Start Kafka & Zookeeper

```bash
docker compose -f dockercompose-kafka.yaml up -d
```

---

### Step 2: Enter Kafka container

```bash
docker exec -it kafka bash
```

---

### Step 3: List topics

```bash
kafka-topics.sh --zookeeper zookeeper:2181 --list
```

---

### Step 4: Create topic (if not present)

```bash
kafka-topics.sh \
  --zookeeper zookeeper:2181 \
  --create \
  --topic employeetopic \
  --partitions 1 \
  --replication-factor 1
```

---

### Step 5: Produce message

```bash
kafka-console-producer.sh \
  --broker-list localhost:9092 \
  --topic employeetopic
```

---

### Step 6: Send JSON message

```json
{"id":1,"name":"kafka"}
```

Each line = **one Kafka message**

---

## 📦 Spring Versions

### Option 1 (Stable – Recommended)

```xml
<version>3.4.5</version>
<spring-cloud.version>2024.0.3</spring-cloud.version>
```

### Option 2 (Latest)

```xml
<version>3.5.9</version>
<spring-cloud.version>2025.0.1</spring-cloud.version>
```

---

## 📌 Notes & Best Practices

* Sidecar RabbitMQ is ideal for **local/dev environments**
* No PVC required unless message durability across pod restarts is needed
* For production:

  * Use external RabbitMQ or StatefulSet
  * Disable `guest` user
  * Enable TLS & authentication

---

## ✅ Quick Flow Summary

### RabbitMQ (Sidecar)

1. Build JAR
2. Build Docker image
3. Deploy sidecar YAML
4. Access RabbitMQ UI
5. Publish message → observe logs

### Kafka (Local)

1. Start Docker Compose
2. Create topic
3. Produce JSON message
4. Consume via Spring app

---

Happy messaging 🚀
