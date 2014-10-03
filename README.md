Todo List application that uses Dropwizard to provide CRUD functionalities over a REST API for a Item object.
A Todo Item can be marked "done/notdone" with true/false. When a task is marked done true, 
the application sends a SMS to the number provided in the API using Twilio SMS service.

A TodoItem has the following fields:
{
    "title" : "",
    "body" : "",
    "done" false
}

To compile and run the application:

1. fill in mongo, elasticsearch and Twilio configurations in todo.yml
2. mvn package
3. verify Mongo, Elasticsearch are running
4. set up MongoDB-Elsaticsearch river using https://github.com/richardwilly98/elasticsearch-river-mongodb 
5. java -jar target/dropwizard.jar server todo.yml
6. the admin for the Application is available at localhost:8081 provided by dropwizard framework that contains metrics and health-checks for the application.

API calls:

1. save/create a item:
curl -i -X POST -H "Content-Type: application/json" -d '{"title":"test-title 7","body":"test body 7","done":false}' http://localhost:8080/todo

2. GET a item:
curl -i -X GET http://localhost:8080/todo/<<id>>

3. UPDATE a item:
curl -i -X PUT -H "Content-Type: application/json" -d '{"title":"test-title 5","body":"test body 5-2","done":false}' http://localhost:8080/todo/<<id>>

4. DELETE a item:
curl -i -X DELETE http://localhost:8080/todo/<<id>>

5. mark a item Done:
curl -i -X POST http://localhost:8080/todo/<<id>>/true?smsTo=<<phone-number>>
NOTE: "true" after/<<id>>/ is marking the Item done "true" and <<phone-number>> is to send sms to

6. search query:
curl -i -X GET -H "Content-Type: application/json" 'http://localhost:8080/todo/search/<<query>>'
NOTE: search query is set to query Elasticsearch over 'Title' and 'Body' fields of the item and 
give higher boosting to the items that matches in 'Title' field
