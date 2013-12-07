
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
export NSPROXY_CONNECTION=1
export NSPROXTPORT=80
```

