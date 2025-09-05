# Mock project step by step

1. Create & Config Azure SQL Database:
+ Create SQL server name "dopv3":
  ![img.png](statics/images/img.png)
+ Create database name "products" and insert 5 record:
  ![img.png](statics/images/img0.png)
+ Config SQL server network, enable public access:
  ![img_1.png](statics/images/img_1.png)

2. Create Spring Boot app & connect to SQL DB & push to github repo:
+ Get DB connection string:
  ![img_2.png](statics/images/img_2.png)
+ Config application.properties in Spring Boot app:
  ![img_3.png](statics/images/img_3.png)
+ Push source code to github repo:
  ![img_4.png](statics/images/img_4.png)

3. Create Azure DevOps project & Service connection & create pipeline map to github repo:
+ DevOps project name: group_project01
+ Service connection name: dopham-sc
+ Pipeline:
  ![img_5.png](statics/images/img_5.png)

4. Tạo pipeline:
+ Gồm 2 stages: Build và Deploy
+ Build:
  + Checkout code
  + Build with maven
  + publish artifact (output from maven build)
+ Deploy stage:
  + download artifact
  + config environment variable for app service
  + deploy jar file(artifact) to app service