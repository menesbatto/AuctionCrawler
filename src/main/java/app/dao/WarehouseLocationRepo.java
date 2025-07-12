package app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.dao.entity.Auction;
import app.dao.entity.WareHouseLocation;

@RepositoryRestResource
public interface WarehouseLocationRepo extends JpaRepository<WareHouseLocation, Long> {


}
