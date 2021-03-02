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

lscpu_out=`lscpu`
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)

hostname=$(hostname -f)
cpu_number=$cpu_number
cpu_architecture=$(echo "$lscpu_out" | grep "Architecture" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out" | egrep "Model name:" | awk '{$1=""; $2=""; print $0}' | xargs)
cpu_mhz=$(echo "$lscpu_out" | egrep "^CPU MHz:" | awk '{print $3}' | xargs)
l2_cache=$(echo "$lscpu_out" | egrep "L2 cache:" | awk '{print $3}' | sed 's/K//' | xargs)
total_mem=$(cat /proc/meminfo | fgrep "MemTotal:" | awk '{print $2}' | xargs)
tstamp=$(date "+%Y-%m-%d %H:%M:%S")


stmt="INSERT INTO PUBLIC.host_info (hostname,cpu_number,cpu_architechture,cpu_model,cpu_mhz,L2_cache,total_mem,timestamp)
      VALUES ('$hostname',$cpu_number,'$cpu_architecture','$cpu_model',$cpu_mhz,$l2_cache,$total_mem,'$tstamp')"

export PGPASSWORD="$psql_password"
psql -h "$psql_host" -p "$psql_port" -U "$psql_user" -d "$db_name" -c "$stmt"