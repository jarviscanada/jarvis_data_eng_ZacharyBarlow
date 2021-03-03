# Linux Cluster Monitoring Agent

# Introduction
The Jarvis Linux Cluster Administration (LCA) team manages a Linux cluster of 10 nodes/servers which are running CentOS 7. All of these servers are connected internally to each other through a switch and able to communication through interval IPv4 addresses.

The goal is to record the hardware specifications of each one of the nodes and monitor their individual usages in real-time. This data is collected by a crontab job every minute and store within a RDBMS database using PostgreSQL.

This data will be used to create reports for future research planning purposes and detect failures on specific hosts running.

The scripts to get the data and insert into the database are using bash and sql all running on a docker psql instance container running a CentOS7 image to run the automation process and the monitoring of the data. Using git as a resource version control for the project following the GitFlow ideologies.

# Quick Start
This is a simple quick start guide for you to implement and use the monitoring agent for your team or own system.
1. To start the instance, we have to create the docker container to run the CentOS image for our postgres database. Then we can start the container or stop the container whenever we want using the start/stop commands. The `[db_username][db_password]` are only needed for create tag.
```
$  ./scripts/psql_docker.sh start|stop|create [db_username][db_password]
```
2. Now we need to insert the tables `host_info, host_usage` into our newly created database `host_agent` by running the following command:
```
$ psql -h localhost -U postgres -d host_agent -f sql/ddl.sql
```
3. Before we start running the script continuously, we need to add the host machine's info to the table as it will be what is referenced in the usage table. Note, this command only needs to be run once.
```
$ ../scripts/host_info.sh psql_host psql_port db_name psql_user psql_password
```
4. This command is what gets the data on the specific usage that the machine is running with. To run this command use:
```
$ ./scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password
```
5. Now that we have all the tables and container setup, we will use crontab to run the script every minute to rn the host_usage script which will be what we use to monitor our system's usage for any failures that may occur.
```
$ crontab -e

# in the file
* * * * * bash <pwd>/host_usage.sh psql_host psql_port db_name psql_user psql_password > /tmp/host_usage.log

# list the crontabs
$ crontab -l
```
# Implementation
The implementation is very quite simple. The first part was to create the `psql_docker.sh` bash script which creates the PostgreSQL container to host the database on named `psql`.

If running on a network, the Linux node/server are then connected via a switch and allowed to communicate to each other using IPv4 addresses.

Using the `host_info.sh` and `host_usage.sh` scripts are to find and insert information into their respective tables regarding information on the host's machine.

From there we use the following scripts (`ddl.sql`) to generate the tables to store the host's information and hardware resource usages. The other being (`queries.sql`) which is used to check data from the table on specific use cases such as average memory usages and host failures. These are used by the LCA manager to manage the host clusters. 
## Architecture
![architecture](./assets/linux.jpeg)


## Scripts
1. The `psql_docker.sh` bash script is used to create|start|stop the docker container that is running the instance of the psql database we are using with the `jrvs_psql` image. It needs the associated `db_username` and `db_password` to create the container if it does not exist already. The start and stop options do not require these arguments. 
```
$  ./scripts/psql_docker.sh start|stop|create [db_username][db_password]
```

2. `host_info.sh` script is what is used to add the specific machine running the instance to the table to be referenced in the `host_usage` table. It takes the information to connect to the database `host_agent` and adds the host's information to the `host_info` table.
```
$ ../scripts/host_info.sh psql_host psql_port db_name psql_user psql_password
```
  *  `psql_host`: the name of the host or it's ip address. (ex. localhost)
  *  `psql_port`: the port for the psql instance (5432)
  *  `db_name`: the name of the database connecting to (host_agent)
  *  `psql_user`: the username for the instance (postgres)
  *  `psql_password`: the password for the instance (password)

3. `host_usage.sh` script is what we run against the crontab job. It finds the usages of specific details regarding the host machine it is running on and then inserts it into the `host_usage` table in the database.
```
$ ./scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password
```
  *  `psql_host`: the name of the host or it's ip address. (ex. localhost)
  *  `psql_port`: the port for the psql instance (5432)
  *  `db_name`: the name of the database connecting to (host_agent)
  *  `psql_user`: the username for the instance (postgres)
  *  `psql_password`: the password for the instance (password)

4. The crontab is a job running feature which allows us to continuously run a script at a specified time interval.
```
$ crontab -e

# in the file
* * * * * bash <pwd>/host_usage.sh psql_host psql_port db_name psql_user psql_password > /tmp/host_usage.log

# list the crontabs
$ crontab -l
```

##### SQL Queries
5. `./sql/ddl.sql` is the script to create and add the tables `host_info` and `host_usage` to the database if they do not exist already.
```
$ psql -h localhost -U postgres -d host_agent -f sql/ddl.sql
```

6. `./sql/queries.sql` contains 3 queries that the LCA manager or others will use to manage the cluster better and also plan for furture resources.
  * Group hosts by hardware info: groups hosts by their cpu_number and sorted by their total memory.
  * Average memory usage: the average amount of memory usage in a 5 minute time interval.
  * Detect host failure: detects if there is a host failure, which means if there are less than 3 inserts in a 5 min interval.

## Database Modeling
**Table: host_info**
Column | Type | Description 
--------------|------|--------------
id | `SERIAL PK` | This is what will be used to identify the host
hostname | `VARCHAR` | The name of the host machine
cpu_number | `INTEGER` | Number of cpu(s)
cpu_architecture | `VARCHAR` | The system's bit instruction set
cpu_model | `VARCHAR` | The model of the cpu
cpu_mhz | `REAL` | Speed of cpu `mhz`
L2_cache | `INTEGER` | Storage of the L2 cache on the system `kB`
total_mem | `INTEGER` | The total memory on the machine `kB`
timestamp | `TIMESTAMP` | When the host was added to the table `UTC`


**Table: host_usage**
Column | Type | Description 
--------------|------|--------------
timestamp | `TIMESTAMP` | When the collection of data was taken `UTC`
host_id | `SERIAL FK` | The id of the host it is coming from
memory_free | `INTEGER` | The amount of memory that has not been used `MB`
cpu_idle | `REAL` | The percentage of cpu that is not being used
cpu_kernel | `REAL` | The perecentage of kernel cpu usage 
disk_io | `INTEGER` | number of disk I/O
disk_available | `INTEGER` | Available memory in root directory `MB`

## Tests
1. The bash scripts were tested manually running each different option to test if the error returns are correct and as well as the scripts work on proper input.
2. SQL queries were run on inputted mock data to test the output and check for any erros.

## Improvements
1. Create a script to automate the crontab setup.
2. Handle change in updated hardware to update table host info
3. Use a software to check the sql queries instead of all the bloated boilerplate to run and check it via terminal
