#!/bin/bash
echo trying to run rabbit container
result=$(sudo podman ps -a | grep rabbit-dev)

if [[ -n "$result" ]]; then
  echo "Container exists. Cleanup"
  sudo podman rm rabbit-dev
else
  echo "Running container"
  sudo podman run -d -h node-1.rabbit \
    --name rabbit-dev \
    -p "4369:4369" \
    -p "5672:5672" \
    -p "15672:15672" \
    -p "25672:25672" \
    -p "35197:35197" \
    -e "RABBITMQ_USE_LONGNAME=true" \
    -e "RABBITMQ_LOGS=/var/log/rabbitmq/rabbit.log" \
    -v /tmp/rabbit/data:/var/lib/rabbitmq \
    -v /tmp/rabbit/data/logs:/var/log/rabbitmq \
    rabbitmq:management
fi
