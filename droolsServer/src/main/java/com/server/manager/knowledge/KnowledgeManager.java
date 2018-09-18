package com.server.manager.knowledge;

import com.server.MessageCommand.KnowledgeMessage;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/17/18
 * Time: 6:55 PM
 */
public interface KnowledgeManager {

    /**
     * 重载知识库
     * @return
     */
    public KnowledgeMessage reloadKnowledge();

    /**
     * 清除知识库
     * @return
     */
    public KnowledgeMessage clearKnowledge();

    /**
     * 加载知识库
     * @return
     */
    public KnowledgeMessage loadKnowledge();

    /**
     * 清空知识库
     * @return
     */
    public KnowledgeMessage clearAllKnowledge();

}
