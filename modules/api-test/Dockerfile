FROM python:2.7-alpine

# This needs to be set otherwise pycurl won't link correctly
ENV PYCURL_SSL_LIBRARY=openssl

RUN apk add --no-cache --update openssl curl \
    && apk add --no-cache --update --virtual .build-deps build-base python-dev curl-dev \
    && pip install jmespath jsonschema pyresttest \
    && apk del .build-deps

ADD tests tests

WORKDIR /tests

ENTRYPOINT ["pyresttest"]
