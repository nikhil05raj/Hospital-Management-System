Step 1 : first start SQl , then Open cmd admin. and then run sql using ( mysql -u root -p )
Step 2 : create the database in mysql using (create database hospital_db;) 
step 3 : open wsl/docker and run the redis server using cmd (sudo service redis-server start) 
step 4 : now run the java spring program , now it will find the database and create the tables/Entities.
step 5 : open Postman and start sending the requests. (soon you'll see the fast responses, all bcuz of caching using Redis)