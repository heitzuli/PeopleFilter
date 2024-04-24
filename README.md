Hey-yo!

Here's a people-filter-sorter, or cartoon-filter-sorter, you can use to sort through a json consisting of people (firstName, lastName, age, gender). 
The rpogram asks first for which value to filter by, ie which people to keep and show.
Next, the program asks for what value to sort the remaining people by.
Lastly, the program asks wether to sort in ascending or descending order.

As of 24 April 2024 filtering by age is not yet implemented. Filtering works with firstName, lastName and gender.

How to run the program: Intelli J

This program was created with IntelliJ. If you're using IntelliJ all you need to do is start the program with IntelliJ(green play-button), specifying the input file in the run configurations
(three dots next to the debug icon in the upper-left hand corner). 
There are three input files in resources:
- input.json
- invalid_format.json
- invalid_schema.json

How to run the program: command line

- Open the project root folder (/PeopleFilter) in your terminal
- Exectue .\gradlew.bat jar to build the program
- Run the program with java -jar build/libs/PeopleFilter-1.0-SNAPSHOT.jar src/main/resources/input.json
  - To change the input file edit the X's in the latter part of the run command: src/main/resources/XXX.json

