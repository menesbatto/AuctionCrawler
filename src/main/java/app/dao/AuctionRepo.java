package app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.Auction;

@RepositoryRestResource
public interface AuctionRepo extends JpaRepository<Auction, Long> {


}
