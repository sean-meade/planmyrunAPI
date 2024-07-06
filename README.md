# planmyrunAPI

Link to live hosted API Swagger UI: https://planmyrunapi.onrender.com/swagger-ui/index.html

API to perform CRUD functionality of routes for PlanMyRun V4 with a PostgreSQL database.

#### Database Models

A PostgreSQL database hosted on [elephantsql.com](https://www.elephantsql.com/) is used to hold the route data.

##### Route Model

A model describing a route.

| Key        | Name | Type         | Extra Info               |
| ---------- |------|--------------|--------------------------|
| PrimaryKey | id   | INTEGER      | Unique id                |
|            | name | VARCHAR(255) | Name of Route - Not Null |

##### Point Model

A model describing a point of a route

| Key        | Name      | Type    | Extra Info                    |
|------------|-----------|---------|-------------------------------|
| PrimaryKey | id        | INTEGER | Unique id                     |
|            | latitude  | DOUBLE  | latitude of point - Not Null  |
|            | longitude | DOUBLE  | longitude of point - Not Null |
| ForeignKey | route_id  | BIGINT  | Reference to route by ID      |

#### API for PlanMyRun

The API is built using the Spring Boot framework.

This is the base URL when running locally
BASE_URL = http://localhost:8080/api/route/

Included is swagger for easier use of each request and can be found at: http://localhost:8080/swagger-ui/index.html

##### Get all routes

Type of HTTP request: `GET`

URL: {BASE_URL}/all

On a successful request it returns a status of `200` and all the routes in the database as a list:

```
[
  {
    "id": 1,
    "name": "My First Route Created",
    "points": [
      {
        "id": 1,
        "latitude": 52.3456,
        "longitude": -3.5423
      },
      {
        "id": 2,
        "latitude": 53.7426,
        "longitude": -2.9425
      }
    ]
  },
  {
    "id": 3,
    "name": "Third Route",
    "points": [
      {
        "id": 8,
        "latitude": 12.3456,
        "longitude": -3.5423
      },
      {
        "id": 9,
        "latitude": 13.3456,
        "longitude": -4.5423
      },
      {
        "id": 10,
        "latitude": 11.3456,
        "longitude": -5.5423
      }
    ]
  },
  .
  .
  .
]
```

##### Get route by id

Type of HTTP request: `GET`

URL: {BASE_URL}/{id}

Where `id` is the id of the route you want to get.

On a successful request it returns a status of `200` and the route from the database:

```
{
  "id": 3,
  "name": "Third Route",
  "points": [
    {
      "id": 8,
      "latitude": 12.3456,
      "longitude": -3.5423
    },
    {
      "id": 9,
      "latitude": 13.3456,
      "longitude": -4.5423
    },
    {
      "id": 10,
      "latitude": 11.3456,
      "longitude": -5.5423
    }
  ]
}
```

##### Add a route

Type of HTTP request: `POST`

URL: {BASE_URL}/create

Package sent with `POST` request:

```
{
  "name": "Third Route",
    "points": [
      {"latitude": 12.3456, "longitude": -3.5423},
      {"latitude": 13.3456, "longitude": -4.5423},
      {"latitude": 11.3456, "longitude": -5.5423}
    ]
}
```

On a successful request it returns `200` and the newly added route with an `id`:

```
{
  "id": 3,
  "name": "Third Route",
  "points": [
    {
      "id": 8,
      "latitude": 12.3456,
      "longitude": -3.5423
    },
    {
      "id": 9,
      "latitude": 13.3456,
      "longitude": -4.5423
    },
    {
      "id": 10,
      "latitude": 11.3456,
      "longitude": -5.5423
    }
  ]
}
```

##### Edit an existing route in the database

Type of HTTP request: `PUT`

URL: {BASE_URL}/edit/{id}

Where `id` is the id of the route you want to edit.

Package sent with `PUT` request:

```
{
  "name": "Updated Route",
    "points": [
      {"latitude": 12.3456, "longitude": -3.5423},
      {"latitude": 33.3456, "longitude": -4.5423}
    ]
}
```

Returns with status `200` and the route you just edited:

```
{
  "id": 2,
  "name": "Updated Route",
  "points": [
    {
      "id": 6,
      "latitude": 33.3456,
      "longitude": -4.5423
    },
    {
      "id": 7,
      "latitude": 12.3456,
      "longitude": -3.5423
    }
  ]
}
```

##### Delete an existing route from the database

Type of HTTP request: DELETE

URL: {BASE_URL}/delete/{id}

Where `id` is the id of the route you want to delete.

## Create Database and entries

### Create Database

To create the PostgreSQL database with the proper schema use the following scripts:

```psql
CREATE TABLE route (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE point (
    id SERIAL PRIMARY KEY,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    route_id BIGINT,
    FOREIGN KEY (route_id) REFERENCES route(id)
);
```

## Future improvements:

- Add a user model and assign users their own routes