#! /bin/bash

gradle clean test
cp -r build/reports/tests/test/* /report
gradle allureServe --port $TARGETPORT