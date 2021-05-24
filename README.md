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

## Test list
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
- [ ] pass filepath to BirthdayGreetings
- [ ] pass SMTP server configuration to BirthdayGreetings 
- [X] handle external resources
- [ ] Employee file is empty -> log error to console (message empty file) + no email 
- [ ] Wrong file format
  - [ ] estensione
  - [ ] formato mail non valido
  - [ ] formato data non valido
  - [ ] numero di colonne errato
  - [ ] data non valida, e.g. 2021/02/29
 