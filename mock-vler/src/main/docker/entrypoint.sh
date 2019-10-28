#!/usr/bin/env bash

ENDPOINT_DOMAIN_NAME="$K8S_LOAD_BALANCER"
ENVIRONMENT="$K8S_ENVIRONMENT"
BASE_PATH="$BASE_PATH"

#Put Health endpoints here if you got them, all that's here is a WAG
PATHS=("/actuator/health")

SUCCESS=0

FAILURE=0

# New phone who this?
usage() {
cat <<EOF
Commands
  smoke-test [--endpoint-domain-name|-d <endpoint>] [--environment|-e <env>] [--base-path|-b <base_path>] [--username|-u <mock_username>] [--password|-p <mock_password>] [--icn|-i <patient_icn>]
  regression-test [--endpoint-domain-name|-d <endpoint>] [--environment|-e <env>] [--base-path|-b <base_path>] [--username|-u <mock_username>] [--password|-p <mock_password>] [--icn|-i <patient_icn>]

Example
  smoke-test
    --endpoint-domain-name=localhost
    --environment=qa
    --base-path=/direct/addresses

$1
EOF
exit 1
}

# Keeps track of successes and failures
trackStatus () {
  if [ "$status_code" == 200 -o "$status_code" == 201 ]
    then
      SUCCESS=$((SUCCESS + 1))
      echo "$request_url: $status_code - Success"
    else
      FAILURE=$((FAILURE + 1))
      echo "$request_url: $status_code - Fail"
  fi
}

smokeTest() {

  if [[ ! "$ENDPOINT_DOMAIN_NAME" == http* ]]; then
    ENDPOINT_DOMAIN_NAME="https://$ENDPOINT_DOMAIN_NAME"
  fi

  for path in "${PATHS[@]}"
    do
      doCurl 200
    done

  # Happy Path Practitioner
  path="/direct/addresses"
  doCurl 200

  printResults
}

regressionTest() {

  if [[ ! "$ENDPOINT_DOMAIN_NAME" == http* ]]; then
    ENDPOINT_DOMAIN_NAME="https://$ENDPOINT_DOMAIN_NAME"
  fi

  for path in "${PATHS[@]}"
    do
      doCurl 200
    done

  # Happy Path Practitioner by identifier
  path="/direct/addresses"
  doCurl 200

  printResults
}

printResults () {
  TOTAL=$((SUCCESS + FAILURE))

  echo "=== TOTAL: $TOTAL | SUCCESS: $SUCCESS | FAILURE: $FAILURE ==="

  if [[ "$FAILURE" -gt 0 ]]; then
  exit 1
  fi
}

# Let's get down to business
ARGS=$(getopt -n $(basename ${0}) \
    -l "endpoint-domain-name:,environment:,base-path:,help" \
    -o "d:e:t:b:v:p:ei:en:h" -- "$@")
[ $? != 0 ] && usage
eval set -- "$ARGS"
while true
do
  case "$1" in
    -d|--endpoint-domain-name) ENDPOINT_DOMAIN_NAME=$2;;
    -e|--environment) ENVIRONMENT=$2;;
    -b|--base-path) BASE_PATH=$2;;
    -h|--help) usage "I need a hero! I'm holding out for a hero...";;
    --) shift;break;;
  esac
  shift;
done

if [[ -z "$ENDPOINT_DOMAIN_NAME" || -e "$ENDPOINT_DOMAIN_NAME" ]]; then
  usage "Missing variable K8S_LOAD_BALANCER or option --endpoint-domain-name|-d."
fi

if [[ -z "$ENVIRONMENT" || -e "$ENVIRONMENT" ]]; then
  usage "Missing variable K8S_ENVIRONMENT or option --environment|-e."
fi

[ $# == 0 ] && usage "No command specified"
COMMAND=$1
shift

case "$COMMAND" in
  s|smoke-test) smokeTest;;
  r|regression-test) regressionTest;;
  *) usage "Unknown command: $COMMAND";;
esac

exit 0
