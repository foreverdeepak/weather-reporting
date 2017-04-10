package com.mckinsey.wr.supervisor;

import com.mckinsey.wr.platform.Bootstrap;

/**
 * Created by deepakc on 10/04/17.
 */
public class Runner {

    public static void main(String[] args) throws Exception {
        Bootstrap bootstrap = new Bootstrap(SupervisorConfig.class, true);
        bootstrap.load();
    }
}
