package team.redrock.prize.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Future;

@Component
public class AsyncTaskService {


    @Autowired
    TemplateMessageService templateMessageService;

    @Async("processExecutor")
    public Future<String> executeAsyncTask(String openid, String msg, String activity, String award, String addtime, String prizetime, String remark) throws SQLException {

        Future<String> future;

      String result = templateMessageService.sendMsg(openid,msg,activity,award,addtime,prizetime,remark);
        System.out.println("线程开启"+Thread.currentThread().getName());

        future = new AsyncResult<String>(result);

        return future;
    }

}
