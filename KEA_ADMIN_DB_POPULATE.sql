USE KEA_ADMIN_DB;

INSERT INTO userTypes VALUES 
('admin'), ('student'), ('teacher');

INSERT INTO users VALUES 
('Paul69', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 'student'),
('Jarlen', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 'admin'),
('MuskelMartin', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 'teacher'),
('BobMARLEY', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 'student'),
('Weedlova420', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 'teacher'), 
('tyndmave12', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 'admin');

INSERT INTO students VALUES 
(1, 'John Hansen', 'BobMARLEY'), 
(2, 'Paul PopOff', 'Paul69');

INSERT INTO teachers VALUES 
(1, 'Martin Mcoursesartensen', 'MuskelMartin'), 
(2, 'Jakob Wulff', 'Weedlova420');

INSERT INTO courses VALUES 
(1, 
10, 
'Java in IDE', 
'this is a description of this study', 
'Internal oral exam based on hand in product. Graded based on the 7-scale.',
35,
'English', 
'Individual work and exam. Communication takes place via our Ryver channel WD-2018-F-NODEJS',
'Students will be able to code a full stack web based application, set-up a NODEJS server in the cloud and decide the best possible use of MongoDB',
'true',
50,
15,
'Full Stack NodeJs',
'Full Stack NodeJs',
1,
'Students must know HTML, CSS, JS, PHP and MySQL.',
3,
'Web Development'),
(2, 
15, 
'Database definition language and database data manipulation language, MYSQL', 
'this is a description of this course', 
'Written Exam, Repport oriented Exam, graded based on the 7-scale',
20,
'Dansk', 
'Team based work and exam. Team oriented course, and is valued highly.',
'Students will be able to create and manipulate a database',
'true',
38,
12,
'Rational Database MYSQL',
'Rational Database MYSQL',
2,
'Students must have knowlegde of the programming language JAVA, database is builed on top of a Java Application',
2,
'Rational Database'),
(3, 
20, 
'Secure Network based Communication Applications, and learn the different networks layers', 
'Network security for securing your application', 
'Oral and Written Exam, students are graded based by the 7-scale template',
38,
'English', 
'Team based work and exam. Team oriented course, and is valued highly.',
'Students will be able to identify the different network layers and secure them by best practices',
'true',
42,
10,
'Network Security',
'Network Security',
1,
'Students must have knowlegde of the programming language JAVA',
4,
'Network Security STU');

INSERT INTO teacher_course VALUES
(1, 1), (1,2), (2,3);

INSERT INTO student_course VALUES
(1, 1), (1,3), (2,2), (2,3);

INSERT INTO applications VALUES
(1, 1, current_timestamp()), 
(2, 1, current_timestamp());