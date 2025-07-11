package app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.Vote;

@RepositoryRestResource
public interface VoteRepo extends JpaRepository<Vote, Long> {

	Vote findById(Long id);
	
	@Query("SELECT DISTINCT v.serieASeasonDay FROM Vote v")
	List<Integer> findAllDistinct();

	
	@Query("SELECT DISTINCT v.team FROM Vote v")
	List<String> findAllSerieATeam();

}
