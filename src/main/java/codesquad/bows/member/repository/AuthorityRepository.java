package codesquad.bows.member.repository;

import codesquad.bows.member.entity.Authority;
import codesquad.bows.member.entity.AuthorityName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByName(String name);
}
