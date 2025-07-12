package app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.AuctionEvent;

@RepositoryRestResource
public interface AuctionEventRepo extends JpaRepository<AuctionEvent, Long> {


}
