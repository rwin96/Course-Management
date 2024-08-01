package course.management.repositories;

import course.management.entities.Course;
import course.management.entities.Department;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends PagingAndSortingRepository<Course, Long> {

    boolean existsByNameAndDepartment(String name, Department department);

}
