FROM gradle:6.4-jdk8

RUN mkdir /usr/app

ADD src /usr/app/src
ADD *.gradle /usr/app/

WORKDIR /usr/app
EXPOSE 8080
CMD gradle --project-cache-dir /tmp/gradle-cache bootRun
