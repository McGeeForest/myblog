package com.foresttech.myblog.Platform.Api;

import com.foresttech.myblog.Utils.Message;

import com.foresttech.myblog.Utils.Parser;
import com.foresttech.myblog.Utils.ResponseHead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("test")
public class testApi {
    Logger logger = LoggerFactory.getLogger(testApi.class);
    @RequestMapping("te")
    public Message te() throws IllegalAccessException {
        Message message = new Message();
        message.setResHead(ResponseHead.SUCCESS);
        message.setResData("Data");
        logger.info(Parser.toString(message));
        return message;
    }



}
