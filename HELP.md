# Getting Started

 Demo:  
 * [demo on youtube](https://youtu.be/Up4Iiuw8_-4)
 * [local swagger](http://localhost:8000/swagger-ui.html)

## Reference Documentation
### Properties file

Now, dev  is active profile. The file should have the next properties
```json

spring.datasource.url=
spring.datasource.username=
spring.datasource.password=

# Allows Hibernate to generate SQL optimized for a particular DBMS. better SQL for the chosen database
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
#drop n create table again, good for testing, comment this in production
#spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.hibernate.ddl-auto=create
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true

# ===================================================================
# logging settings
# ===================================================================
logging.file.path=
logging.level.com.se.service=debug


#=====================================================================
# AWS
# ===================================================================
amazon.access-key=
amazon.secret-key=
amazon.region-static=
#===================================================================
# notification settings
#===================================================================
# send from this email 
notification.sp.sender=

# SENDGRID CONFIG (SendGridAutoConfiguration)
spring.sendgrid.api-key=
send-grid.mail.from=
```


### build 
```bash
	 mvn package spring-boot:repackage
```

check with sonar
```bash
mvn clean install & sonar:sonar
```

## Demo case 
 The test instructions to demo how to project work 

### step 1. create a set of placeholders that we plan to use this template Controller Template
```json
{
  "name": "attr1"
}
```

### Step 2. Create notification group 
```json
{
  "enabled": true,
  "name": "notification-group-name"
}
```

### Step 3. Create notification
We will use the next simpler html body with 2 attributes
```html
<head>
   <style>body {background-color: powderblue;}h1 {color: blue;}p {color: red;}</style>
</head>
<body>
   <p>Password</p>
   <p>${attr1}</p>
   <p> Some attribute 2: ${attr2} </p>
   <p>Regards, SE</p>
</body>
</html>
```
The html template should be minimized to use in swagger
```json
{
  "enabled": true,
  "htmlContent": "<head><style>body {background-color: powderblue;}h1 {color: blue;}p {color: red;}</style></head> <body> <p>Password</p> <p>${attr1}</p><p> Some attribute 2: ${attr2} </p> <p>Regards, SE</p></body> </html>",
  "name": "notification_name",
  "parentGroupId": 2
}
```

### Send notification message

```json
{
  "destinationAddressList":[
    {
      "alertType":"EMAIL",
      "destinationAddress":"skiba.eugeniy@gmail.com"
    }
  ],
  "notificationId":1,
  "placeholdersMap":{
    "attr1":"1111111",
    "attr2s":"22222222"
  },
  "subject":"subject text"
}
```