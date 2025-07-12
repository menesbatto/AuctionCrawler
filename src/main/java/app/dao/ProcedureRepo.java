package app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.Proceeding;

@RepositoryRestResource
public interface ProcedureRepo extends JpaRepository<Proceeding, Long> {


}
