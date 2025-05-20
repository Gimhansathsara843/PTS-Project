package com.it.ceb.pts.repo;

import java.util.HashMap;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.it.ceb.pts.domain.Province;

@Repository
@Transactional
public class ProvinceDaoImpl implements ProvinceDao {
	
	@Autowired
    private EntityManager em;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, String> getProvinceListToLicense(String licenceCode) throws Exception
	{
		String qryStr = "SELECT s FROM Province s where s.licenseCode =:licenseCode";
		Query query = em.createQuery(qryStr);
		query.setParameter("licenseCode", licenceCode);
		List<Province> provinceList = query.getResultList();
		HashMap<String, String> provinceMap = new HashMap<String, String>();
	    for (Province province : provinceList) {
	    	provinceMap.put(province.getProvinceCode(), province.getProvinceName());
	    }
		return provinceMap;
		
	}


	@Override
	public List<Province> getProvinceByDivition(String licenceCode) {
		String qryStr = "SELECT s FROM Province s where s.licenseCode =:licenseCode";
		Query query = em.createQuery(qryStr);
		query.setParameter("licenseCode", licenceCode);
		List<Province> provinceList = query.getResultList();
		return provinceList;
	}


	@Override
	public List<Province> getAllProvince() {
		String qryStr = "SELECT s FROM Province s";
		Query query = em.createQuery(qryStr);
		List<Province> provinceList = query.getResultList();
		return provinceList;
	}

}
