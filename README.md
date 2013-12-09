
# Sweettalk, NetSuite API Proxy

This is a HTTP server which proxies requests to the NetSuite 'Suitetalk' API,
handling pooling of concurrent connections to within the users limits.

## Usage

Clone the repository and then start the server...

```
lein run
```

This will start a web server on the default port 8080.

## Configuration

All configuration is handled through environment variables.

```
# netsuite
export ST_WS_URL="https://webservices.sandbox.netsuite.com"
export ST_WS_CONNECTIONS=1

# http server
export ST_HTTP_PORT=8080

# logging
export ST_LOG_LEVEL="warn"
export ST_LOG_PATTERN="%m"
export ST_LOG_FILE="logs/access.log"

# statsd
export ST_STATSD_HOST="localhost"
export ST_STATSD_PORT=8126
```

