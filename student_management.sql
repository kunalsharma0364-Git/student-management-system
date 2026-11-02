
CREATE DATABASE IF NOT EXISTS student_management;

USE student_management;

CREATE TABLE IF NOT EXISTS students (
                                        id INT PRIMARY KEY,
                                        name VARCHAR(100) NOT NULL,
    age INT NOT NULL CHECK (age BETWEEN 1 AND 120),
    course VARCHAR(100) NOT NULL,
    marks DECIMAL(5,2) NOT NULL CHECK (marks BETWEEN 0 AND 100)
    );
