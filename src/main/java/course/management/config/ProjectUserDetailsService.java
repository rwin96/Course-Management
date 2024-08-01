package course.management.config;

import course.management.entities.Admin;
import course.management.entities.Student;
import course.management.entities.Teacher;
import course.management.helper.CustomUserDetailsImpl;
import course.management.repositories.AdminsRepository;
import course.management.repositories.StudentRepository;
import course.management.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectUserDetailsService implements UserDetailsService {

    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;

    private AdminsRepository adminsRepository;

    @Autowired
    public ProjectUserDetailsService(StudentRepository studentRepository, TeacherRepository teacherRepository, AdminsRepository adminsRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.adminsRepository = adminsRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authority;

        Admin admin = adminsRepository.findByUsername(username).orElse(null);
        if (admin != null) {
            authority = List.of(new SimpleGrantedAuthority(admin.getRole()));
            return new CustomUserDetailsImpl(admin.getUsername(), admin.getPassword(), authority, admin.getId(), admin.getNationalId(), 0L);
        }

        Student student = studentRepository.findByStudentNumber(Long.valueOf(username)).orElse(null);
        Teacher teacher = teacherRepository.findByPersonalId(Long.valueOf(username)).orElse(null);
        CustomUserDetailsImpl customUser = null;

        if (student == null && teacher == null) {
            throw new UsernameNotFoundException("User details not found for the user: " + username);
        } else if (student == null) {
            authority = List.of(new SimpleGrantedAuthority(teacher.getRole()));
            customUser = new CustomUserDetailsImpl(String.valueOf(teacher.getPersonalId()), teacher.getPassword(), authority, teacher.getId(), teacher.getNationalId(), teacher.getDepartment().getId());
        } else if (teacher == null) {
            authority = List.of(new SimpleGrantedAuthority(student.getRole()));
            customUser = new CustomUserDetailsImpl(String.valueOf(student.getStudentNumber()), student.getPassword(), authority, student.getId(), student.getNationalId(), student.getDepartment().getId());
        }

        return customUser;
    }

}
