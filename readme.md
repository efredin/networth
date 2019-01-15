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

## Notes
* First time working with Java / Spring.
* Spring has lots of magic by convention. This is difficult for new users.
* Interested in using maven instead of gradle on the next go around.
* Ran into issues getting networking online with docker on windows.  Spring data couldn't communicate with mongo through loopback (known docker issue).
* Ran into issues with configuration. 
  * So many settings and difficult to find a list of them.
  * Looking for a better way to store secrets out-of-band, like a `.env` file.  Don't like having environment specific configuration in-band.
* Couldn't get e2e tests running.
  