package app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.AuctionEvent;

@RepositoryRestResource
public interface AuctionEventRepo extends JpaRepository<AuctionEvent, Long> {

	AuctionEvent findByDetailPageUrl(String urlDetailPage);

	List<AuctionEvent> findByProcessState(String string);
	
	@Query(value = "SELECT * " +
		    "FROM auctionEvent ae " +
		    "JOIN auction a ON ae.auction_id = a.id " +
		    "JOIN ( " +
		    "    SELECT ae_inner.auction_id, MIN(ae_inner.startPrice) AS min_prezzo, MAX(ae_inner.startPrice) AS max_prezzo " +
		    "    FROM auctionEvent ae_inner " +
		    "    JOIN auction a_inner ON ae_inner.auction_id = a_inner.id " +
		    "    GROUP BY ae_inner.auction_id " +
		    "    HAVING COUNT(*) >= 2 " +
		    "       AND MAX(ae_inner.startPrice) >= MIN(ae_inner.startPrice) * 3 " +
		    "       AND MAX(ae_inner.startPrice) > :minValue " +
		    "       AND MAX(ae_inner.startPrice) <= :maxValue " +
		    ") agg ON ae.auction_id = agg.auction_id " +
		    "     AND (ae.startPrice = agg.min_prezzo OR ae.startPrice = agg.max_prezzo) " +
		    "WHERE ae.sellEndDate > CURRENT_DATE", 
		    nativeQuery = true)
		List<AuctionEvent> findExtremeAuctionEventsWithRange(@Param("minValue") int minValue, 
		                                                     @Param("maxValue") int maxValue);
	
	@Query(value = "SELECT ae.* " +
             "FROM auctionEvent ae " +
             "JOIN auction a ON ae.auction_id = a.id " +
             "JOIN ( " +
             "    SELECT ae_inner.auction_id, MIN(ae_inner.startPrice) AS min_prezzo, MAX(ae_inner.startPrice) AS max_prezzo " +
             "    FROM auctionEvent ae_inner " +
             "    JOIN auction a_inner ON ae_inner.auction_id = a_inner.id " +
             "    WHERE a_inner.idIVG = :idIvg " +
             "    GROUP BY ae_inner.auction_id " +
             "    HAVING COUNT(*) >= 2 " +
             "       AND MAX(ae_inner.startPrice) >= MIN(ae_inner.startPrice) * 3 " +
             ") agg ON ae.auction_id = agg.auction_id " +
             "     AND (ae.startPrice = agg.min_prezzo OR ae.startPrice = agg.max_prezzo) " +
             "WHERE a.idIVG = :idIvg", nativeQuery = true)
	 List<AuctionEvent> findExtremeAuctionEvents(@Param("idIvg") String idIvg);
//	@Query(value = "SELECT ae.* " +
//			"FROM auctionEvent ae " +
//			"JOIN auction a ON ae.auction_id = a.id " +
//			"JOIN ( " +
//			"    SELECT ae_inner.auction_id, MIN(ae_inner.startPrice) AS min_prezzo, MAX(ae_inner.startPrice) AS max_prezzo " +
//			"    FROM auctionEvent ae_inner " +
//			"    JOIN auction a_inner ON ae_inner.auction_id = a_inner.id " +
//			"    WHERE a_inner.idIVG = :idIvg " +
//			"    GROUP BY ae_inner.auction_id " +
//			"    HAVING COUNT(*) >= 2 " +
//			"       AND MAX(ae_inner.startPrice) >= MIN(ae_inner.startPrice) * 3 " +
//			") agg ON ae.auction_id = agg.auction_id " +
//			"     AND (ae.startPrice = agg.min_prezzo OR ae.startPrice = agg.max_prezzo) " +
//			"WHERE a.idIVG = :idIvg", nativeQuery = true)
//	List<AuctionEvent> findExtremeAuctionEvents(@Param("idIvg") String idIvg);
		 
		 
		 
		 
		 


}
