package course.management.helper;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetailsImpl extends User {

    private Long id;
    private String nationalId;
    private Long departmentId;

    public CustomUserDetailsImpl(String username, String password, Collection<? extends GrantedAuthority> authorities, Long id, String nationalId, Long departmentId) {
        super(username, password, authorities);
        this.id = id;
        this.nationalId = nationalId;
        this.departmentId = departmentId;
    }

    public Long getId() {
        return id;
    }

    public String getNationalId() {
        return nationalId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }
}
