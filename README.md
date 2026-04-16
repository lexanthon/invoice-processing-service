# Invoice Processing Service

Java / Spring Boot service for scheduled parsing and persistence of invoice data in a database-backed workflow.

## Reliability hardening and edge cases covered

The latest changes make invoice processing safer under retries,
replays, restarts, and concurrent execution. In practice, the service
now avoids unsafe reprocessing, handles duplicate main-invoice creation
safely, and stops downstream child persistence when the parent invoice
is already effectively present.

- Scheduled processing is protected against weak state transitions,
  accidental re-entry, and double-processing.
- Main-invoice creation is idempotent for the same logical input,
  using `invoiceFileId`.
- Concurrent or retry-driven duplicate main-invoice inserts are handled
  safely as benign duplicate cases, not hard failures.
- After a benign duplicate main-invoice outcome, UBL child persistence
  does not continue, preventing duplicate or inconsistent child data.
- Local development reliability was also improved through Helger
  dependency updates and datasource adjustments.


## Known limitation

Stale `PROCESSING` rows are not yet automatically recovered after crash or hard stop.

## Overview

The application polls source rows from `invoice_file`, claims work before processing, parses supported invoice payloads, persists a main `Invoice` row plus related child records, and finalizes source-row status.

The implementation focuses on:
- scheduled database-backed processing
- controlled state transitions
- duplicate-processing prevention
- main-invoice idempotency
- transaction-boundary edge cases

## Supported invoice types

- `UBL`
- `CII`

## Reliability features

- atomic claim from `PENDING` to `PROCESSING`
- guarded final status transitions
- protection against duplicate main-invoice creation
- benign handling of duplicate-save races for the main invoice record

## Status model

- `1 = PENDING`
- `2 = PARSED`
- `3 = FAILED`
- `4 = PROCESSING`

## Tech stack

- Java 17
- Spring Boot 2.1.2
- Spring Data JPA / Hibernate
- MySQL
- Helger UBL / CII libraries
- Maven Wrapper

## Configuration

Main config file:

`src/main/resources/application.properties`

Set local database credentials before running:

    spring.datasource.username=YOUR_DB_USERNAME
    spring.datasource.password=YOUR_DB_PASSWORD

Scheduler cron:

`timer=0/3 * * * * ?`

## Build

Windows:

    .\mvnw.cmd clean package

macOS / Linux:

    ./mvnw clean package

## Run

Windows:

    .\mvnw.cmd spring-boot:run

macOS / Linux:

    ./mvnw spring-boot:run

## Tests

Windows:

    .\mvnw.cmd test

macOS / Linux:

    ./mvnw test

## Known limitation

Stale `PROCESSING` rows are not yet automatically recovered after crash or hard stop.
