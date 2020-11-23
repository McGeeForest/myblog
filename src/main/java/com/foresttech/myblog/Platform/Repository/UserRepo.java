package com.foresttech.myblog.Platform.Repository;

import com.foresttech.myblog.Platform.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    public Optional<User> findByNickname(String nickname);

}
