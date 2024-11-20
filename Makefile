# Variables
DOCKER_COMPOSE_FILE=docker/docker-compose.yaml

# Targets
.PHONY: up down logs kafka-logs ps exec-kafka

up:
	docker-compose -f $(DOCKER_COMPOSE_FILE) up -d

down:
	docker-compose -f $(DOCKER_COMPOSE_FILE) down

logs:
	docker-compose -f $(DOCKER_COMPOSE_FILE) logs

kafka-logs:
	docker-compose -f $(DOCKER_COMPOSE_FILE) logs kafka

ps:
	docker-compose -f $(DOCKER_COMPOSE_FILE) ps

exec-kafka:
	docker exec -it $$(docker ps -qf "name=docker-kafka-1") /bin/bash