package app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.SerieATeam;

@RepositoryRestResource
public interface SerieATeamRepo extends JpaRepository<SerieATeam, Long> {

	

}
