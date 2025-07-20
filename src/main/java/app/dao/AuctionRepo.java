package app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.Auction;
import app.dao.entity.Proceeding;

@RepositoryRestResource
public interface AuctionRepo extends JpaRepository<Auction, Long> {

//	Auction findByTitle(String description);

	Auction findByProceeding(Proceeding proceeding);

	Auction findByProceedingAndLotCode(Proceeding proceeding, String lotCode);

	Auction findByProceedingAndLotCodeAndTitle(Proceeding proceeding, String lotCode, String title);


}
