# Banking App
This is a banking application built using Java with Swing and JDBC. The backend is connected to a PostgreSQL database.
## Installation
To run the application, you need to have Java and PostgreSQL installed on your machine. Follow these steps:
1. Download the JAR and database dump from the releases page.
2. Import the database dump into your PostgreSQL server.
   ```bash
   psql -U USERNAME -d DATABASE -f dump.sql
   ```
3. Run the JAR file using the command:
   ```bash
   java "-Ddb.password=PASSWORD" "-Ddb.url=jdbc:postgresql://HOSTNAME:PORT/DATABASE" -jar banking-app.jar
   ```
Where:
- `PASSWORD` is the password for the PostgreSQL user.
- `HOSTNAME` is the hostname of your PostgreSQL server (default is `localhost`).
- `PORT` is the port number of your PostgreSQL server (default is `5432`).
- `DATABASE` is the name of your PostgreSQL database (default is `postgres`).
