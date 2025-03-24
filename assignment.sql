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
('Keerthivardhan', 'Gembali', '2000-01-01', 'keerthivardhan.gembali@gmail.com', '9876543210'),
('Ram', 'Kumar', '2001-02-02', 'ram.kumar@gmail.com', '9876543211'),
('Ananya', 'Sharma', '2002-03-03', 'ananya.sharma@gmail.com', '9876543212'),
('Amit', 'Verma', '2003-04-04', 'amit.verma@gmail.com', '9876543213'),
('Sneha', 'Iyer', '2004-05-05', 'sneha.iyer@gmail.com', '9876543214'),
('Rohit', 'Patil', '2005-06-06', 'rohit.patil@gmail.com', '9876543215'),
('Priya', 'Joshi', '2006-07-07', 'priya.joshi@gmail.com', '9876543216'),
('Vikram', 'Singh', '2007-08-08', 'vikram.singh@gmail.com', '9876543217'),
('Neha', 'Gupta', '2008-09-09', 'neha.gupta@gmail.com', '9876543218'),
('Arjun', 'Mehta', '2009-10-10', 'arjun.mehta@gmail.com', '9876543219');


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


