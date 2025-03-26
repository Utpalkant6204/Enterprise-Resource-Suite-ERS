package com.example.Enterprise.Resource.Suite.ERS.Repositories;

import com.example.Enterprise.Resource.Suite.ERS.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = """
        SELECT * FROM employee e\s
        WHERE (:employeeId IS NULL OR e.employee_id = :employeeId)
        AND (:searchText IS NULL OR CONCAT(e.first_name, ' ', e.last_name) LIKE %:searchText%)
        LIMIT :limit OFFSET :offset
    """, nativeQuery = true)
    List<Employee> searchEmployee(
            @Param("employeeId") Long employeeId,
            @Param("searchText") String searchText,
//            @Param("role") String role,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    Employee findByEmail(String username);
}
