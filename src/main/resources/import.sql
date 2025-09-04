-- Jobs
INSERT INTO jobs (job_code, job_title) VALUES ('IT', 'IT Specialist');
INSERT INTO jobs (job_code, job_title) VALUES ('HR', 'HR Specialist');
INSERT INTO jobs (job_code, job_title) VALUES ('PM', 'Project Manager');

-- Grades
INSERT INTO grades (grade_code, min_salary, max_salary) VALUES ('GS-11', 55000, 70000);
INSERT INTO grades (grade_code, min_salary, max_salary) VALUES ('GS-12', 65000, 85000);
INSERT INTO grades (grade_code, min_salary, max_salary) VALUES ('GS-13', 80000, 105000);

-- Employees
INSERT INTO employees (first_name, last_name, email, hire_date, job_id, grade_id, salary) VALUES ('Alice', 'Smith', 'alice.smith@example.com', '2020-01-15', 1, 2, 68000);
INSERT INTO employees (first_name, last_name, email, hire_date, job_id, grade_id, salary) VALUES ('Bob', 'Jones', 'bob.jones@example.com', '2018-06-01', 2, 3, 82000);
INSERT INTO employees (first_name, last_name, email, hire_date, job_id, grade_id, salary) VALUES ('Charlie', 'Brown', 'charlie.brown@example.com', '2021-03-10', 3, 3, 95000);
