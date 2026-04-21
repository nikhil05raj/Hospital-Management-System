Step 1 : first start SQl , then Open cmd admin. and then run sql using ( mysql -u root -p )
Step 2 : create the database in mysql using (create database hospital_db ;) 
step 3 : now run the java spring program , now it will find the database and create the tables/Entities.


implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
implementation 'org.springframework.boot:spring-boot-starter-webmvc'
compileOnly 'org.projectlombok:lombok'
annotationProcessor 'org.projectlombok:lombok'
runtimeOnly'com.mysql:mysql-connector-j'
implementation 'org.springframework.boot:spring-boot-starter'
implementation 'org.springframework.boot:spring-boot-starter-log4j2'
implementation 'org.springframework.boot:spring-boot-starter-actuator'

---> add inbuilt cashing
--> Add loging through AOP