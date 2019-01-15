# Networth Calculator Case Study
This is a monorepo consisting of both a front-end and back-end api for a case study assignment.  See the individual projects within for more detailed information about each component.

## Getting Started
#### Installation
```
npm run install
```
#### Configuration
Currently there is only one environment variable, which is used for accessing the fixer currency conversion api.  For more detail, see the `networth-api` readme. You can configure the environment manually, or use a docker `.env` file.
```
FIXER_APIKEY=[your api key here]
```
#### Build
```
npm run build
```
#### Running
##### Locally
If you'd like to run locally, you'll need to setup mongo yourself.
```
npm run start
```
##### Docker Compose
```
npm run docker:build
docker-compose up
```

## Urls
| Service | URL |
|---|---|
| networth-web | http://localhost:8000 |
| networth-api | http://localhost:8080 |