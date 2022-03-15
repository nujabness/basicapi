# BASIC API

- Date : **03/2022**
- Subject: *REST API*
- Project : *Spring Boot User API*

## Auteur(s)

|Nom|Prénom|
|--|--|
*EL ASSOURI* | *Mohammed*|

##How to start
  ```shell
    mvnw spring-boot:run
  ```

## Routes

- Get User
  - **Path:**: localhost:8080/users/{id}
  - **Method:** GET

  - **Exemple Response :**
    ```json
    {
        "name": "Mohammed EL ASSOURI",
        "birthDate": "05/11/1998",
        "country": "France",
        "phoneNumber": "0698554441",
        "gender": "MALE"
    }
    ```


- Create User
  - **Path:** localhost:8080/users
  - **Method:** POST
  - **Exemple Request :**
    ```json
    {
         "name": "string",
         "birthDate": "dd/mm/yyyy",
         "country": "string",
         "phoneNumber": "0000000000",
         "gender": "MALE|FEMAL"
    }
    ```
