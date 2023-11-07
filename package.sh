#!/bin/sh

if [ "$1" = "" ];
then
    echo -e "\033[0;31m 未输入操作名 \033[0m  \033[0;34m [test|prod|clean|quit] \033[0m"
    exit 1
fi

# shellcheck disable=SC2112
function buildTest() {
    echo "build test start"
    mvn clean package -P test
    echo "build test end"
}

# shellcheck disable=SC2112
function buildProd() {
    echo "build prod start"
    mvn clean package -P prod
    echo "build prod end"
}

# shellcheck disable=SC2112
function clean() {
    echo "clean start"
    mvn clean
    echo "clean end"
}

# shellcheck disable=SC2112
function quit() {
    exit 0
}

case $1 in
    test)
    buildTest;;
    prod)
    buildProd;;
    clean)
    clean;;
    quit)
    quit;;
    *)
esac
