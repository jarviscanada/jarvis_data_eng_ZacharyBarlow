// Databricks notebook source
// MAGIC %md
// MAGIC 
// MAGIC ## Overview
// MAGIC 
// MAGIC This notebook will show you how to create and query a table or DataFrame that you uploaded to DBFS. [DBFS](https://docs.databricks.com/user-guide/dbfs-databricks-file-system.html) is a Databricks File System that allows you to store data for querying inside of Databricks. This notebook assumes that you have a file already inside of DBFS that you would like to read from.
// MAGIC 
// MAGIC This notebook is written in **Python** so the default cell type is Python. However, you can use different languages by using the `%LANGUAGE` syntax. Python, Scala, SQL, and R are all supported.

// COMMAND ----------

// # File location and type
val file_location = "/FileStore/tables/online_retail_II.csv"
val file_type = "csv"

// # CSV options
val infer_schema = "false"
val first_row_is_header = "true"
val delimiter = ","

// # The applied options are for CSV files. For other file types, these will be ignored.
var df = spark.read.format(file_type) 
  .option("inferSchema", infer_schema) 
  .option("header", first_row_is_header) 
  .option("sep", delimiter) 
  .load(file_location)

display(df)

// COMMAND ----------

// MAGIC %python
// MAGIC # Create a view or table
// MAGIC 
// MAGIC temp_table_name = "online_retail_II_csv"
// MAGIC 
// MAGIC df.createOrReplaceTempView(temp_table_name)

// COMMAND ----------

// MAGIC %sql
// MAGIC 
// MAGIC /* Query the created temp table in a SQL cell */
// MAGIC 
// MAGIC select * from `online_retail_II_csv`

// COMMAND ----------

// MAGIC %python
// MAGIC # With this registered as a temp view, it will only be available to this particular notebook. If you'd like other users to be able to query this table, you can also create a table from the DataFrame.
// MAGIC # Once saved, this table will persist across cluster restarts as well as allow various users across different notebooks to query this data.
// MAGIC # To do so, choose your table name and uncomment the bottom line.
// MAGIC 
// MAGIC permanent_table_name = "online_retail_II_csv"
// MAGIC 
// MAGIC # df.write.format("parquet").saveAsTable(permanent_table_name)

// COMMAND ----------

// MAGIC %md
// MAGIC #Total Invoice Amount Distribution

// COMMAND ----------

/* 1. Calculate the invoice amount. Note: an invoice consists of one or more items where each item is a row in the df. (hint: you need to GROUP BY invoice) */
var amount_col = df.withColumn("Amount", $"Quantity" * $"Price")
var inv_amount_df = amount_col
    .groupBy("Invoice")
    .sum("Amount")
    .withColumnRenamed("sum(Amount)", "Amount")
    .filter("Amount > 0")
    .orderBy("Invoice")

display(inv_amount_df)

// COMMAND ----------

import org.apache.spark.sql.functions._
/* 2. Draw the distribution of invoice amount with min, max, median, mod, and mean. However, you will notice many outlier data (e.g. invoices with large amounts). Sample hist and box charts: */
var amount = inv_amount_df.select("Amount")
amount.describe().show()

// COMMAND ----------

// MAGIC %md
// MAGIC #Monthly Placed and Cancelled Orders

// COMMAND ----------

/*
  The attribute information (see the project kick-off section) contains useful information that helps you to identify canceled orders
  To simplify the problem, you can assume that there are two invoice numbers for each canceled order (one for the original invoice and one for the canceled invoice)
  Therefore, # of placed orders = total # of orders - 2 * canceled order. Furthermore, you can also assume the original invoice and canceled invoice are on #always on the same 
  day (this eliminate the case where the original _root_invoice and canceled invoices are on different months)
  hints: you might want to create a new integer column with YYYYMM format. e.g. 2009-12-01 07:45:00 -> 200912 which allows easy GROUP BY.
 */
var year_month_df = df
  .withColumn("YYYYMM", date_format($"InvoiceDate","yyyyMM").as("YYYYMM"))
  .groupBy("Invoice", "YYYYMM")
  .count()

var cancelled_df = year_month_df
  .filter($"Invoice".contains("C"))
  .groupBy("YYYYMM")
  .count()
  .withColumnRenamed("count", "Cancelled")
  .orderBy("YYYYMM")

var total_order_df = year_month_df
  .filter(!($"Invoice".contains("C")))
  .groupBy("YYYYMM")
  .count()
  .withColumnRenamed("count", "Placed")
  .withColumnRenamed("YYYYMM", "YM")
  .orderBy("YM")

var dff = total_order_df
  .join(cancelled_df, total_order_df("YM") === cancelled_df("YYYYMM"))
  .withColumn("Placed", $"Placed" - $"Cancelled" * 2)
  .drop("YYYYMM")
  .orderBy("YM")

display(dff)

// COMMAND ----------

// MAGIC %md
// MAGIC # Monthly Sales

// COMMAND ----------

/* 
   Calculate the monthly sales data
   Plot a chart to show monthly sales (e.g. x-asix=year_month, y-axis=sales_amount)
*/
var ym_ent_df = amount_col
  .withColumn("YYYYMM", date_format($"InvoiceDate","yyyyMM").as("YYYYMM"))

var monthly_sales = ym_ent_df
  .groupBy("YYYYMM")
  .sum("Amount")
  .withColumnRenamed("sum(Amount)", "Sales")
  .withColumn("Sales", $"Sales" / 1000000)
  .orderBy("YYYYMM")

display(monthly_sales)

// COMMAND ----------

// MAGIC %md
// MAGIC # Monthly Sales Growth

// COMMAND ----------

/* 
  https://stackoverflow.com/questions/36071911/create-new-column-in-spark-dataframe-with-diff-of-previous-values-from-another-c
  Calculate monthly sales percentage growth data
  Plot a chart to show the growth percentage
*/
import org.apache.spark.sql.expressions.Window

var win = Window.orderBy("YYYYMM")
val dfWithLag = monthly_sales.withColumn("diff", lag("Sales", 1, 0).over(win))
val dfWithDiff = dfWithLag
  .withColumn("Percentage Change", (dfWithLag("Sales") - dfWithLag("diff"))/100)
  .select("YYYYMM", "Percentage Change")

display(dfWithDiff)

// COMMAND ----------

// MAGIC %md
// MAGIC # Monthly Active Users

// COMMAND ----------

/*
  Compute # of active users (e.g. unique CusotomerID) for each month
  Plot a bar chart
*/
var year_grouped_count = ym_ent_df
  .groupBy("YYYYMM")
  .count()

var monthly_active_users_df = ym_ent_df
  .join(year_grouped_count, "YYYYMM")
  .select("YYYYMM", "Customer ID")
  .distinct
  .groupBy("YYYYMM")
  .count()
  .orderBy("YYYYMM")

display(monthly_active_users_df)

// COMMAND ----------

// MAGIC %md
// MAGIC # New and Active Users

// COMMAND ----------

/*
  Plot a diagram to show new and exiting user for each month.
  A user is identified as a new user when he/she makes the first purchase
  A user is identified as an existing user when he/she made purchases in the past
  hints: find out the first purchase year-month for each user and then join this data with the transactional data to help you identified new/exiting users
*/
var cust_df = ym_ent_df
  .groupBy("Customer ID")
  .count()

var first_month_users_df = ym_ent_df
  .join(cust_df, "Customer ID")
  .groupBy("Customer ID")
  .agg(min("YYYYMM"))
  .withColumnRenamed("min(YYYYMM)", "Min")
  .orderBy("Customer ID")

var new_users = ym_ent_df
  .join(first_month_users_df, "Customer ID")
  .select("Customer ID", "Min")
  .distinct
  .groupBy("Min")
  .count()
  .withColumnRenamed("count", "New Users")
  .withColumnRenamed("min", "YYYYMM")
  .orderBy("YYYYMM")

var existing_users = ym_ent_df
  .join(first_month_users_df, "Customer ID")
  .filter(!($"YYYYMM" <=> $"Min"))
  .select("Customer ID", "YYYYMM")
  .distinct
  .groupBy("YYYYMM")
  .count()
  .orderBy("YYYYMM")

val someDF = Seq(("200912",0.0)).toDF("YYYYMM", "Existing Users")
existing_users = someDF.union(existing_users)

var final_df = new_users
  .join(existing_users, "YYYYMM")
  .orderBy("YYYYMM")

display(final_df)

// COMMAND ----------

// MAGIC %md
// MAGIC ## Finding RFM
// MAGIC 
// MAGIC RFM is a method used for analyzing customer value. It is commonly used in database marketing and direct marketing and has received particular attention in the retail and professional services industries. ([wikipedia](https://en.wikipedia.org/wiki/RFM_(market_research)))
// MAGIC 
// MAGIC Optional Reading: [Making Your Database Pay Off Using Recency Frequency and Monetary Analysis](http://www.dbmarketing.com/2010/03/making-your-database-pay-off-using-recency-frequency-and-monetary-analysis/)
// MAGIC 
// MAGIC 
// MAGIC RFM stands for three dimensions:
// MAGIC 
// MAGIC - Recency – How recently did the customer purchase?
// MAGIC 
// MAGIC - Frequency – How often do they purchase?
// MAGIC 
// MAGIC - Monetary Value – How much do they spend?
// MAGIC 
// MAGIC Note: To simplify the problem, let's keep all placed and canceled orders.
// MAGIC 
// MAGIC 
// MAGIC **Sample RFM table**
// MAGIC 
// MAGIC ![](https://i.imgur.com/sXFIg6u.jpg)

// COMMAND ----------

/*
new_df = retail_df.groupby(['CUSTOMER ID'], as_index=False)
freq = new_df['INVOICE'].nunique()['INVOICE']
freq
*/
var dfss = ym_ent_df
  .join(cust_df, "Customer ID")
  .select("Invoice", "Customer ID")
  .distinct
  .groupBy("Customer ID")
  .count()
  .withColumnRenamed("count", "Frequency")
  .orderBy("Customer ID")

display(dfss)

// COMMAND ----------

// rfm_df = retail_df[['INVOICE', 'QUANTITY', 'PRICE', 'INVOICEDATE', 'CUSTOMER ID', 'AMOUNT', 'YM']]
// rfm_df['MON'] = retail_df['QUANTITY'] * retail_df['PRICE']
// mone = rfm_df.groupby('CUSTOMER ID', as_index=False)['AMOUNT'].sum()['AMOUNT']

var monetary_df = ym_ent_df
  .join(cust_df, "Customer ID")
  .groupBy("Customer ID")
  .agg(sum("Amount"))
  .withColumnRenamed("sum(Amount)", "Monetary")
  .orderBy("Customer ID")
  
display(monetary_df)

// COMMAND ----------

// rece = rfm_df.groupby('CUSTOMER ID', as_index=False)['INVOICEDATE'].max()
// rece['INVOICEDATE'] = pd.to_datetime(rece['INVOICEDATE'])
// rece['Recency'] = (today - rece['INVOICEDATE']).dt.days
val date_df = Seq("2012-01-01 00:00:00").toDF("time")
val date_val = date_df.select("time").limit(1)
var max_date_df = ym_ent_df
  .join(cust_df, "Customer ID")
  .withColumn("InvoiceDate", col("InvoiceDate").cast("timestamp"))
  .groupBy("Customer ID")
  .agg(max("InvoiceDate"))
  .withColumnRenamed("max(InvoiceDate)", "MaxDate")
  .orderBy("Customer ID")

var recency = ym_ent_df
  .join(max_date_df, "Customer ID")
  .select($"Customer ID", datediff(to_date(lit("2012-01-01 00:00:00")), $"MaxDate").as("Recency"))
  .groupBy("Customer ID")
  .agg(max("Recency"))
  .withColumnRenamed("max(Recency)", "Recency")
  .orderBy("Customer ID")

display(recency)

// COMMAND ----------

var rfm_df = dfss
  .join(monetary_df, "Customer ID")
  .join(recency, "Customer ID")
  .orderBy("Customer ID")

display(rfm_df)
