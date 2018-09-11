package com.server.project.wms4;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 6/6/18
 * Time: 9:57 AM
 */

public class OdoCommand implements Serializable {
    private static final long serialVersionUID = 5523630809017120661L;

    @JSONField
    private String odoCode;

    @JSONField
    private String extCode;

    @JSONField
    private String ecOrderCode;

    @JSONField
    private Long customerId;

    @JSONField
    private Long storeId;

    @JSONField
    private String odoType;

    @JSONField
    private String originalOdoCode;

    @JSONField
    private Integer priorityLevel;

    @JSONField
    private Boolean isWholeOrderOutbound;

    @JSONField
    private String crossDockingSymbol;

    @JSONField
    private String orderType;

    @JSONField
    private Date orderTime;

    @JSONField
    private String odoStatus;

    @JSONField
    private Double qty;

    @JSONField
    private Double currentQty;

    @JSONField
    private Double actualQty;

    @JSONField
    private Double cancelQty;

    @JSONField
    private Integer skuNumberOfPackages;

    @JSONField
    private Double amt;

    @JSONField
    private String distributeMode;

    @JSONField
    private String epistaticSystemsOrderType;

    @JSONField
    private Long outboundCartonType;

    @JSONField
    private Boolean includeHazardousCargo;

    @JSONField
    private Boolean includeFragileCargo;

    @JSONField
    private Boolean isLocked;

    @JSONField
    private Long ouId;

    @JSONField
    private Date createTime;

    @JSONField
    private Long createdId;

    @JSONField
    private Date lastModifyTime;

    @JSONField
    private Long modifiedId;

    @JSONField
    private String groupOdoCode;

    @JSONField
    private Boolean isAssignSuccess;

    @JSONField
    private String assignFailReason;

    @JSONField
    private Boolean isAllowMerge;

    @JSONField
    private String waveCode;

    @JSONField
    private String counterCode;

    @JSONField
    private String distributionCode;

    @JSONField
    private String dataSource;

    @JSONField
    private Boolean isFreightInvoiceSunder;

    @JSONField
    private String lagOdoStatus;

    @JSONField
    private Boolean isPermitOutBound = true;

    @JSONField
    private String odoIndex;

    @JSONField
    private String sysDate;

    @JSONField
    private Date archivTime;

    @JSONField
    private String extOdoType;

    @JSONField
    private Boolean isCancel;

    @JSONField
    private Date pickTime;

    @JSONField
    private Date outboundTime;

    @JSONField
    private Boolean isWhVasOrder;

    @JSONField
    private String extProps;

    @JSONField
    private Double currentActualQty;

    @JSONField
    private String partOutboundStrategy;

    @JSONField
    private Integer cartonQty;

    @JSONField
    private String invChangeNotifyIm;

    @JSONField
    private String waveBatch;

    @JSONField
    private String createWaveFlag;

    @JSONField
    private String message;

    @JSONField
    private List<WhOdoLineCommand> whOdoLineCommands;


    public String getWaveBatch() {
        return this.waveBatch;
    }

    public void setWaveBatch(String waveBatch) {
        this.waveBatch = waveBatch;
    }

    public String getCreateWaveFlag() {
        return this.createWaveFlag;
    }

    public void setCreateWaveFlag(String createWaveFlag) {
        this.createWaveFlag = createWaveFlag;
    }

    public String getInvChangeNotifyIm() {
        return this.invChangeNotifyIm;
    }

    public void setInvChangeNotifyIm(String invChangeNotifyIm) {
        this.invChangeNotifyIm = invChangeNotifyIm;
    }

    public String getOdoCode() {
        return this.odoCode;
    }

    public void setOdoCode(String odoCode) {
        this.odoCode = odoCode;
    }

    public String getExtCode() {
        return this.extCode;
    }

    public void setExtCode(String extCode) {
        this.extCode = extCode;
    }

    public String getEcOrderCode() {
        return this.ecOrderCode;
    }

    public void setEcOrderCode(String ecOrderCode) {
        this.ecOrderCode = ecOrderCode;
    }

    public Long getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getStoreId() {
        return this.storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getOdoType() {
        return this.odoType;
    }

    public void setOdoType(String odoType) {
        this.odoType = odoType;
    }

    public String getOriginalOdoCode() {
        return this.originalOdoCode;
    }

    public void setOriginalOdoCode(String originalOdoCode) {
        this.originalOdoCode = originalOdoCode;
    }

    public Integer getPriorityLevel() {
        return this.priorityLevel;
    }

    public void setPriorityLevel(Integer priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public Boolean getIsWholeOrderOutbound() {
        return this.isWholeOrderOutbound;
    }

    public void setIsWholeOrderOutbound(Boolean isWholeOrderOutbound) {
        this.isWholeOrderOutbound = isWholeOrderOutbound;
    }

    public String getCrossDockingSymbol() {
        return this.crossDockingSymbol;
    }

    public void setCrossDockingSymbol(String crossDockingSymbol) {
        this.crossDockingSymbol = crossDockingSymbol;
    }

    public String getOrderType() {
        return this.orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Date getOrderTime() {
        return this.orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getOdoStatus() {
        return this.odoStatus;
    }

    public void setOdoStatus(String odoStatus) {
        this.odoStatus = odoStatus;
    }

    public Double getQty() {
        return this.qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getCurrentQty() {
        return this.currentQty;
    }

    public void setCurrentQty(Double currentQty) {
        this.currentQty = currentQty;
    }

    public Double getActualQty() {
        return this.actualQty;
    }

    public void setActualQty(Double actualQty) {
        this.actualQty = actualQty;
    }

    public Double getCancelQty() {
        return this.cancelQty;
    }

    public void setCancelQty(Double cancelQty) {
        this.cancelQty = cancelQty;
    }

    public Integer getSkuNumberOfPackages() {
        return this.skuNumberOfPackages;
    }

    public void setSkuNumberOfPackages(Integer skuNumberOfPackages) {
        this.skuNumberOfPackages = skuNumberOfPackages;
    }

    public Double getAmt() {
        return this.amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public String getDistributeMode() {
        return this.distributeMode;
    }

    public void setDistributeMode(String distributeMode) {
        this.distributeMode = distributeMode;
    }

    public String getEpistaticSystemsOrderType() {
        return this.epistaticSystemsOrderType;
    }

    public void setEpistaticSystemsOrderType(String epistaticSystemsOrderType) {
        this.epistaticSystemsOrderType = epistaticSystemsOrderType;
    }

    public Long getOutboundCartonType() {
        return this.outboundCartonType;
    }

    public void setOutboundCartonType(Long outboundCartonType) {
        this.outboundCartonType = outboundCartonType;
    }

    public Boolean getIncludeHazardousCargo() {
        return this.includeHazardousCargo;
    }

    public void setIncludeHazardousCargo(Boolean includeHazardousCargo) {
        this.includeHazardousCargo = includeHazardousCargo;
    }

    public Boolean getIncludeFragileCargo() {
        return this.includeFragileCargo;
    }

    public void setIncludeFragileCargo(Boolean includeFragileCargo) {
        this.includeFragileCargo = includeFragileCargo;
    }

    public Boolean getIsLocked() {
        return this.isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Long getOuId() {
        return this.ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreatedId() {
        return this.createdId;
    }

    public void setCreatedId(Long createdId) {
        this.createdId = createdId;
    }

    public Date getLastModifyTime() {
        return this.lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Long getModifiedId() {
        return this.modifiedId;
    }

    public void setModifiedId(Long modifiedId) {
        this.modifiedId = modifiedId;
    }

    public String getGroupOdoCode() {
        return this.groupOdoCode;
    }

    public void setGroupOdoCode(String groupOdoCode) {
        this.groupOdoCode = groupOdoCode;
    }

    public Boolean getIsAssignSuccess() {
        return this.isAssignSuccess;
    }

    public void setIsAssignSuccess(Boolean isAssignSuccess) {
        this.isAssignSuccess = isAssignSuccess;
    }

    public String getAssignFailReason() {
        return this.assignFailReason;
    }

    public void setAssignFailReason(String assignFailReason) {
        this.assignFailReason = assignFailReason;
    }

    public Boolean getIsAllowMerge() {
        return this.isAllowMerge;
    }

    public void setIsAllowMerge(Boolean isAllowMerge) {
        this.isAllowMerge = isAllowMerge;
    }

    public String getWaveCode() {
        return this.waveCode;
    }

    public void setWaveCode(String waveCode) {
        this.waveCode = waveCode;
    }

    public String getCounterCode() {
        return this.counterCode;
    }

    public void setCounterCode(String counterCode) {
        this.counterCode = counterCode;
    }

    public String getDistributionCode() {
        return this.distributionCode;
    }

    public void setDistributionCode(String distributionCode) {
        this.distributionCode = distributionCode;
    }

    public String getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public Boolean getIsFreightInvoiceSunder() {
        return this.isFreightInvoiceSunder;
    }

    public void setIsFreightInvoiceSunder(Boolean isFreightInvoiceSunder) {
        this.isFreightInvoiceSunder = isFreightInvoiceSunder;
    }

    public String getLagOdoStatus() {
        return this.lagOdoStatus;
    }

    public void setLagOdoStatus(String lagOdoStatus) {
        this.lagOdoStatus = lagOdoStatus;
    }

    public Boolean getIsPermitOutBound() {
        return this.isPermitOutBound;
    }

    public void setIsPermitOutBound(Boolean isPermitOutBound) {
        this.isPermitOutBound = isPermitOutBound;
    }

    public String getOdoIndex() {
        return this.odoIndex;
    }

    public void setOdoIndex(String odoIndex) {
        this.odoIndex = odoIndex;
    }

    public String getSysDate() {
        return this.sysDate;
    }

    public void setSysDate(String sysDate) {
        this.sysDate = sysDate;
    }

    public Date getArchivTime() {
        return this.archivTime;
    }

    public void setArchivTime(Date archivTime) {
        this.archivTime = archivTime;
    }

    public String getExtOdoType() {
        return this.extOdoType;
    }

    public void setExtOdoType(String extOdoType) {
        this.extOdoType = extOdoType;
    }

    public Boolean getIsCancel() {
        return this.isCancel;
    }

    public void setIsCancel(Boolean isCancel) {
        this.isCancel = isCancel;
    }

    public Date getPickTime() {
        return this.pickTime;
    }

    public void setPickTime(Date pickTime) {
        this.pickTime = pickTime;
    }

    public Date getOutboundTime() {
        return this.outboundTime;
    }

    public void setOutboundTime(Date outboundTime) {
        this.outboundTime = outboundTime;
    }

    public Boolean getIsWhVasOrder() {
        return this.isWhVasOrder;
    }

    public void setIsWhVasOrder(Boolean isWhVasOrder) {
        this.isWhVasOrder = isWhVasOrder;
    }

    public String getExtProps() {
        return this.extProps;
    }

    public void setExtProps(String extProps) {
        this.extProps = extProps;
    }

    public Double getCurrentActualQty() {
        return this.currentActualQty;
    }

    public void setCurrentActualQty(Double currentActualQty) {
        this.currentActualQty = currentActualQty;
    }

    public String getPartOutboundStrategy() {
        return this.partOutboundStrategy;
    }

    public void setPartOutboundStrategy(String partOutboundStrategy) {
        this.partOutboundStrategy = partOutboundStrategy;
    }

    public Integer getCartonQty() {
        return this.cartonQty;
    }

    public void setCartonQty(Integer cartonQty) {
        this.cartonQty = cartonQty;
    }

    public List<WhOdoLineCommand> getWhOdoLineCommands() {
        return whOdoLineCommands;
    }

    public void setWhOdoLineCommands(List<WhOdoLineCommand> whOdoLineCommands) {
        this.whOdoLineCommands = whOdoLineCommands;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
