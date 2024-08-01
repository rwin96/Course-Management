package course.management.repositories;

import course.management.entities.Teacher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends PagingAndSortingRepository<Teacher, Long> {
    @Override
    List<Teacher> findAll();

    boolean existsByPersonalIdAndNationalId(Long personalId, String nationalId);

    boolean existsByPersonalId(Long personalId);

    boolean existsByNationalId(String nationalId);

    @Query("SELECT DISTINCT sc.student.id FROM SelectedCourse sc WHERE sc.providedCourse.id = :providedCourseId")
    List<Long> getAllStudentsIdsByProvidedCourseId(Long providedCourseId);

    @Query("SELECT DISTINCT pc.id FROM ProvidedCourse pc WHERE pc.teacher.id = :teacherId")
    List<Long> getAllProvidedCoursesIdByTeacherId(Long teacherId);

    Optional<Teacher> findByPersonalId(Long PersonalId);
}
