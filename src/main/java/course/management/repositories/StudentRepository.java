package course.management.repositories;

import course.management.entities.SelectedCourse;
import course.management.entities.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
    boolean existsByStudentNumberAndNationalId(Long studentNumber, String nationalId);

    boolean existsByNationalId(String nationalId);

    boolean existsByStudentNumber(Long studentNumber);

    @Query("SELECT sc FROM SelectedCourse sc WHERE sc.student.id = :studentId")
    List<SelectedCourse> getAllSelectedCourseByStudentId(@Param("studentId") Long id);

    Optional<Student> findByStudentNumber(Long studentNumber);
}
