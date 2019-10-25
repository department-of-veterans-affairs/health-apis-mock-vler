#! /usr/bin/env bash

usage() {
cat<<EOF
$0 [options] <command>

Start and stop applications

Commands
 r, restart   Restart applications
 s, start     Start applications
 st, status   Report status of applications
 k, stop      Stop applications

Options
 -v, --mock-vler   Include Mock-VLER

Examples
 $0 start -v
 $0 stop --mock-vler

$1
EOF
exit 1
}


startApp() {
  local app=$1
  local pid=$(pidOf $app)
  [ -n "$pid" ] && echo "$app appears to already be running ($pid)" && return
  echo "Starting $app"
  cd $REPO/$app
  local jar=$(find target -maxdepth 1 -name "$app-*.jar" | head -1)
  [ -z "$jar" ] && echo "Cannot find $app application jar" && exit 1
  java -jar $jar &
}

stopApp() {
  local app=$1
  local pid=$(pidOf $app)
  [ -z "$pid" ] && echo "$app does not appear to be running" && return
  echo "Stopping $app ($pid)"
  if [[ "$OSTYPE" == "msys" ]]; then
  taskkill //F //PID $pid
  else
  kill $pid
  fi
}

pidOf() {
  local app=$1
  jps -l | grep -E "target/$app-.*\.jar" | cut -d ' ' -f 1
}

statusOf() {
  local app=$1
  local pid=$(pidOf $app)
  local running="RUNNING"
  [ -z "$pid" ] && running="NOT RUNNING"
  printf "%-11s   %-11s   %s\n" $app "$running" $pid
}

doStatus() {
  statusOf mock-vler
}

doStart() {
  export SPRING_PROFILES_ACTIVE
  echo "Using profile: $SPRING_PROFILES_ACTIVE"
  [ $MOCKVLER == true ] && startApp mock-vler
}

doStop() {
  [ $MOCKVLER == true ] && stopApp mock-vler
}


REPO=$(cd $(dirname $0)/../.. && pwd)
MOCKVLER=false
SPRING_PROFILES_ACTIVE=dev

ARGS=$(getopt -n $(basename ${0}) \
    -l "debug,help,mock-vler" \
    -o "vh" -- "$@")
[ $? != 0 ] && usage
eval set -- "$ARGS"
while true
do
  case "$1" in
    -v|--mock-vler) MOCKVLER=true;;
    --debug) set -x;;
    -h|--help) usage "halp! what this do?";;
    --) shift;break;;
  esac
  shift;
done

[ $# != 1 ] && usage
COMMAND=$1

case $COMMAND in
  s|start) doStart;;
  st|status) doStatus;;
  k|stop) doStop;;
  r|restart) doStop;doStart;;
  *) usage "Unknown command: $COMMAND";;
esac
