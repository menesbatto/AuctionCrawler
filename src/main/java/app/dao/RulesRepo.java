package app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.Rules;
import app.dao.entity.Vote;

@RepositoryRestResource
public interface RulesRepo extends JpaRepository<Rules, Long> {


}
