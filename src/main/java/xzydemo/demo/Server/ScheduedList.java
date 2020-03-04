package xzydemo.demo.Server;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduedList {

    @Scheduled(cron = "*/5 * * * * *")
    public void CheckMailCodeValid(){
        AccountCheckServer accountCheckServer=AccountCheckServer.getInstance();
        accountCheckServer.CheckTime();
    }

}
