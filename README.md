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