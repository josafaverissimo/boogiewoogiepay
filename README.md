# Boogie Woogie Pay
A enterprise solution for handling payments requests. ☕

Project developed for the [Rinha de Backend 2025](https://github.com/zanfranceschi/rinha-de-backend-2025).

## Architecture design

```mermaid
---
title: Payments Handler
---

graph LR
    subgraph Backend
        Nginx[Nginx] --> Backend1[Backend #1]
        Nginx --> Backend2[Backend #2]
        Backend1 --> Redis[Redis]
        Backend2 --> Redis
        Redis --> Worker[Worker]
    end
    
    subgraph PaymentProcessor
        Worker --> PaymentProcessorDefault[Default Processor]
        PaymentProcessorDefault --> PaymentProcessorFallback[Fallback Processor]
    end

    BancoCentral[Banco Central] --> Nginx
```
## Stack

- **API**: Java + Javalin
- **Worker**: Java
- **Broker**: Redis (used as a lightweight message broker)
- **Load Balancer**: Nginx
- **Orchestration**: Docker Compose

## App Flow

1. Requests are handled by nginx, then it choose what backend will
process the request.

2. When recieve the request, the API, just adds the request data into the Redis
queue.

3. The worker listens to the Redis queue to process request data, sending it
first to the default payment processor, and to the fallback payment processor
if the default fails.
