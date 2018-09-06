package com.baozun.netty.client.command;

import org.jboss.netty.buffer.BigEndianHeapChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/5/18
 * Time: 7:26 PM
 */
public class RuleCommand<T> {

    private static final Integer DEFAULTSEGMENTATIONNUM = 5000;

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
            return DEFAULTSEGMENTATIONNUM;
        }
        return segmentationNum;
    }

    public void setSegmentationNum(Integer segmentationNum) {
        this.segmentationNum = segmentationNum;
    }

    public List<RuleCommand> getRuleCommandList() {
        if(null==this.factList && this.factList.size()<=0)
            return null;
        List<RuleCommand> ruleCommands = new ArrayList<>();
        List<T> list;
        int count = (int)Math.ceil(((double)this.factList.size())/DEFAULTSEGMENTATIONNUM);
        int size = 0;
        for (int i=0;i<count;i++){
            size = i*this.getSegmentationNum()<=this.factList.size()?DEFAULTSEGMENTATIONNUM:this.factList.size()-((count-i)*DEFAULTSEGMENTATIONNUM);
            list = new ArrayList<>(size);
            RuleCommand ruleCommand = new RuleCommand();
            ruleCommand.setGroup(this.getGroup());
            System.arraycopy(this.factList,i,list,0,size);
            ruleCommand.setFactList(list);
            ruleCommands.add(ruleCommand);
        }
        return ruleCommands;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
