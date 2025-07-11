package app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.User;

@RepositoryRestResource
public interface UserRepo extends JpaRepository<User, Long> {

	User findByUsername(String username);
	
	


}
