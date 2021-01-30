# RESTful API for our statistics

## Task
* The API has to be thread-safe with concurrent requests
* Requests are POST, GET, DELETE 
* The POST and GET endpoints MUST execute in constant time and memory ie O(1)
* Get request returns statistics of post in last 60 sec like: {"sum": "1000.00", "avg": "100.53", "max": "200000.49", "min": "50.23","count": 10}
* The solution has to work without a database 

## Solution

If the use of the database is forbidden, it hints at the presence of a data structure in the solution. 
There are several data structures, for which insert and access to elements takes O(1). 
But for all of them, counting statistics for GET requests will take O(N). 
This is due to the fact that:

1. Counting sum, avg... will take O(N)
2. At the time of creating statistics, we need to be sure that all transactions are not older than 60 seconds.
That is why f.e. statistics refreshing cannot be done together with the last POST request. It is not a single trigger, that shows that data is updated. 
There is the additional trigger - time.

Therefore, as a solution created Statistics Updater, that refresh statistics in the background.
So any GET request just takes the ready-made statistics.

## Possible improvements
1. Pull out transactions that are not older than 60 sec in a separate list (by analogy with indexes in databases). 
Se we would have 2 lists - original and listForStatistics.
2. Deleting transactions that are older than 60 sec from this small list, in order to don't filter them every time.

This is not done in order not to complicate the code and understanding of the solution.

## QnA

### How frequently statistics updater is triggered
It is triggered every 1 millisecond. I agree that it looks very strange, but this is the only way to be sure that statistics will be up-to-date at any given time.
It works with such frequency because it is a time step between transaction dates. Transaction date format: YYYY-MM-DDThh:mm:ss.sssZ. 

### Why is the ArrayList selected as a data structure for keeping transactions?
1. It is lightweight and the time of insert operation takes O(1) (in the common case)
2. Possible option - LinkedList, which gives more accurate time for insert operation, but definitely loses in consumed memory (trade-off)
