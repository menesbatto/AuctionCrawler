package app.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import app.dao.entity.SerieATeam;

@Service
@EnableCaching
public class SerieATeamDao {

	@Autowired
	private SerieATeamRepo serieATeamRepo;


	@Cacheable("serieateams")
	public Map<String, String> findAllTeamIds() {
		List<SerieATeam> list = serieATeamRepo.findAll();
		
		Map<String, String> map = new HashMap<String, String>();
		for (SerieATeam pair : list) {
			map.put(pair.getName(), pair.getSiteId());
		}
		return map;
	}


	public void saveTeamIds(Map<String, String> map) {
		List<SerieATeam> list = new ArrayList<SerieATeam>();
		for (String key : map.keySet()) {
			SerieATeam t =  new SerieATeam();
			t.setSiteId(map.get(key));
			t.setName(key);
			list.add(t);
		}
		serieATeamRepo.save(list);
		
	}

	
	
	
}
