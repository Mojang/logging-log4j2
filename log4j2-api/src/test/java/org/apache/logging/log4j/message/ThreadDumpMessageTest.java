/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */
package org.apache.logging.log4j.message;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

import static org.junit.Assert.assertTrue;

/**
 *
 */
public class ThreadDumpMessageTest {

    @Test
    public void testMessage() {
        ThreadDumpMessage msg = new ThreadDumpMessage("Testing");

        String message = msg.getFormattedMessage();
        //System.out.print(message);
        assertTrue("No header", message.contains("Testing"));
        assertTrue("No RUNNABLE", message.contains("RUNNABLE"));
        assertTrue("No ThreadDumpMessage", message.contains("ThreadDumpMessage"));
    }


    @Test
    public void testMessageWithLocks() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        ThreadDumpMessage msg;
        synchronized(this) {
            try {
                msg = new ThreadDumpMessage("Testing"/* , true */);
            } finally {
                lock.unlock();
            }
        }

        String message = msg.getFormattedMessage();
        //System.out.print(message);
        assertTrue("No header", message.contains("Testing"));
        assertTrue("No RUNNABLE", message.contains("RUNNABLE"));
        assertTrue("No ThreadDumpMessage", message.contains("ThreadDumpMessage"));
        //assertTrue("No Locks", message.contains("waiting on"));
        //assertTrue("No syncronizers", message.contains("locked syncrhonizers"));
    }
}
