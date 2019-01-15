# Net Worth Case Study - REST API
This project represents the API component of the net worth case study.

## Configuration
| Environment Variable | Default | Description |
|---|---|---|
| DB_URI | mongodb://127.0.0.1:27017/test | MongoDB database name |
| FIXER_APIKEY | null | Fixer API key |

## Running
```
./gradlew bootRun
```

## Tests
```
./gradlew test jacocoTestReport
```

## Docker
```
./graldew build && docker build -t efredin/networth-api:latest .
```

## External Dependencies
This service relies on fixer for currency data.  An API key can be requested at https://fixer.io/

## TODO:
* async