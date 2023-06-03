#!/bin/sh
set -e

until cqlsh -f /init.cql; do
  echo "cqlsh failed, retrying in 5 secs..."
  sleep 5
done &

exec /docker-entrypoint.py --smp 1 --overprovisioned 1 --developer-mode 1
