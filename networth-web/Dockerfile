FROM node:10-alpine

WORKDIR /app
COPY ./dist ./
RUN npm i http-server -g
EXPOSE 8000
CMD [ "http-server", "-p", "8000" ]
