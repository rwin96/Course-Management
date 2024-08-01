package course.management.repositories;

import course.management.entities.ProvidedCourse;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvidedCourseRepository extends PagingAndSortingRepository<ProvidedCourse, Long> {
}
