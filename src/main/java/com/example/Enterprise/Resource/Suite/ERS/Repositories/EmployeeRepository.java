package com.example.Enterprise.Resource.Suite.ERS.Repositories;

import com.example.Enterprise.Resource.Suite.ERS.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    @Query(value = """
        SELECT * FROM employee e
        WHERE (:employeeId IS NULL OR e.employee_id = CAST(:employeeId AS UUID))
        AND (:searchText IS NULL OR LOWER(CONCAT(e.first_name, ' ', e.last_name)) ILIKE LOWER(CONCAT('%', :searchText, '%')))
        LIMIT :limit OFFSET :offset
    """, nativeQuery = true)
    List<Employee> searchEmployee(
            @Param("employeeId") UUID employeeId,
            @Param("searchText") String searchText,
            @Param("limit") int limit,
            @Param("offset") int offset
    );


    Employee findByEmail(String username);
}
