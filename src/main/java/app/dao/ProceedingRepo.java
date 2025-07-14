package app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.Proceeding;

@RepositoryRestResource
public interface ProceedingRepo extends JpaRepository<Proceeding, Long> {

//	Proceeding findByDescription(String description);

	Proceeding findByNumberAndYear(String number, String year);


}
