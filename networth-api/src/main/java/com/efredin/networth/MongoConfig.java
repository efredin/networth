package com.efredin.networth;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {
  
    @Override
    protected String getDatabaseName() {
        return "networth";
    }

    @Value("${spring.data.mongodb.uri}")
    protected String uri;

    @Override
    public MongoClient mongoClient() {
        MongoClientURI uri = new MongoClientURI(this.uri);
        return new MongoClient(uri);
    }
  
    @Override
    protected String getMappingBasePackage() {
        return "com.efredin.networth";
    }
}