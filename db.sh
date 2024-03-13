#!/usr/bin/env bash

if [ "$1" == "up" ]; then
  docker-compose --project-name todoismo-db up
elif [ "$1" == "down" ]; then
  docker-compose --project-name todoismo-db down --remove-orphans
else
  echo "Anna argumenttina joko 'up' tai 'down'"
fi
