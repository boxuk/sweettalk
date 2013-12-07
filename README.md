
# NetSuite API Proxy

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
export NSPROXY_WS_URL="https://webservices.sandbox.netsuite.com"
export NSPROXY_WS_CONNECTIONS=1
export NSPROXY_HTTP_PORT=8080
export NSPROXY_LOG_LEVEL="warn"
export NSPROXY_LOG_PATTERN="%m"
export NSPROXY_LOG_FILE="logs/access.log"
```

