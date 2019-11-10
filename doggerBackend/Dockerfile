FROM ubuntu:16.04

RUN apt-get update &&\
    apt-get install -y software-properties-common python-software-properties &&\
    apt-get install -y curl &&\
    apt-get install -y wget &&\
    add-apt-repository -y ppa:longsleep/golang-backports &&\
    apt-get update &&\
    apt-get install -y golang-go &&\
    apt-get install -y git