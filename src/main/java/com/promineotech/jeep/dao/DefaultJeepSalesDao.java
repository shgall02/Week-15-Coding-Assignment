package com.promineotech.jeep.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DefaultJeepSalesDao implements JeepSalesDao {
  
  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  
 public List<Jeep> fetchJeeps(JeepModel model, String trim){
   log.debug("DAO: model={}, trim={}", model, trim);
   
   String sql = ""
       + "Select * "
       + "From models "
       + "Where model_id = :model_id And trim_level = :trim_level";
   
   Map<String, Object> params = new HashMap<>();
   params.put("model_id", model.toString());
   params.put("trim_level", trim);
   
     return namedParameterJdbcTemplate.query(sql, params, 
         new RowMapper<>() {
         @Override
         public Jeep mapRow(ResultSet rs, int rowNum) throws SQLException  {
           return Jeep.builder()
               .basePrice(new BigDecimal(rs.getString("base_price")))
               .modelId(JeepModel.valueOf(rs.getString("model_id")))
               .modelPK(rs.getLong("model_pk"))
               .numDoors(rs.getInt("num_doors"))
               .trimLevel(rs.getString("trim_level"))
               .wheelSize(rs.getInt("wheel_size"))
               .build();
         }
     } );
 }
}