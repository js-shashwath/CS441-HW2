# CS441 Homework 2 : Jawaharlal Sathyanarayan, Shashwath (sjawah2@uic.edu)

## Introduction
This is the second homework assignment of CS 441. It consists of creating 4 map-reduce algorithms each satisfying one of the below 4 criteria.

1. You will compute a spreadsheet or an CSV file that shows the distribution of different types of messages across predefined time intervals and injected string instances of the designated regex pattern for these log message types.
2. Second, you will compute time intervals sorted in the descending order that contained most log messages of the type ERROR with injected regex pattern string instances.
3. Then, for each message type you will produce the number of the generated log messages.
4. You will produce the number of characters in each log message for each log message type that contain the highest number of characters in the detected instances of the designated regex pattern.
The code has been written in Scala and can be compiled using SBT.

## How to run the project

### Environment Setup
1) Download vmware workstation and Hortonworks sandbox as mentioned in the original project description 
2) Follow the steps to run the sandbox and complete the necessary configurations

### Download the project and create the jar
Follow the following steps to run the simulations using IntellJ IDEA with the Scala plugin installed and sbt.
1) Open IntellJ IDEA, click on File > New Project >  “Check out from Version Control” and then “Git” option.
2) Enter the following URL and click “Clone”: https://github.com/js-shashwath/CS441-HW2.git
3) When prompted click “Yes” in the dialog box
4) The SBT import screen will appear, click “OK”
5) Confirm overwriting with “Yes”
6) Then run `sbt clean compile` to compile and build the project
7) Run `sbt assembly` to build the jar file in the target folder

### Testing the functionality - 5 Tests (src/test/scala/wordCounterTest/)	

The following test class is provided:LogFileGeneratorTest. The following test methods are executed using the `sbt test` command:
1) To check whether the timeStamp startTime is same as the one given in config file
2) To check whether the timeStamp endTime is same as the one given in config file
3) To check whether the seperator used is a comma
4) find the pattern in the string
5) test to check if the generated string matches the patter specified, if not this test returns a string of length 0

### Running the map reduce with the jar file
1) Copy the generated log file to the vm by running `scp -P 2222 <absolute path-to-log-file> root@<vm ip>:<destination/path>`
2) Copy the generated jar file to the vm by running `scp -P 2222 <absolute path-to-jar-file> root@<vm ip>:<destination/path>`
3) Once the file are copied ssh to the vm and navigate to `<destination/path>` mentioned earlier
4) Run `hadoop fs -put <logFileName> <logFileName>` to put to log file in HDFS
5) Same jar file can be used to run all tasks by passing a cli argument to denote which task to run

#### Running individual tasks
1) Task1: 
   1) Run `hadoop jar <jarFileName> <logFileName> <outputFolder> one`
   2) To see the output run `hadoop fs -cat <outputFolder>/*`
2) Task2: 
   1) Run `hadoop jar <jarFileName> <logFileName> <outputFolder1> <outputFolder2> two`
   2) To see the output run `hadoop fs -cat <outputFolder2>/*`
3) Task3:
   1) Run `hadoop jar <jarFileName> <logFileName> <outputFolder> three`
   2) To see the output run `hadoop fs -cat <outputFolder>/*`
4) Task4:
   1) Run `hadoop jar <jarFileName> <logFileName> <outputFolder> four`
   2) To see the output run `hadoop fs -cat <outputFolder>/*`
   


## Result
### Task1
For task 1, I have selected a time period of 1 sec.
For each time interval of 1 sec I have counted the number of different log types.
The key is formatted in the form of `<timestampSecond__logType>`
``````````````````````````````
16:13:12__INFO,1                                                                                                                                                        
16:13:12__WARN,1                                                                                                                                                        
16:13:13__DEBUG,6                                                                                                                                                       
16:13:13__ERROR,2                                                                                                                                                       
16:13:13__INFO,40                                                                                                                                                       
16:13:13__WARN,19                                                                                                                                                       
16:13:14__DEBUG,5                                                                                                                                                       
16:13:14__INFO,53                                                                                                                                                       
16:13:14__WARN,18                                                                                                                                                       
16:13:15__DEBUG,6                                                                                                                                                       
16:13:15__ERROR,1                                                                                                                                                       
16:13:15__INFO,58                                                                                                                                                       
16:13:15__WARN,15                                                                                                                                                       
16:13:16__DEBUG,8                                                                                                                                                       
16:13:16__INFO,58                                                                                                                                                       
16:13:16__WARN,16                                                                                                                                                       
16:13:17__DEBUG,9                                                                                                                                                       
16:13:17__INFO,67                                                                                                                                                       
16:13:17__WARN,8                                                                                                                                                        
16:13:18__DEBUG,11                                                                                                                                                      
16:13:18__INFO,58                                                                                                                                                       
16:13:18__WARN,17                                                                                                                                                       
16:13:19__DEBUG,8                                                                                                                                                       
16:13:19__ERROR,1                                                                                                                                                       
16:13:19__INFO,63                                                                                                                                                       
16:13:19__WARN,13                                                                                                                                                       
16:13:20__DEBUG,8                                                                                                                                                       
16:13:20__INFO,60                                                                                                                                                       
16:13:20__WARN,17                                                                                                                                                       
16:13:21__DEBUG,6                                                                                                                                                       
16:13:21__ERROR,1                                                                                                                                                       
16:13:21__INFO,63                                                                                                                                                       
16:13:21__WARN,13                                                                                                                                                       
16:13:22__DEBUG,11                                                                                                                                                      
16:13:22__ERROR,1                                                                                                                                                       
16:13:22__INFO,61                                                                                                                                                       
16:13:22__WARN,12                                                                                                                                                       
16:13:23__DEBUG,13                                                                                                                                                      
16:13:23__INFO,49                                                                                                                                                       
16:13:23__WARN,21                                                                                                                                                       
16:13:24__DEBUG,11                                                                                                                                                      
16:13:24__INFO,54                                                                                                                                                       
16:13:24__WARN,16                                                                                                                                                       
16:13:25__DEBUG,1                                                                                                                                                       
16:13:25__INFO,20                                                                                                                                                       
16:13:25__WARN,3
``````````````````````````````
### Task2

For task 2, I have selected a time period of 1 sec just like the first task. I have chained two map reducers. 
In the first map reducer I have calculated the error log count for each time interval. The second map reducer computes the time interval 
with the maximum number of error messages.
The key is formatted in the form of `<timestampSecond___logType>`
``````````````````````````````
16:13:13__ERROR,2                                                                                                                                                       
16:13:22__ERROR,1	 
``````````````````````````````
### Task3
In the third task we compute the total number of log messages generated for each log message type.
The key is formatted in the form of `<logType___frequency of log type>`
``````````````````````````````
DEBUG,103                                                                                                                                                               
ERROR,6                                                                                                                                                                 
INFO,705                                                                                                                                                                
WARN,189 
``````````````````````````````
### Task4
In the fourth task we compute the maximum number of characters for each type of a generated log message type.
The key is formatted in the form of `<logType___longest character sequence per error type>`
``````````````````````````````
DEBUG,30                                                                                                                                                                
ERROR,60                                                                                                                                                                
INFO,10                                                                                                                                                                 
WARN,44 
``````````````````````````````