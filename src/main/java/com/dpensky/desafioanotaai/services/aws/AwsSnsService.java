package com.dpensky.desafioanotaai.services.aws;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;

@Service
public class AwsSnsService {
    AmazonSNS snsClient;
    Topic catalogTopic;
    public AwsSnsService(AmazonSNS snsClient, @Qualifier("catalogEventsTopic") Topic catalogTopic) {
        this.snsClient = snsClient;
        this.catalogTopic = catalogTopic;
    }

    public void publish(MessageDTO messageDTO) {
        this.snsClient.publish(catalogTopic.getTopicArn(), messageDTO.toString());
    }
}
