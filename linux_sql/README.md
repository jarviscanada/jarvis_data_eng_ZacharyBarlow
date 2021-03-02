# Linux Cluster Monitoring Agent

## Introduction
The Jarvis Linux Cluster Administration (LCA) team manages a Linux cluster of 10 nodes/servers which are running CentOS 7. All of these servers are connected internally to each other through a switch and able to communication through interval IPv4 addresses.

The goal is to record the hardware specifications of each one of the nodes and monitor their individual usages in real-time. This data is collected by a crontab job every minute and store within a RDBMS database using PostgreSQL.

This data will be used to create reports for future research planning purposes and detect failures on specific hosts running.

The scripts to get the data and insert into the database are using bash and sql all running on a docker psql instance container running a CentOS7 image to run the automation process and the monitoring of the data. Using git as a resource version control for the project following the GitFlow ideologies.
