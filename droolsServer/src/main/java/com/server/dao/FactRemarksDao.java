package com.server.dao;


import com.server.model.FactRemarks;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/21/18
 * Time: 10:36 AM
 */

@Mapper
public interface FactRemarksDao {

    void saveFactRemarks(FactRemarks factRemarks);

    List<FactRemarks> findFactRemarksByParameter(FactRemarks factRemarks);

    Long deleteFactRemarksByParameter(FactRemarks factRemarks);

    Long updateFactRemarksByParameter(FactRemarks factRemarks);

}
