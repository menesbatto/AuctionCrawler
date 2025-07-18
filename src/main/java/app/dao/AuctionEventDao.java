package app.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import app.dao.entity.Auction;
import app.dao.entity.AuctionEvent;
import app.dao.entity.Proceeding;
import app.dao.entity.WareHouseLocation;
import app.logic._0_votesDownloader.model.AuctionDTO;
import app.logic._0_votesDownloader.model.AuctionEventDTO;
import app.logic._0_votesDownloader.model.ProcessStatusEnum;
import app.logic._0_votesDownloader.model.WareHouseLocationDTO;

@Service
@EnableCaching
public class AuctionEventDao {

	@Autowired
	private AuctionEventRepo auctionEventRepo;
	@Autowired
	private AuctionRepo auctionRepo;
	@Autowired
	private ProceedingDao proceedingDao;
	@Autowired
	private WarehouseLocationDao warehouseLocationDao;


	public void saveAuctionEvent(AuctionEventDTO dto) {
		AuctionEvent ent = null;
		
		if (dto.getDetailPageUrl()!= null) {
			ent = auctionEventRepo.findByDetailPageUrl(dto.getDetailPageUrl());

			if (ent == null)
				ent = new AuctionEvent();
			
			ent = populateVote(dto, ent);
		}
		else {
			System.out.println("errore dto.getDetailPageUrl() == NULL");
		}
		
		
//		if (dto.getDetailPageUrl()!= null) {
//			ent = auctionEventRepo.findByDetailPageUrl(dto.getDetailPageUrl());
//
//			if (ent == null)
//				ent = populateVote(dto);
//			else {
//				ent.getAuction().setDescription(dto.getAuction().getDescription());
//				ent.setAuctionPageUrl(dto.getAuctionPageUrl());
//				ent.setProcessState(dto.getProcessStatus().getCode());
//				ent
//			//// DA POPOLARE AUCTION EVENT
//			}
//		}
//		else {
//			ent = populateVote(dto);
//		}
		try {
			auctionEventRepo.save(ent);
		}
		catch (Exception e) {
//			 e.printStackTrace();
			System.out.println(ent.getAuction().getDescription());
			System.out.println();
		}
	}
	
	public void saveAuctionEvents(List<AuctionEventDTO> dtoList) {
		
		
		AuctionEvent ent = null;
//		List<AuctionEvent> entList = new ArrayList<>();
		for (AuctionEventDTO dto : dtoList) {
			try {
				ent = auctionEventRepo.findByDetailPageUrl(dto.getDetailPageUrl());
			}
			catch(Exception e) {
				System.out.println(e);
			}
			if (ent==null) 
				ent = new AuctionEvent();
			//CONTROLLA
			if (ProcessStatusEnum.DETAIL_INFO_DOWNLOADED.getCode().equals(ent.getProcessState()))
				dto.setProcessStatus(ProcessStatusEnum.DETAIL_INFO_DOWNLOADED);
			
			ent = populateVote(dto, ent);
			auctionEventRepo.save(ent);
			
//			else {
//				ent.getAuction().setDescription(dto.getAuction().getDescription());
//				
//				ent.setProcessState(dto.getProcessStatus().getCode());
//			//// DA POPOLARE AUCTION EVENT
//			}
//		}
//		try {
//			if (!entList.isEmpty())
//				auctionEventRepo.save(entList);
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			System.out.println(ent);
		}
	}

	private AuctionEvent populateVote(AuctionEventDTO dto, AuctionEvent ent) {
//		 ent = null;
//		AuctionEvent ents = auctionEventRepo.findByDetailPageUrl(dto.getDetailPageUrl());
//		if (ents==null) {
		// 	AUCTION EVENT

			// 	AUCTION EVENT	AUCTION
			AuctionDTO auctionDTO = dto.getAuction();
			Auction auction = null;
			try {
				auction = auctionRepo.findByTitle(auctionDTO.getTitle());
			}
			catch (Exception e) {
				System.out.println(e);
			}
			if (auction == null )	{					// Se è il primo AuctionEvent che metto dalla home e quindi ancora non c'è l'auction
				if (ent.getDetailPageUrl() != null){	// Se a DB l'autctionEvent è presente. A DB è sempre presente
					System.out.println("Errore la pagina del dettaglio non funziona piu'");
					ent.setProcessState(ProcessStatusEnum.BLOCKED_NO_DETAIL_PAGE.getCode());
					return ent;
				}
			}
			if (auction==null) {
				auction = new Auction();
			}
			if (auctionDTO.getCategoryMacro()!= null)
				auction.setCategory(auctionDTO.getCategoryMacro().getDescription());
			if (auctionDTO.getCategoryC0()!= null)
				auction.setCategoryC0(auctionDTO.getCategoryC0().getDescription());
			if (auctionDTO.getCategoryC1()!= null)
				auction.setCategoryC1(auctionDTO.getCategoryC1().getDescription());
			if (auctionDTO.getCourt()!= null) 
				auction.setCourt(auctionDTO.getCourt().getDescription());
			if (auctionDTO.getIdIVG()!= null) 
				auction.setIdIVG(auctionDTO.getIdIVG().getDescription());
			auction.setLotCode(auctionDTO.getLotCode());
			auction.setTitle(auctionDTO.getTitle());
			auction.setDescription(auctionDTO.getDescription());
		
			// 	AUCTION EVENT	AUCTION		PROCEEDING
			if (auctionDTO.getProceeding()!= null) {
				String description = auctionDTO.getProceeding().getDescription();
				String number = auctionDTO.getProceeding().getNumber();
				String year = auctionDTO.getProceeding().getYear();
				try {
					Proceeding proceeding = proceedingDao.retrieveByNumberAndYear(number, year);
					
					
					if (proceeding == null) {
						proceeding= new Proceeding();
						proceeding.setDescription(description);
						proceeding.setNumber(number);
						proceeding.setYear(year);
					}		
					auction.setProceeding(proceeding);
				}
				catch (Exception e) {
					System.out.println();
				}
			}
			
			if (auctionDTO.getSellType()!=null)
				auction.setSellType(auctionDTO.getSellType().getDescription());
	
			// 	AUCTION EVENT	AUCTION		WAREHOUSE LOCATION
			WareHouseLocationDTO locationDTO = auctionDTO.getWarehouseLocation();
			if (locationDTO != null) {
				String street = locationDTO.getStreet();
				String city = locationDTO.getCity();
				WareHouseLocation location = warehouseLocationDao.retrieveByCity(city);
				if (location == null) {
					location = new WareHouseLocation();
					location.setCap(locationDTO.getCap());
					location.setCity(city);
					location.setLat(locationDTO.getLat());
					location.setLon(locationDTO.getLon());
					location.setNumber(locationDTO.getNumber());
					location.setProvince(locationDTO.getProvince());
					location.setStreet(street);
				}
				auction.setWarehouseLocation(location);
			}
		
			ent.setAuction(auction);
			
			
			// AUCTION EVENT ALTRO
			ent.setCurrentPrice(dto.getCurrentPrice());
			ent.setCurrentPriceWithTaxes(dto.getCurrentPriceWithTaxes());
			ent.setEndPrice(dto.getEndPrice());
			ent.setEndPriceWithTaxes(dto.getEndPriceWithTaxes());
			ent.setIdPVP(dto.getIdPVP());
			ent.setSellCode(dto.getSellCode());
			ent.setSellEndDate(dto.getSellEndDate());
			ent.setSellStartDate(dto.getSellStartDate());
			if (dto.getSellState()!= null)
				ent.setSellState(dto.getSellState().getDescription());
			ent.setStartPrice(dto.getStartPrice());
			ent.setStartPriceWithTaxes(dto.getStartPriceWithTaxes());
			
			ent.setDetailPageUrl(dto.getDetailPageUrl());
			ent.setAuctionPageUrl(dto.getAuctionPageUrl());
			
			ent.setProcessState(dto.getProcessStatus().getCode());
		
		
		
		
		return ent;
	}

	public List<AuctionEventDTO> retrieveAuctionEvents(ProcessStatusEnum processState) {
		List<AuctionEvent> ents = auctionEventRepo.findByProcessState(processState.getCode());
		List<AuctionEventDTO> dtoList = new ArrayList<>();
		for (AuctionEvent ent : ents) {
			AuctionEventDTO dto = new AuctionEventDTO();
			// CREA DTO DA ENTITY
			dto.setDetailPageUrl(ent.getDetailPageUrl());
			dtoList.add(dto);
		}
		
		return dtoList;
	}


//
//	public void saveGazzettaCredentials(Credentials credentials) {
//		String username = "mene"; //TROVA LO USER DALLA SESSION
//		
//		User user = userRepo.findByUsername(username);
//		
//		user.setGazzettaPassword(credentials.getPassword());
//		user.setGazzettaUsername(credentials.getUsername());
//		
//		userRepo.save(user);
//		
//	}
//
//
//	public void createUser(UserBean userBean) {
//		User ent = new User();
//		ent.setFirstname(userBean.getFirstname());
//		ent.setLastname(userBean.getLastname());
//		ent.setEmail(userBean.getEmail());
//		ent.setUsername(userBean.getUsername());
//		ent.setPassword(userBean.getPassword());
//		Integer rnd = userBean.hashCode();
//		ent.setToBeConfirm(rnd);
//		
//		userRepo.save(ent);
//		
//	}
//	
//	
//	public Boolean confirmUser(ConfirmUser confirmUser) {
//		String username = confirmUser.getUsername();
//		
//		User user = userRepo.findByUsername(username);
//
//		if (user.getToBeConfirm().toString().equals(confirmUser.getRnd())) {
//			user.setToBeConfirm(0);
//			userRepo.save(user);
//			return true;
//		}
//		else {
//			return false;
//		}
//		
//	}
//
//
//	public UserBean login(Credentials credentials) {
//		User dbUser = userRepo.findByUsername(credentials.getUsername());
//		if (credentials.getPassword().equals(dbUser.getPassword())) {
//			UserBean userBean = createUserBean(dbUser);
//			return userBean;
//		}
//		return null;
//	}
//
//
//	private UserBean createUserBean(User ent) {
//		UserBean userBean = new UserBean();
//		userBean.setFirstname(ent.getFirstname());
//		userBean.setLastname(ent.getLastname());
//		userBean.setEmail(ent.getEmail());
//		userBean.setUsername(ent.getUsername());
//		userBean.setPassword(ent.getPassword());
//		return userBean;
//		
//	}
//	
//	

	
	
}
