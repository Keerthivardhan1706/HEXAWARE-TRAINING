-- Task 1
CREATE DATABASE SIS;
USE SIS;

CREATE TABLE Students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(15) UNIQUE NOT NULL
);

CREATE TABLE Teacher (
    teacher_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);


CREATE TABLE Courses (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    credits INT NOT NULL,
    teacher_id INT,
    FOREIGN KEY (teacher_id) REFERENCES Teacher(teacher_id) ON DELETE SET NULL
);

CREATE TABLE Enrollments (
    enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    course_id INT,
    enrollment_date DATE NOT NULL,
    FOREIGN KEY (student_id) REFERENCES Students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES Courses(course_id) ON DELETE CASCADE
);

CREATE TABLE Payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    amount DECIMAL(10,2) NOT NULL,
    payment_date DATE NOT NULL,
    FOREIGN KEY (student_id) REFERENCES Students(student_id) ON DELETE CASCADE
);


INSERT INTO Students (first_name, last_name, date_of_birth, email, phone_number) VALUES
('Keerthivardhan', 'Gembali', '2002-05-14', 'keerthivardhan374@gmail.com', '7207820958'),
('Ram', 'Kumar', '2002-04-12', 'ram3754@gmail.com', '9347294947'),
('Ananya', 'Sharma', '2014-03-18', 'ananya8294@gmail.com', '9883234211'),
('Amit', 'Verma', '2005-11-19', 'amit27728@gmail.com', '9789145621'),
('Sneha', 'Iyer', '2004-02-15', 'sneha288123@gmail.com', '9876543214'),
('Rohit', 'Patil', '2002-06-22', 'rohit6892@gmail.com', '9871278912'),
('Priya', 'Joshi', '2001-07-17', 'priya791@gmail.com', '9789167234'),
('Vikram', 'Singh', '2007-12-08', 'vikram.2939@gmail.com', '9098914523'),
('Neha', 'Gupta', '2008-03-09', 'neha2442@gmail.com', '9789126723'),
('Arjun', 'Mehta', '2009-09-10', 'arjun2342@gmail.com', '9876543219');


INSERT INTO Teacher (first_name, last_name, email) VALUES
('Rajesh', 'Sharma', 'rajesh.sharma@gmail.com'),
('Suresh', 'Rao', 'suresh.rao@gmail.com'),
('Meera', 'Nair', 'meera.nair@gmail.com');

INSERT INTO Courses (course_name, credits, teacher_id) VALUES
('Mathematics', 3, 1),
('Computer Science', 4, 2),
('Physics', 3, 3),
('Biology', 4, 1),
('History', 3, 2),
('Chemistry', 4, 3),
('Economics', 3, 1),
('Political Science', 3, 2),
('English Literature', 4, 3),
('Psychology', 3, 1);

INSERT INTO Enrollments (student_id, course_id, enrollment_date) VALUES
(1, 1, '2025-01-10'),
(2, 2, '2025-01-11'),
(3, 3, '2025-01-12'),
(4, 4, '2025-01-13'),
(5, 5, '2025-01-14'),
(6, 6, '2025-01-15'),
(7, 7, '2025-01-16'),
(8, 8, '2025-01-17'),
(9, 9, '2025-01-18'),
(10, 10, '2025-01-19');

INSERT INTO Payments (student_id, amount, payment_date) VALUES
(1, 500.00, '2025-02-01'),
(2, 600.00, '2025-02-02'),
(3, 550.00, '2025-02-03'),
(4, 620.00, '2025-02-04'),
(5, 580.00, '2025-02-05'),
(6, 630.00, '2025-02-06'),
(7, 700.00, '2025-02-07'),
(8, 750.00, '2025-02-08'),
(9, 800.00, '2025-02-09'),
(10, 850.00, '2025-02-10');

--Task 2
INSERT INTO Students (first_name, last_name, date_of_birth, email, phone_number)
VALUES ('John', 'Doe', '1995-08-15', 'john.doe@gmail.com', '9876543220');

INSERT INTO Enrollments (student_id, course_id, enrollment_date)
VALUES (1, 2, CURDATE());

UPDATE Teacher
SET email = 'updated.rajesh.sharma@gmail.com'
WHERE teacher_id = 1;

DELETE FROM Enrollments
WHERE student_id = 1 AND course_id = 2;

UPDATE Courses
SET teacher_id = 2
WHERE course_id = 3;

DELETE FROM Students
WHERE student_id = 1;

UPDATE Payments
SET amount = 999.99
WHERE payment_id = 1;

-- Task 3

SELECT s.student_id, s.first_name, s.last_name, SUM(p.amount) AS total_payment
FROM Students s
JOIN Payments p ON s.student_id = p.student_id
WHERE s.student_id = 1
GROUP BY s.student_id;

SELECT c.course_name, COUNT(e.student_id) AS student_count
FROM Courses c
LEFT JOIN Enrollments e ON c.course_id = e.course_id
GROUP BY c.course_id;

SELECT s.student_id, s.first_name, s.last_name
FROM Students s
LEFT JOIN Enrollments e ON s.student_id = e.student_id
WHERE e.student_id IS NULL;

SELECT s.first_name, s.last_name, c.course_name
FROM Students s
JOIN Enrollments e ON s.student_id = e.student_id
JOIN Courses c ON e.course_id = c.course_id;

SELECT t.first_name, t.last_name, c.course_name
FROM Teacher t
JOIN Courses c ON t.teacher_id = c.teacher_id;

SELECT s.first_name, s.last_name, e.enrollment_date
FROM Students s
JOIN Enrollments e ON s.student_id = e.student_id
JOIN Courses c ON e.course_id = c.course_id
WHERE c.course_name = 'Mathematics';

SELECT s.first_name, s.last_name
FROM Students s
LEFT JOIN Payments p ON s.student_id = p.student_id
WHERE p.student_id IS NULL;

SELECT c.course_name
FROM Courses c
LEFT JOIN Enrollments e ON c.course_id = e.course_id
WHERE e.course_id IS NULL;

SELECT DISTINCT s.student_id, s.first_name, s.last_name
FROM Enrollments e1
JOIN Enrollments e2 ON e1.student_id = e2.student_id AND e1.course_id <> e2.course_id
JOIN Students s ON e1.student_id = s.student_id;

SELECT t.teacher_id, t.first_name, t.last_name
FROM Teacher t
LEFT JOIN Courses c ON t.teacher_id = c.teacher_id
WHERE c.course_id IS NULL;

-- Task 4

SELECT AVG(student_count) AS average_enrollment
FROM (
    SELECT course_id, COUNT(student_id) AS student_count
    FROM Enrollments
    GROUP BY course_id
) AS course_enrollments;

SELECT s.student_id, s.first_name, s.last_name, p.amount
FROM Payments p
JOIN Students s ON p.student_id = s.student_id
WHERE p.amount = (SELECT MAX(amount) FROM Payments);

SELECT c.course_id, c.course_name, COUNT(e.student_id) AS enrollment_count
FROM Enrollments e
JOIN Courses c ON e.course_id = c.course_id
GROUP BY c.course_id, c.course_name
HAVING COUNT(e.student_id) = (
    SELECT MAX(enrollment_count) FROM (
        SELECT COUNT(student_id) AS enrollment_count FROM Enrollments GROUP BY course_id
    ) AS max_enrollments
);

SELECT t.teacher_id, t.first_name, t.last_name, COALESCE(SUM(p.amount), 0) AS total_payments
FROM Teacher t
LEFT JOIN Courses c ON t.teacher_id = c.teacher_id
LEFT JOIN Enrollments e ON c.course_id = e.course_id
LEFT JOIN Payments p ON e.student_id = p.student_id
GROUP BY t.teacher_id, t.first_name, t.last_name;

SELECT s.student_id, s.first_name, s.last_name
FROM Students s
WHERE (
    SELECT COUNT(DISTINCT e.course_id)
    FROM Enrollments e
    WHERE e.student_id = s.student_id
) = (SELECT COUNT(*) FROM Courses);

SELECT t.teacher_id, t.first_name, t.last_name
FROM Teacher t
WHERE t.teacher_id NOT IN (SELECT DISTINCT teacher_id FROM Courses WHERE teacher_id IS NOT NULL);

SELECT AVG(TIMESTAMPDIFF(YEAR, date_of_birth, CURDATE())) AS average_age FROM Students;

SELECT c.course_id, c.course_name
FROM Courses c
WHERE NOT EXISTS (SELECT 1 FROM Enrollments e WHERE e.course_id = c.course_id);

SELECT e.student_id, e.course_id, SUM(p.amount) AS total_payment
FROM Enrollments e
JOIN Payments p ON e.student_id = p.student_id
GROUP BY e.student_id, e.course_id;

SELECT s.student_id, s.first_name, s.last_name
FROM Students s
JOIN Payments p ON s.student_id = p.student_id
GROUP BY s.student_id, s.first_name, s.last_name
HAVING COUNT(p.payment_id) > 1;

SELECT s.student_id, s.first_name, s.last_name, SUM(p.amount) AS total_payment
FROM Students s
JOIN Payments p ON s.student_id = p.student_id
GROUP BY s.student_id, s.first_name, s.last_name;

SELECT c.course_id, c.course_name, COUNT(e.student_id) AS student_count
FROM Courses c
LEFT JOIN Enrollments e ON c.course_id = e.course_id
GROUP BY c.course_id, c.course_name;

SELECT s.student_id, s.first_name, s.last_name, AVG(p.amount) AS average_payment
FROM Students s
JOIN Payments p ON s.student_id = p.student_id
GROUP BY s.student_id, s.first_name, s.last_name;


