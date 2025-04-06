CREATE TABLE attendance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(100),
    student_id VARCHAR(20),
    subject VARCHAR(100),
    date DATE,
    status VARCHAR(10)
);
