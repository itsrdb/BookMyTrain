# BookMyTrain

BookMyTrain provies a train booking API service. Written in Java Spring Boot leveraging Spring Security. 

## Table of Contents

* [Overview](#overview)
* [Getting Started](#getting-started)
    * [Prerequisites](#prerequisites)
    * [Installation](#installation) (if applicable)
    * [Authentication](#authentication)
* [API Reference](#api-reference)
    * [Resources](#resources)
        * [List of resources](#list-of-conductors) (examples with links)
    * [Request Structure](#request-structure) (common request format)
    * [Response Structure](#response-structure) (common response format)
    * [Error Codes](#error-codes) (list of error codes and descriptions)
* [Examples](#examples) (code snippets for common use cases)

## Overview

* This API uses protected endpoints which can only be accessed by the Admin or authenticated Users.
* Token based authentication is used for Role based privilages and maintaining session info.
* Semaphores are used to maintain atomicity in transactions, thus allowing multiple uses to book at the same time without breaking.
* Database used is PostgreSQL.

## Getting Started

1. By default server port is set to 3000 (Can be changed in Spring application.properties)
2. Create a role and database in PostgreSQL
3. I'm using database named '__train_db__' and postgreSQL port being set to 6432. The database name and port should be replaced in case you have set it differently.
(Can be changed in Spring application.properties)
```
spring.datasource.url=jdbc:postgresql://localhost:6432/train_db
```
4. Set you username and password configured for postgreSQL in application.properties:
```
spring.datasource.username=rohitborkar
spring.datasource.password=password
```
5. JWT secret key can be replaces with a valid HMAC-SHA256 key, can be replaced in application.properties. (Not necessary)
```
security.jwt.secret-key=
```

## API Features

### 1. Registation, Assumed registration should be open to all users (atleast for testing).<br>
  For normal users (Role: User)
```
http://localhost:3000/api/register [POST]((Not restricted)
```
  For admin users (Role: Admin)
```
  http://localhost:3000/api/registerAdmin [POST]((Not restricted)
```
  Accepts JSON of form:
```
{
    "username": "rohit",
    "password": "password" 
}
```
### 2. Login and Auth Token generation
```
http://localhost:3000/api/login [POST](Not restricted)
```
  
  Accepts JSON of form:
```
{
    "username": "rohit",
    "password": "password" 
}
```
Responds with __token__ and __expiry_time__:
```
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJpdHNyZGI4IiwiaWF0IjoxNzE0MDA4MjcxLCJleHAiOjE3MTQwMTE4NzF9.ChfzRVYnJYt8ZbZz8CLk06eiAXZzHAKw0WdOy3vCFtk",
    "expiresIn": 3600000
}
```
### 3. Adding Train data
```
http://localhost:3000/api/addTrain [POST](Restricted to Admin)
```
  Accepts JSON of form:
```
{
    "id": 199,
    "name": "Nagpur SF" ,
    "schedules": [
        {
            "stationName": "Nagpur",
            "arrivalTime": "09:30"
        },
        {
            "stationName": "Wardha",
            "arrivalTime": "12:45"
        },
        {
            "stationName": "Sakoli",
            "arrivalTime": "18:20"
        }
    ],
    "noOfSeats": 60
}
```
### 4. Fetching Trains on a date
Returns a list of trains with available seats on a particular date:
```
http://localhost:3000/api/getTrains [GET](Not Restricted)
```
  Accepts JSON of form:
```
{
    "source": "Nagpur",
    "destination": "Jabalpur",
    "date": "2024-04-26"
}
```
  Returns a list of trains with available seats:
```
[
    {
        "id": 421,
        "name": "Nashik SF Express",
        "source": "Nagpur",
        "destination": "Jabalpur",
        "availableSeats": 48
    }
]
```
### 5. Booking seat
Books train ticket from source to destination on provided date while validating and decreasing number of available seats by 1.
```
http://localhost:3000/api/bookSeat [POST](Restricted to authorized users only (Assumed both User and Admin should be allowed))
```
  Accepts JSON of form:
```
{
    "source": "Nagpur",
    "destination": "Jabalpur",
    "date": "2024-04-25",
    "train_id": 421
}
```
  Returns with a response status OK and message ___Booking successful__ for successful booking.
### 6. Get All Bookings
Returns a list of all bookings made my the user on any date.
```
http://localhost:3000/api/getAllBookings [GET](Restricted to authorized users only)
```
  Returns a list of all bookings made:
```
[
    {
        "trainNumber": 145,
        "trainName": "Pune SF",
        "seatNumber": 3,
        "source": "Nagpur",
        "destination": "Delhi",
        "bookingDate": "2024-04-26"
    },
    {
        "trainNumber": 421,
        "trainName": "Nashik SF Express",
        "seatNumber": 13,
        "source": "Nagpur",
        "destination": "Jabalpur",
        "bookingDate": "2024-04-26"
    }
]
```
### 6. Get All Bookings for a particular date
Returns a list of all bookings made my the user on provided date.
```
http://localhost:3000/api/getBookingsOnDate [GET](Restricted to authorized users only)
```
  Returns a list of all bookings made for that date:
```
[
    {
        "trainNumber": 421,
        "trainName": "Nashik SF Express",
        "seatNumber": 1,
        "source": "Nagpur",
        "destination": "Jabalpur",
        "bookingDate": "2024-04-25"
    }
]
```

## Few Examples
#### * Database Schema 
<img width="210" alt="Screenshot 2024-04-25 at 8 39 36 AM" src="https://github.com/itsrdb/BookMyTrain/assets/15973523/2c706100-2e62-411b-bbd7-4da74e2d98aa">

#### * Fetching bookings on a prticular date
<img width="1414" alt="Screenshot 2024-04-25 at 7 11 40 AM" src="https://github.com/itsrdb/BookMyTrain/assets/15973523/d7f38723-d54d-4dd6-8a8b-ac7bcbbf87ad">

#### * Booking a train
<img width="1414" alt="Screenshot 2024-04-25 at 4 07 21 AM" src="https://github.com/itsrdb/BookMyTrain/assets/15973523/1d9cc07b-469c-44c8-8ba6-d4eb0f6bef0b">

#### * JWT Expiration
<img width="1413" alt="Screenshot 2024-04-25 at 6 35 53 AM" src="https://github.com/itsrdb/BookMyTrain/assets/15973523/690256ae-977c-4fa4-b9ab-3a688d938b6e">
