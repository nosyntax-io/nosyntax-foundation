#!/bin/bash

LIGHT_GREEN='\033[1;32m'
RED='\033[0;31m'
NC='\033[0m'

read -r response

status=$(echo "$response" | jq -r '.status')
message=$(echo "$response" | jq -r '.message')

timestamp=$(date '+%Y-%m-%d %H:%M:%S')

log_message() {
  local color="$1"
  local msg="$2"
  echo -e "${color}[$timestamp] $msg${NC}"
}

if [[ -z "$status" || -z "$message" ]]; then
  log_message "$RED" "[ERROR] - Invalid response format."
  exit 1
fi

if (( status == 200 )); then
  log_message "$LIGHT_GREEN" "[SUCCESS] - $message"
else
  log_message "$RED" "[ERROR] - Status: $status | Message: $message"
  exit 1
fi