package com.garbage.repository;

// import com.garbage.model.Public;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;

// public interface PublicRepository extends JpaRepository<Public, Long> {
//     @Query("SELECT p FROM Public p WHERE p.email = :email AND p.password = :password")
//     Public findByEmailIdAndPassword(@Param("email") String email, @Param("password") String password);
// }

import com.garbage.model.Public;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PublicRepository extends JpaRepository<Public, Integer> {
    Optional<Public> findByemailidAndPassword(String emailid, String password);
    Optional<Public> findByPublicId(Integer publicId);
}
