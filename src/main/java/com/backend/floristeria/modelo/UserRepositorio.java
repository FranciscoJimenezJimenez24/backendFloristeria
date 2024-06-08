package com.backend.floristeria.modelo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepositorio extends JpaRepository<User, Integer>{
	Optional<User> findByUsername(String username);
	
	@Modifying()
    @Query("update User u set u.firstname=:firstname, u.lastname=:lastname, u.country=:country, u.role=:role where u.id = :id")
    void updateUser(@Param(value = "id") Integer id,   @Param(value = "firstname") String firstname, @Param(value = "lastname") String lastname , @Param(value = "country") String country, @Param(value = "role") Rol role);


}
