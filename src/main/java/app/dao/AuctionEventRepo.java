package app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.AuctionEvent;

@RepositoryRestResource
public interface AuctionEventRepo extends JpaRepository<AuctionEvent, Long> {

	AuctionEvent findByDetailPageUrl(String urlDetailPage);

	List<AuctionEvent> findByProcessState(String string);


}
