# Net Worth Case Study - REST API
This project represents the API component of the net worth case study.

## Configuration
| Environment Variable | Default | Description |
|---|---|---|
| DB_DATABASENAME | networth | MongoDB database name |
| DB_HOST | 127.0.0.1 | MongoDB host |
| DB_PORT | 27017 | MongoDB port |
| FIXER_APIKEY | null | Fixer API key |

## Running
```
./gradlew bootRun
```

## Tests
```
./gradlew test jacocoTestReport
```

## External Dependencies
This service relies on fixer for currency data.  An API key can be requested at https://fixer.io/

## TODO:
* async