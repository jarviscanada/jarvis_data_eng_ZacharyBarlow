#!/bin/bash

# check if the docker system deamon is running, if not, start it up
sudo systemctl status docker || systemctl start docker

# create a variable to hold the command to check the amount of containers named jrvs-psql
cont=`docker container ls -a -f name=jrvs-psql | wc -l`
# variables for input arguments
command=$1
db_username=$2
db_password=$3

# case checking on the inputted arguments (create, start, stop)
case $command in
  # create a new container if the container does not exist already
  'create')
    # container exists
    if [ $cont -eq 2 ]; then
      echo "psql_docker: Docker container 'jrvs-psql is already created"
      exit 1
    fi

    # the command did not contain a username, password, or both
    if [ "$#" -ne 3 ]; then
      echo "psql_docker: db_username or db_password were not entered"
      exit 1
    fi

    # create the volume to hold the data and create and run the container with the entered db username and password
    docker run --name jrvs-psql -e POSTGRES_PASSWORD=${db_password} -e POSTGRES_USER=${db_username} -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
    #What's $? variable? https://bit.ly/2LanHUi
    exit $?;;

  # When the argument is to start the container
  'start')
    if [ $cont -ne 2 ]; then
      echo "psql_docker: docker container 'jrvs-psql' does not exist."
      exit 1
    fi

    # start the container
    docker container start jrvs-psql
    exit $?;;

  # When the argument is to stop the container
  'stop')
    if [ $cont -ne 2 ]; then
      echo "psql_docker: docker container 'jrvs-psql' does not exist."
      exit 1
    fi

    # stop the container
    docker container stop jrvs-psql
    exit $?;;

  *)
    echo "psql_docker: Entered invalid command line argument."
    exit 1;;
esac