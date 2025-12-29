apiVersion: apps/v1
kind: Deployment
metadata:
  name: spring-rabbit-sidecar
spec:
  replicas: 1
  selector:
    matchLabels:
      app: spring-rabbit-sidecar
  template:
    metadata:
      labels:
        app: spring-rabbit-sidecar
    spec:
      containers:

        # ================= Spring Boot App =================
        - name: spring-app
          image: spring-rabbit-consumer:latest
          imagePullPolicy: Never
          env:
            - name: SPRING_RABBITMQ_HOST
              value: localhost
            - name: SPRING_RABBITMQ_PORT
              value: "5672"
            - name: SPRING_RABBITMQ_USERNAME
              value: guest
            - name: SPRING_RABBITMQ_PASSWORD
              value: guest
          ports:
            - containerPort: 8080

        # ================= RabbitMQ Sidecar =================
        - name: rabbitmq
          image: rabbitmq:3.13-management
          ports:
            - containerPort: 5672
            - containerPort: 15672
          env:
            - name: RABBITMQ_DEFAULT_USER
              value: guest
            - name: RABBITMQ_DEFAULT_PASS
              value: guest
---
apiVersion: v1
kind: Service
metadata:
  name: rabbitmq-ui
spec:
  type: NodePort
  selector:
    app: spring-rabbit-sidecar
  ports:
    - name: management
      port: 15672
      targetPort: 15672
      nodePort: 31672
