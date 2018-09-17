package com.baozun.netty.client.manager;


import com.server.project.wms4.OdoCommand;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 9/7/18
 * Time: 3:17 PM
 * 接受到信息的例子
 */
public class MessageHandleManagerImpl implements MessageHandleManager {

    @Override
    public void messageHandle(Object message) throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS");
        Map<String, Object[]> map = (Map<String, Object[]>) message;
        List list = Arrays.asList(map.get("data"));
        System.out.println(formatter.format(new Date()) + "   end");
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            if (null != ((OdoCommand) list.get(i)).getMessage())
                count++;
        }
        System.out.println(list.size() + "   count");
        System.out.println(count + "   result");
    }
}
