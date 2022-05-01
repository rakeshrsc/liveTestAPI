# liveTestAPI - Event Schedular for logging request APIs
Event/Request log scheduler

## Process to run the application

1. Build and run the application
2. Once the build is successful use a curl or postman to fire the request.
3. The port is given as 7070. So, the request format should be http://localhost:7070/api/smaato/accept
4. Add query params (id & endpoint) to test the application.
5. **_id_** param is mandatory. without it 400 (Bad request) error will be thrown.
6. **_endpoint_** param is optional. The format should be "/api/smaato/{any string}".
7. If endpoint is provided another post request will be fired internally.
8. For every one minute log will be written indicating unique requests (unique by id) received by /api/smaato/accept.

## Internal logics

1. acceptRequest - Get request with endpoint '/api/smaato/accept';
2. postRequest - Internal post method to be called when there is a endpoint queryparam in the accept request.
3. Logger used - Slf4j
4. Log will be wriiten every 1 min based on count received in accept method.
5. Log will be written for the post request fired with count from accept method.
