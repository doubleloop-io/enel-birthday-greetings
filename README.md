## Description
Write a program that loads a set of employee records from a flat file and sends a greetings email to all employees whose birthday is today.
The flat file is a sequence of records in plain text, separated by newlines; the following is a sample of the file format:
```
last_name, first_name, date_of_birth, email
Doe, John, 1982/10/08, john.doe@foobar.com
Ann, Mary, 1975/09/11, mary.ann@foobar.com
```
The greetings email has subject `Happy birthday!` and body `Happy birthday, dear John!` (where `John` is the first name of the employee).

The application should be runnable from command line.

## Reference
http://matteo.vaccari.name/blog/archives/154

## Dependencies
- server SMTP
- File System
- Time

## Responsibilities
- [ ] Read from file
- [ ] Parse CSV
- [X] Config mail client
- [X] Compose email
- [X] Send email
- [X] Check birthday

## Test list
- [ ] simplify BirthDate instantiation in tests
- [ ] CsvEmployeeCatalog
  - [X] one employee
  - [X] many employees
  - [ ] no employees
  - [ ] empty file
  - [ ] file not found
  - [ ] Wrong file format
    - [ ] wrong file extension
    - [ ] invalid date format
    - [ ] wrong number of columns
    - [ ] invalid date, e.g. 2021/02/29
- [ ] MailInfo
  - [ ] check mail format

- [X] SmtpMailSender
  - [X] send one mail
  - [X] send many mail
  - [X] SMTP is down
- [X] BirthDate
  - [X] birthDate 14/09/1995, today 14/09/2021 -> is birthday
  - [X] birthDate 14/09/1995, today 15/09/2021 -> is NOT birthday
  - [X] birthDate 29/02/2020, today 28/02/2021 -> is birthday
  - [X] birthDate 29/02/2020, today 28/02/2024 -> is NOT birthday
- [X] pass filepath to it.enel.kata.birthday_greetings.BirthdayGreetings
- [X] pass SMTP server configuration to it.enel.kata.birthday_greetings.BirthdayGreetings
- [X] execute console application
- [X] one greeting
  Given:
  ```
  last_name, first_name, date_of_birth, email
  Doe, John, 1982/10/08, john.doe@foobar.com
  Ann, Mary, 1975/09/11, mary.ann@foobar.com
  ```
  
  When:
  Send greetings Today: 2021/10/08
  
  Then:
  email to John Doe, subject `Happy birthday!`, body `Happy birthday, dear John!`
- [X] Many greetings
  Given:
  ```
  last_name, first_name, date_of_birth, email
  Doe, John, 1982/10/08, john.doe@foobar.com
  Ann, Mary, 1975/09/11, mary.ann@foobar.com
  Andrea, Vallotti, 1977/09/11, andrea.vallotti@foobar.com
  ```

  When:
  Send greetings Today: 2021/09/11

  Then:
  email to Mary Ann and Andrea Vallotti, check body and subject
- [X] No greetings
  Given:
  ```
  last_name, first_name, date_of_birth, email
  Doe, John, 1982/10/08, john.doe@foobar.com
  Ann, Mary, 1975/09/11, mary.ann@foobar.com
  Andrea, Vallotti, 1977/09/11, andrea.vallotti@foobar.com
  ```

  When:
  Send greetings Today: 2021/09/12

  Then:
  no email
- [X] handle external resources
