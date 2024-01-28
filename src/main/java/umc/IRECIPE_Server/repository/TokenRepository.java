package umc.IRECIPE_Server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.IRECIPE_Server.entity.RefreshToken;

public interface TokenRepository extends JpaRepository<RefreshToken, Long> {
}
