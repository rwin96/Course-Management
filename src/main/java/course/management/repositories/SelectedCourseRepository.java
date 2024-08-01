package course.management.repositories;

import course.management.entities.SelectedCourse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SelectedCourseRepository extends PagingAndSortingRepository<SelectedCourse, Long> {

    @Query("SELECT sc.providedCourse.id FROM SelectedCourse sc WHERE sc.student.id = :studentId")
    List<Long> findProvidedCoursesIdsByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT sc FROM SelectedCourse sc WHERE sc.providedCourse.course.department.id = :departmentId")
    Iterable<SelectedCourse> findAllByDepartmentId(Long departmentId);

}
