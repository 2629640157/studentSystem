package com.you.system;

import org.apache.activemq.command.ActiveMQQueue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.Queue;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 游斌
 * @create 2020-12-25  23:12
 */
public class TimeTest {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Queue queue;
    @Test
    public void tests() {
//         ZonedDateTime now = ZonedDateTime.now();
        LocalDateTime now = LocalDateTime.now();
        System.out.println("now = " + now);
        DateTimeFormatter date = DateTimeFormatter.ISO_DATE;
        String format = now.format(date);
        System.out.println("format = " + format);


    }

    @Test
    public void test1() {
        ArrayList<List<Integer>> lists = new ArrayList<>();
    }

     /*@Test
      public void message1() {
      Queue queue=   new ActiveMQQueue("queue");
         jmsMessagingTemplate.convertAndSend(queue,"123456");
         System.out.println("success");
     }
*/
    @Test
    public void recivemMessage() {
        jmsMessagingTemplate.convertAndSend(queue,"123456");
        System.out.println("success");
    }
     @Test
      public void t1() {


      }
}
