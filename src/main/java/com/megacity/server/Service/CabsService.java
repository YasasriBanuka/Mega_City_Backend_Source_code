package com.megacity.server.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.megacity.server.Model.Cabs;
import java.util.Map;

@Service
public interface CabsService {
    
    Cabs addCab(Cabs cab);
    Cabs updateCab(String cabId, Cabs cab);
    void deleteCab(String cabId);
    Cabs getCabById(String cabId);
    List<Map<String, Object>> getAllCabs();
    List<Cabs> getcabsById(String categoryId);

}
