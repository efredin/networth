package com.efredin.networth;

import com.mongodb.MongoClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {
  
    @Value("${db.databaseName}")
    private String databaseName;

    @Value("${db.host}")
    private String host;

    private int port;
    
    @Value("${db.port}")
    private void setPort(String value) {
        try {
            this.port = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            this.port = 27017;
        }
    }

    @Override
    protected String getDatabaseName() {
        return this.databaseName;
    }

    @Override
    public MongoClient mongoClient() {
        return new MongoClient(this.host, this.port);
    }
  
    @Override
    protected String getMappingBasePackage() {
        return "com.efredin.networth";
    }
}