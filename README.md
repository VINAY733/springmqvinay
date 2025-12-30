# Spring Boot RabbitMQ Consumer (Sidecar Setup)

This project demonstrates a **Spring Boot RabbitMQ consumer** running alongside **RabbitMQ as a sidecar container in a single Kubernetes Pod**.

---

## 🛠 Prerequisites

* Java 17+
* Maven 3.9+
* Docker Desktop (Kubernetes enabled)
* kubectl

---

## 🔧 Build the Application

### Standard build

```bash
mvn clean install
```

### Skip tests (recommended for faster Docker builds)

```bash
mvn clean install -DskipTests
```

> Ensure the JAR is created under `target/` before running Docker builds.

---

## 🐳 Docker Image Build

### Default Dockerfile

```bash
docker build -t spring-rabbit-consumer:latest .
```

### Custom Dockerfiles

```bash
docker build -f .\\DockerfileSimple -t spring-rabbit-consumer:latest .

docker build -f .\\DockerfileComplex -t spring-rabbit-consumer:latest .
```

> Use **DockerfileSimple** for local/dev and **DockerfileComplex** if you have multi-stage or advanced configs.

---

## ☸️ Kubernetes Deployment (RabbitMQ Sidecar)

### Apply deployment

```bash
kubectl apply -f spring-rabbitmq-sidecar.yaml
```

### Verify resources

```bash
kubectl get pods
kubectl get svc
```

### View Spring Boot logs

```bash
kubectl logs -f deployment/spring-rabbit-sidecar -c spring-app
```

### Cleanup

```bash
kubectl delete -f spring-rabbitmq-sidecar.yaml
```

---

## 📦 Spring Versions

### Option 1 (Stable)

```xml
<spring-boot.version>3.4.5</spring-boot.version>
<spring-cloud.version>2024.0.3</spring-cloud.version>
```

### Option 2 (Latest)

```xml
<spring-boot.version>3.5.9</spring-boot.version>
<spring-cloud.version>2025.0.1</spring-cloud.version>
```

> ✅ Both combinations are compatible. Prefer **Option 1** for production stability.

---

## 🐰 RabbitMQ Management UI

Access RabbitMQ UI:

```
http://localhost:31672/
```

**Default credentials** (unless overridden in YAML):

* Username: `guest`
* Password: `guest`

> If running on a remote cluster, use `kubectl port-forward` instead of NodePort.

---

## 📩 Sample Test Message

Send this message to the configured queue:

```json
{
  "id": 1,
  "name": "Vinay"
}
```

---

## 📌 Notes & Best Practices

* RabbitMQ sidecar avoids external dependency for local/dev environments
* No PVC is required unless message durability across pod restarts is needed
* For production:

    * Use external RabbitMQ or StatefulSet
    * Disable `guest` user
    * Enable TLS & auth

---

## ✅ Quick Flow Summary

1. Build JAR → `mvn clean install -DskipTests`
2. Build Docker image
3. Deploy sidecar YAML
4. Access RabbitMQ UI
5. Publish message → observe consumer logs

---

Happy messaging 🚀
