# RailWay Reservation System

A desktop train reservation project built with Java Swing, JDBC and SQLite. The database is created automatically as `reservation.db` beside the program.

## Run it

Install Java 8+ and Maven, then from this folder run:

```powershell
mvn clean package
java -jar target/train-reservation-system-1.0.0.jar
```

Default sign-in: **admin** / **admin123**

## Included features

- Database-backed login using parameterized SQL
- Reservation form with automatic train name lookup
- Unique, readable PNR generation and booking confirmation
- PNR lookup and confirmation-based cancellation
- Required-field, train-number and ISO date (`yyyy-MM-dd`) validation

SQLite was selected so that no database server is needed. The project uses the Xerial SQLite JDBC dependency and `PreparedStatement` for user-provided database values.

## Project notes

The train list is intentionally a small in-code catalogue for a focused classroom project. Add rows to `Database.TRAINS` to extend it.
