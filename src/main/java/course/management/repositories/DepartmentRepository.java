package course.management.repositories;

import course.management.entities.Department;
import course.management.entities.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends PagingAndSortingRepository<Department, Long> {

    @Query("SELECT d.students FROM Department d WHERE d.id = :id")
    List<Student> findAllStudentById(@Param("id") Long id);

    boolean existsByName(String name);
}
