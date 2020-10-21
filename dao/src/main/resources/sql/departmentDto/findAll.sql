SELECT d.id as id, d.name as name ,
  avg(e.salary) as average_salary FROM department d LEFT JOIN employee e
  ON e.department_id = d.id
  GROUP BY d.id, d.name
  ORDER BY d.id;