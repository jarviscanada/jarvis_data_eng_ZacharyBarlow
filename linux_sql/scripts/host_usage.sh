#!/bin/bash

if [ "$#" -ne 5 ]; then
  echo "host_info: invalid number of command line arguments"
  exit 1
fi

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

export PGPASSWORD="$psql_password"

memory_free=$(cat /proc/meminfo | fgrep "MemFree:" | awk '{print int($2*0.001)}' | xargs)
cpu_idle=$(grep "cpu " /proc/stat | awk '{print (100-($2+$4)*100/($2+$4+$5))}')
cpu_kernel=$(vmstat -t | sed -n 3p | awk '{print  $14}' | xargs)
disk_io=$(vmstat -D | sed -n 1p | awk '{print $1}' | xargs)
disk_available=$(df ~/ | awk '{print $4}' | xargs | awk '{print $2}')
tstamp=$(date '+%Y-%m-%d %H:%M:%S')

stmt="INSERT INTO PUBLIC.host_usage (timestamp,host_id,memory_free,cpu_idle,cpu_kernel,disk_io,disk_available)
      VALUES ('$tstamp',(SELECT id FROM host_info WHERE hostname='$hostname'),$memory_free,$cpu_idle,$cpu_kernel,$disk_io,$disk_available)"

psql -h "$psql_host" -p "$psql_port" -U "$psql_user" -d "$db_name" -c "$stmt"

exit $?