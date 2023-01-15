#!/bin/bash

PROJECT_NAME=file-tools

usage() {
  echo 'sh build.sh <native>'
}

build() {
  mvn clean package
  if [ "$1" ]
  then
  native-image -jar target/${PROJECT_NAME}.jar
  fi
}

case "$1" in
"?") usage;;
"native") build 1;;
*) build;;
esac