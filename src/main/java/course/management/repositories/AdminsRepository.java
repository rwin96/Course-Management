package course.management.repositories;

import course.management.entities.Admin;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminsRepository extends PagingAndSortingRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);
}
