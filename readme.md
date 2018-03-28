# SECA project 2

### This is the repository for the backend the application. The frontend can be found at https://git.generalassemb.ly/katebrenner/VehicleReportsBackend

This Application utilizes the City for NY API, specifically, motor vehicle collision information. This application is scalable because it has been set up as a microservices application, with one API called accidents. Feature Tests were used to test the environment and Unit Tests were used to ensure the API & database are working properly.

The app currently has 3 routes on the Front End-

* Homepage displays all Collision data, ordered by date. Each Item can be flagged for later investigation.
* Flagged page displays all accidents that have been flagged as well as notes that have added.
* Map page shows a map of all accidents

Technologies used:
Backend-

* Java
* Docker
* Spring
* Postgresql
* Microservices

Frontend-

* React
* React Router
* React Google Maps