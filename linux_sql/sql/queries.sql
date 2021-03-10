-- Group hosts by CPU number and sort by their
-- memory size in descending order(within each cpu_number group)
SELECT
    cpu_number,
    id AS host_id,
    total_mem
FROM
    PUBLIC.host_info
ORDER BY
    cpu_number ASC,
    total_mem DESC;

-- Average used memory in percentage over 5 mins
-- interval for each host. (used memory = total memory - free memory).
SELECT
    id,
    hostname,
    date_trunc('hour', u."timestamp") + date_part('minute', u."timestamp"):: int / 5 * interval '5 min' AS tstamp,
    AVG((total_mem - memory_free/1000)*100/total_mem):: int AS avg_used_mem_percentage
FROM
    PUBLIC.host_info i
    JOIN PUBLIC.host_usage u
        ON i.id = u.host_id
GROUP BY
    tstamp,
    id ,
    hostname
ORDER BY
    tstamp ASC;

-- The cron job is supposed to insert a new entry to the host_usage
-- table every minute when the server is healthy. We can assume that a server is
-- failed when it inserts less than three data points within
-- 5-min interval. Please write a query to detect host failures.
SELECT
    host_id,
    tstamp,
    count(host_id) AS num_data_points
FROM
    (
    SELECT
        id as host_id,
        date_trunc('hour', u."timestamp") + date_part('minute', u."timestamp"):: int / 5 * interval '5 min' AS tstamp
    FROM
        PUBLIC.host_info i
        JOIN PUBLIC.host_usage u
            ON i.id = u.host_id
    ) as us
GROUP BY
    host_id,
    tstamp
HAVING
    count(host_id) < 3
ORDER BY
    tstamp ASC;