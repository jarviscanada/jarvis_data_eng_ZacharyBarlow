Table of contents
* [Introduction](#Introduction)
* [Hadoop Cluster](#Hadoop-Cluster)
* [Hive Project](#Hive-Project)
* [Improvements](#Improvements)

# Introduction
This project is an extension on top of the PoC trading application and is used for processing the data using Apache Hadoop and evaluate the different tools and techniques. The setup is using a Hadoop cluster with 1 master noder, and 2 worker nodes that are started using YARN and run MapReduce jobs with TEZ. The data is stored in external tables on HDFS and accessed with queries in a Zeppelin notebook using Hive SQL.

From this project, I learned the differences in processes such as Partitions, columnar, parquet, and other queries as well as Spark vs Hive SQL.

# Hadoop Cluster
- Master Node:
  - 2vCPUs 12GB with 100GB
- Worker Nodes: (2)
  - 2vCPUs 12GB with 100GB
- 4 YARN cores with 12GB memory
![Hadoop](./assets/Hadoop.jpg)

# Hive Project
The Hive project uses a Zeppelin notebook on the Hadoop cluster. It is first used to set up tables and find an issue with normal parsing for the indicator_name column and how it affected our results. It also shows the results of OpenCSVSerDe and how it affects the querying time due to deserialization to all String objects.

From here I moved onto finding solutions to the querying time constraints and how to improve the efficiency. I created a partitions table to run dynamic partitions on the year only and columnar file optimization using parquet file which shows the effect on speed due to file size.

Finally showed the difference in running a Hive query vs Spark SQL query for the exact same solution and the time difference between the two.
![Zeppelin](./assets/Zeppelin.jpg)

# Improvements
- Run two different partitions and check the difference between querying against one with just 1 partition vs 2 partitions
- Change number of worker nodes to see the effect
- Run more queries similar to the Python project to do more analysis on