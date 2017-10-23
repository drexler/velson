#!/usr/bin/env bash

set -e
echo "TRAVIS_TAG          : $TRAVIS_TAG"
echo "TRAVIS_BRANCH       : $TRAVIS_BRANCH"
echo "TRAVIS_PULL_REQUEST : $TRAVIS_PULL_REQUEST"
echo "Publishing archives for branch $TRAVIS_BRANCH"

echo "Cleaning..."
./gradlew clean check install --stacktrace

echo "Building"
./gradlew assemble
