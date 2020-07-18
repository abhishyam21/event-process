# App Event Process

This project will read Github event from kafka broker and process them and generate some metrics
#### Metric 1
- Most repeated words in the last 10 events. If there is a tie, then sorted based on natural english order.
#### Metric 2
- Most Common hour of the events i.e. at which hour in day the frequency of events is more.

##### All the above metrics will be accessible in two ways.
#### Using Rest Api
##### Get the most repeated word 
- curl --location --request GET 'localhost:8083/metrics/repeated-word'

##### Get the most common hour in day 
- curl --location --request GET 'localhost:8083/metrics/common-hour'

#### In the command prompt
 - we display the output in the command prompt for each event.
 
 ## Run the Application
 `mvn spring-boot:run`