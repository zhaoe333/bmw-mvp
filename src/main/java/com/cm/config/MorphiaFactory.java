package com.cm.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.MapperOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Mongo.class)
public class MorphiaFactory {

    @Autowired
    private Mongo mongo;

    @Autowired
    MongoProperties mongoProperties;

    @Bean("datastore")
    public AdvancedDatastore get() {
        Morphia morphia = new Morphia();
        //morphia参数设置
        MapperOptions options = new MapperOptions();
        options.setStoreEmpties(false);
        options.setStoreNulls(false);
        //morphia的相关配置文件 TODO
        morphia.getMapper().setOptions(options);
        morphia.mapPackage( "com.cm", true);
        AdvancedDatastore dataStore = (AdvancedDatastore)morphia.createDatastore((MongoClient) mongo,mongoProperties.getDatabase());
        dataStore.ensureIndexes();
        return dataStore;
    }
}
