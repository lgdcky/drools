package com.server.project.wms4;

import com.server.tools.EntityAttributeInfo;
import org.kie.api.command.Command;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 6/6/18
 * Time: 9:58 AM
 */
@EntityAttributeInfo.ClassAnnotation(name = "WhOdoLineCommand", desc = "测试出库单明细模板")
public class WhOdoLineCommand implements Command<WhOdoLineCommand> {
    private static final long serialVersionUID = -2071411398425434080L;
    private Long odoId;
    private Integer linenum;
    private Integer originalLinenum;
    private Long storeId;
    private Integer extLinenum;
    private Long skuId;
    private String skuBarCode;
    private String skuName;
    private String extSkuName;
    private Double qty;
    private Double planQty;
    private Double currentQty;
    private Double actualQty;
    private Double cancelQty;
    private Double assignQty;
    private Double diekingQty;
    private Double linePrice;
    private Double lineTagPrice;
    private Double lineAmt;
    private String odoLineStatus;
    private Boolean isCheck;
    private Boolean fullLineOutbound;
    private String partOutboundStrategy;
    private Date mfgDate;
    private Date expDate;
    private Date minExpDate;
    private Date maxExpDate;
    private String batchNumber;
    private String countryOfOrigin;
    private Long invStatus;
    private String invType;
    private String invAttr1;
    private String invAttr2;
    private String invAttr3;
    private String invAttr4;
    private String invAttr5;
    private Long outboundCartonType;
    private String color;
    private String style;
    private String size;
    private String mixingAttr;
    private String originalOdoCode;
    private String assignFailReason;
    private Boolean isAssignSuccess;
    private Long ouId;
    private Date createTime;
    private Long createdId;
    private Date lastModifyTime;
    private Long modifiedId;
    private String waveCode;
    private Boolean isWhVas;
    private String sysDate;
    private String extProps;

    public WhOdoLineCommand() {
    }

    public Long getOdoId() {
        return this.odoId;
    }

    public Double getLineTagPrice() {
        return this.lineTagPrice;
    }

    public void setLineTagPrice(Double lineTagPrice) {
        this.lineTagPrice = lineTagPrice;
    }

    public String getWaveCode() {
        return this.waveCode;
    }

    public void setWaveCode(String waveCode) {
        this.waveCode = waveCode;
    }

    public void setOdoId(Long odoId) {
        this.odoId = odoId;
    }

    public Integer getLinenum() {
        return this.linenum;
    }

    public void setLinenum(Integer linenum) {
        this.linenum = linenum;
    }

    public Integer getOriginalLinenum() {
        return this.originalLinenum;
    }

    public void setOriginalLinenum(Integer originalLinenum) {
        this.originalLinenum = originalLinenum;
    }

    public Long getStoreId() {
        return this.storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Integer getExtLinenum() {
        return this.extLinenum;
    }

    public void setExtLinenum(Integer extLinenum) {
        this.extLinenum = extLinenum;
    }

    public Long getSkuId() {
        return this.skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuBarCode() {
        return this.skuBarCode;
    }

    public void setSkuBarCode(String skuBarCode) {
        this.skuBarCode = skuBarCode;
    }

    public String getSkuName() {
        return this.skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getExtSkuName() {
        return this.extSkuName;
    }

    public void setExtSkuName(String extSkuName) {
        this.extSkuName = extSkuName;
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

    public Double getAssignQty() {
        return this.assignQty;
    }

    public void setAssignQty(Double assignQty) {
        this.assignQty = assignQty;
    }

    public Double getDiekingQty() {
        return this.diekingQty;
    }

    public void setDiekingQty(Double diekingQty) {
        this.diekingQty = diekingQty;
    }

    public Double getLinePrice() {
        return this.linePrice;
    }

    public void setLinePrice(Double linePrice) {
        this.linePrice = linePrice;
    }

    public Double getLineAmt() {
        return this.lineAmt;
    }

    public void setLineAmt(Double lineAmt) {
        this.lineAmt = lineAmt;
    }

    public String getOdoLineStatus() {
        return this.odoLineStatus;
    }

    public void setOdoLineStatus(String odoLineStatus) {
        this.odoLineStatus = odoLineStatus;
    }

    public Boolean getIsCheck() {
        return this.isCheck;
    }

    public void setIsCheck(Boolean isCheck) {
        this.isCheck = isCheck;
    }

    public Boolean getFullLineOutbound() {
        return this.fullLineOutbound;
    }

    public void setFullLineOutbound(Boolean fullLineOutbound) {
        this.fullLineOutbound = fullLineOutbound;
    }

    public String getPartOutboundStrategy() {
        return this.partOutboundStrategy;
    }

    public void setPartOutboundStrategy(String partOutboundStrategy) {
        this.partOutboundStrategy = partOutboundStrategy;
    }

    public Date getMfgDate() {
        return this.mfgDate;
    }

    public void setMfgDate(Date mfgDate) {
        this.mfgDate = mfgDate;
    }

    public Date getExpDate() {
        return this.expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public Date getMinExpDate() {
        return this.minExpDate;
    }

    public void setMinExpDate(Date minExpDate) {
        this.minExpDate = minExpDate;
    }

    public Date getMaxExpDate() {
        return this.maxExpDate;
    }

    public void setMaxExpDate(Date maxExpDate) {
        this.maxExpDate = maxExpDate;
    }

    public String getBatchNumber() {
        return this.batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getCountryOfOrigin() {
        return this.countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public Long getInvStatus() {
        return this.invStatus;
    }

    public void setInvStatus(Long invStatus) {
        this.invStatus = invStatus;
    }

    public String getInvType() {
        return this.invType;
    }

    public void setInvType(String invType) {
        this.invType = invType;
    }

    public String getInvAttr1() {
        return this.invAttr1;
    }

    public void setInvAttr1(String invAttr1) {
        this.invAttr1 = invAttr1;
    }

    public String getInvAttr2() {
        return this.invAttr2;
    }

    public void setInvAttr2(String invAttr2) {
        this.invAttr2 = invAttr2;
    }

    public String getInvAttr3() {
        return this.invAttr3;
    }

    public void setInvAttr3(String invAttr3) {
        this.invAttr3 = invAttr3;
    }

    public String getInvAttr4() {
        return this.invAttr4;
    }

    public void setInvAttr4(String invAttr4) {
        this.invAttr4 = invAttr4;
    }

    public String getInvAttr5() {
        return this.invAttr5;
    }

    public void setInvAttr5(String invAttr5) {
        this.invAttr5 = invAttr5;
    }

    public Long getOutboundCartonType() {
        return this.outboundCartonType;
    }

    public void setOutboundCartonType(Long outboundCartonType) {
        this.outboundCartonType = outboundCartonType;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStyle() {
        return this.style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMixingAttr() {
        return this.mixingAttr;
    }

    public void setMixingAttr(String mixingAttr) {
        this.mixingAttr = mixingAttr;
    }

    public String getOriginalOdoCode() {
        return this.originalOdoCode;
    }

    public void setOriginalOdoCode(String originalOdoCode) {
        this.originalOdoCode = originalOdoCode;
    }

    public String getAssignFailReason() {
        return this.assignFailReason;
    }

    public void setAssignFailReason(String assignFailReason) {
        this.assignFailReason = assignFailReason;
    }

    public Boolean getIsAssignSuccess() {
        return this.isAssignSuccess;
    }

    public void setIsAssignSuccess(Boolean isAssignSuccess) {
        this.isAssignSuccess = isAssignSuccess;
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

    public Double getPlanQty() {
        return this.planQty;
    }

    public void setPlanQty(Double planQty) {
        this.planQty = planQty;
    }

    public String getSysDate() {
        return this.sysDate;
    }

    public void setSysDate(String sysDate) {
        this.sysDate = sysDate;
    }

    public Boolean getIsWhVas() {
        return this.isWhVas;
    }

    public void setIsWhVas(Boolean isWhVas) {
        this.isWhVas = isWhVas;
    }

    public String getExtProps() {
        return this.extProps;
    }

    public void setExtProps(String extProps) {
        this.extProps = extProps;
    }
}

