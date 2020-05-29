# see https://mherman.org/blog/dockerizing-a-react-app/
FROM node:13.12.0-alpine

# set working directory
WORKDIR /app

# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH
# prevent react-script to exit
ENV CI=true
#enables a polling mechanism via chokidar so that hot-reloading will work.
ENV CHOKIDAR_USEPOLLING=true

EXPOSE 3000
RUN apk add --no-cache bash

COPY . /app
RUN npm install && chmod +x /app/extractEnv.sh

CMD /bin/bash -c /app/extractEnv.sh ; npm start

