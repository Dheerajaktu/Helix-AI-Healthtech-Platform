#!/bin/bash

# ==========================================
# Helix Health Platform - Dev Start Script
# ==========================================
# Runs the app locally (no Docker) against a local/dockerized Postgres,
# with dev profile active. Fast for local iteration.

GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

echo "=========================================="
echo "  Helix Health Platform — Development Environment Start....."
echo "=========================================="

# 1. Make sure Java 21 is active (jenv) if jenv is installed
if command -v jenv &> /dev/null; then
    jenv local 21 2>/dev/null
fi

echo -e "${YELLOW}Using Java version:${NC}"
java -version

# 2. Check if Postgres is reachable on 5432, warn if not
if ! nc -z localhost 5432 2>/dev/null; then
    echo -e "${RED}Warning: Nothing is listening on localhost:5432.${NC}"
    echo -e "${YELLOW}Start Postgres first, e.g.:${NC}"
    echo "  docker compose up -d postgres"
    echo ""
    read -rp "Continue anyway? (y/n): " choice
    if [[ "$choice" != "y" ]]; then
        exit 1
    fi
fi

# 3. Export default JWT secret for local dev if not already set
export JWT_SECRET="${JWT_SECRET:-this-is-a-temporary-dev-secret-key-must-be-at-least-64-bytes-long-for-hs512-algorithm}"

# 4. Run with dev profile active
echo -e "${GREEN}Starting application with 'dev' profile...${NC}"
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev