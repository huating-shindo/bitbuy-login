Simple Login/Register system written using Spring Boot/Spring Security.


#DB
Use h2 memory db. By default, two users 'user'(password: user)/'admin'(password: admin) are created on startup. Refer to the [DB script](src/main/resources/data.sql)

#Endpoint

## register
POST /api/register
{
username,
password
}

code:
```
curl --location --request POST 'http://localhost:8080/api/register' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=DDBC5F95A5AAE5F1CDD2FB40C330691B' \
--data-raw '{
	"username":"test",
	"password":"test"
}'
```

## login
POST /api/login
{
username,
password
}
code
```
curl --location --request POST 'http://localhost:8080/api/login' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=DDBC5F95A5AAE5F1CDD2FB40C330691B' \
--data-raw '{
	"username":"admin",
	"password":"admin"
}'
```

## get user info
GET /api/users/{uuid}

code:
```
curl --location --request GET 'http://localhost:8080/api/users/1' \
--header 'Authorization: Basic dXNlcjp1c2Vy' \
--header 'Cookie: JSESSIONID=DDBC5F95A5AAE5F1CDD2FB40C330691B' \
--data-raw ''
```

## update user info
POST /api/users/{uuid}
{
name,
email,
phone
}

code:
```
curl --location --request POST 'http://localhost:8080/api/users/1' \
--header 'Authorization: Basic dXNlcjp1c2Vy' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=DDBC5F95A5AAE5F1CDD2FB40C330691B' \
--data-raw '{
	"username":"next",
	"password":"123",
	"email": "next@gmail.com",
	"phone": "1234567891"
}'
```
