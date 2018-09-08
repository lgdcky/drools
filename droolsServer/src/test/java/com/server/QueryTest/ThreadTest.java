package com.server.QueryTest;

import com.server.command.OdoCommand;
import com.server.manager.query.QueryManager;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/16/18
 * Time: 2:39 PM
 */
public class ThreadTest implements Runnable {

    private QueryManager queryManager;

    private List<OdoCommand> odoCommands;

    private String name;

    public ThreadTest(QueryManager queryManager,List<OdoCommand> odoCommands,String name){
        this.queryManager = queryManager;
        this.odoCommands = odoCommands;
        this.name=name;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a heartbeat, starting the heartbeat causes the object's
     * <code>run</code> method to be called in that separately executing
     * heartbeat.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        long start = System.currentTimeMillis();
        List list = (List) queryManager.queryCommandWithStatelessKieSessionAsList("test", odoCommands);
        System.out.println("=============================" + (System.currentTimeMillis() - start));
        int count=0;
        for (OdoCommand odoCommand : odoCommands) {
            if (null != odoCommand.getMessage()) {
                count++;
            }
        }
        System.out.println(name+"^^^^^^^^^^^^^^^^^^^^^^^^^^^"+count);
    }
}
