# API-COMERCIO

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

Este projeto é uma APIRest monolítica, construída usando Java, Spring, H2 como banco de dados.

This project is a monolithic APIRest, built using Java, Spring, H2 as the database.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Database](#database)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)
- [Observation](#observation)

## Installation

1. Clone the repository:

```bash
git clone https://github.com/IgorTecnologia/api-comercio.git
```

2. Install dependencies with Maven

## Usage

1. Start the application with Maven
2. The API will be accessible at http://localhost:8080

## Collection Postman

Import this URL into your Postman to use the ready-made HTTP methods to make requests/responses:

URL: https://api.postman.com/collections/30344579-a0674725-66fb-4f96-a80f-2d5e8e3f916d?access_key=PMAT-01J50SK085XAN5VX76WMENESKG


## API Endpoints
The API provides the following endpoints:

**GET USERS**
```markdown
GET /users - Retrieve a pagination of all users.
```
```json
"content": [
        {
            "id": 1,
            "firstName": "Igor",
            "lastName": "Gonçalves",
            "email": "igor@gmail.com",
            "password": "1234",
            "roles": [
                {
                    "id": 1,
                    "authority": "Assistent"
                }
            ]
        }
```
**GET USERS**
```markdown
GET /users/firstName/{firstName} - Retrieve a list of users filtered by firstName.
Exemple: GET /users/firstName/Igor
```
```json
   [
    {
        "id": 4,
        "firstName": "Igor",
        "lastName": "Gonçalves",
        "email": "igor@gmail.com",
        "password": "1234",
        "roles": []
    }
]

```
**GET USERS/ID**
```markdown
GET /users/id - Retrieve a single user by id.
```

```json
{
    "id": 1,
    "firstName": "Igor",
    "lastName": "Gonçalves",
    "email": "igor@gmail.com",
    "password": "1234",
    "roles": [
        {
            "id": 1,
            "authority": "Assistent"
        }
    ]
}
```

**POST USERS**
```markdown
POST /users - Register a new user into the App
```
```json
{
    "id": 3,
    "firstName": "Vitória",
    "lastName": "Gonçalves",
    "email": "vitoria@gmail.com",
    "password": "1234567",
    "roles": [
        {
            "id": 2,
            "authority": "Admin"
        }
    ]
}
```
**PUT USERS**
```markdown
PUT/users/id - Update a user in the application by id.
```
```json
{
    "firstName": "Nanci",
    "lastName": "Maria",
    "email": "nanci@gmail.com",
    "password": "1234567",
    "roles": [
        {
            "id": 2
        }
    ]
}
```
**DELETE USERS**
```markdown
DELETE/users/id - Delete a user in the application by id.
return HTTP status: 204 NO CONTENT

```
## Database
The project utilizes [H2 Database](https://www.h2database.com/html/tutorial.html) as the database.

There are settings for PostgreSQL, MySQL databases, you can use them by changing the application.properties file.

The application comes with the H2 database as standard.

## Technologies Used

- JDK 17
- Spring Boot
- Maven
- H2 Database
- Spring Tool Suite 4
- Postman

## Observation
This APIRest provides other endpoints besides users, such as:
/categories
/products
/role
Located in the Application resources layer.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request to the repository.

When contributing to this project, please follow the existing code style, [commit conventions](https://www.conventionalcommits.org/en/v1.0.0/), and submit your changes in a separate branch.

Contribuições são bem-vindas! Se você encontrar algum problema ou tiver sugestões de melhorias, abra um problema ou envie uma solicitação pull ao repositório.

Ao contribuir para este projeto, siga o estilo de código existente, [convenções de commit](https://medium.com/linkapi-solutions/conventional-commits-pattern-3778d1a1e657), e envie suas alterações em uma branch separado.

![Java mascot](https://img-c.udemycdn.com/course/750x422/3569929_d77b.jpg)
