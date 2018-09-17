package com.baozun.netty.client.command;

import com.baozun.netty.client.tools.TypeConvertTools;

import java.io.IOException;
import java.util.*;

import static com.baozun.netty.client.tools.CompressTool.compresss;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/5/18
 * Time: 7:26 PM
 */
public class RuleCommand<T> {

    //默认以3M大小为分割数组标准,只能保证划分的包在3M左右
    private static final Integer DEFAULTSEGMENTATIO = 3 * 1024 * 1024;

    //规则适用组
    private String group;

    private String type;

    private List<T> factList;

    public void RuleCommand(String group, List<T> factList) {
        this.group = group;
        this.factList = factList;
    }

    //用于将List进行细粒度切分
    private Integer segmentationNum;

    private List<RuleCommand> ruleCommandList;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<T> getFactList() {
        return factList;
    }

    public void setFactList(List<T> factList) {
        this.factList = factList;
    }

    public Integer getSegmentationNum() {
        if (null == this.segmentationNum) {
            return DEFAULTSEGMENTATIO;
        }
        return segmentationNum;
    }

    public void setSegmentationNum(Integer segmentationNum) {
        this.segmentationNum = segmentationNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Map<String, Object[]>> getRuleCommandList() throws Exception {
        if (null == this.factList && this.factList.size() <= 0)
            return null;
        List<Map<String, Object[]>> dataList = new ArrayList<>();
        Object[] array;
        int faceCount = this.factList.size();
        byte[] bytes = convertToBytes(this.factList);
        Integer count = calculatePartitionValuesUp(bytes.length, this.getSegmentationNum());
        if (null == count)
            throw new RuntimeException("convert data error!the converted data must be not null!");
        Integer size = 0;
        Integer remainder = faceCount % count;

        if (remainder == 0) {
            size = faceCount / count;
        } else {
            if (faceCount < count && bytes.length < DEFAULTSEGMENTATIO) {
                size = faceCount;
                remainder = 0;
            } else {
                size = (faceCount - remainder) / count;
                count++;
            }
        }
        setRuleCommandGroup(dataList, count, remainder, size);
        return dataList;
    }

    private void setRuleCommandGroup(List<Map<String, Object[]>> dataList, Integer count, Integer remainderNum, Integer size) {
        Object[] array;
        Map<String, Object[]> map = null;
        Object[] dataArray = this.factList.toArray();
        Object[] paramGroup = new Object[]{this.group};
        Object[] paramType = null;
        if (remainderNum == 0) {
            for (int i = 0; i < count; i++) {
                map = new HashMap<>();
                array = new Object[size];
                paramType = new Object[]{this.type + "_" + count + "_" + i};
                map.put("paramGroup", paramGroup);
                map.put("paramType", paramType);
                System.arraycopy(dataArray, i * size, array, 0, size);
                map.put("data", array);
                dataList.add(map);
                map = null;
            }
        } else {
            for (int i = 0; i < count - 1; i++) {
                map = new HashMap<>();
                array = new Object[size];
                paramType = new Object[]{this.type + "_" + count + "_" + i};
                map.put("paramGroup", paramGroup);
                map.put("paramType", paramType);
                System.arraycopy(dataArray, i * size, array, 0, size);
                map.put("data", array);
                dataList.add(map);
                map = null;
            }
            map = new HashMap<>();
            array = new Object[remainderNum];
            paramType = new Object[]{this.type + "_" + count + "_" + count};
            map.put("paramGroup", paramGroup);
            map.put("paramType", paramType);
            System.arraycopy(dataArray, (count - 1) * size, array, 0, remainderNum);
            map.put("data", array);
            dataList.add(map);
            map = null;
        }
    }

    //计算分割数量
    private static final Integer calculatePartitionValuesUp(Integer numerator, Integer denominator) {
        if (null == numerator && null == denominator && denominator > 0)
            return null;
        Integer segmentatioNum = 0;
        segmentatioNum = (int) Math.ceil(((double) numerator) / denominator);
        return segmentatioNum;
    }

    //获取压缩后体积
    private final byte[] convertToBytes(Object factList) throws IOException {
        return compresss(TypeConvertTools.objToBytesByStream(factList));
    }

}
