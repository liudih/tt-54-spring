package com.tomtop.management.config;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mysql.jdbc.StringUtils;

public class MongoDBUtil {

	public static MongoCollection<Document> getMongoDBCollection(GlobalParameter globalParameter, String collectionName) {
		MongoClient mongoClient = null;
		MongoCollection<Document> collection = null;
		String mongodbIP = globalParameter.getMongodbIp();
		Integer mongodbPort = Integer.parseInt(globalParameter.getMongodbPort());
		String mongodbDataBase = globalParameter.getMongodbDataBase();
		String mongodbUser = globalParameter.getMongodbUser();
		String mongodbPassword = globalParameter.getMongodbPassword();
		try {
			// 连接到 mongodb 服务
			if (StringUtils.isNullOrEmpty(mongodbUser)) {
				mongoClient = new MongoClient(mongodbIP, mongodbPort);
			} else {
				// 连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
				// ServerAddress()两个参数分别为 服务器地址 和 端口
				ServerAddress serverAddress = new ServerAddress(mongodbIP, mongodbPort);
				List<ServerAddress> addrs = new ArrayList<ServerAddress>();
				addrs.add(serverAddress);
				// MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称
				// 密码
				MongoCredential credential = MongoCredential.createScramSha1Credential(mongodbUser, mongodbDataBase,
						mongodbPassword.toCharArray());
				List<MongoCredential> credentials = new ArrayList<MongoCredential>();
				credentials.add(credential);
				// 通过连接认证获取MongoDB连接
				mongoClient = new MongoClient(addrs, credentials);
			}
			MongoDatabase mongoDatabase = mongoClient.getDatabase(mongodbDataBase);
			collection = mongoDatabase.getCollection(collectionName);
			if (null == collection) {
				mongoDatabase.createCollection(collectionName);
				collection = mongoDatabase.getCollection(collectionName);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return collection;
	}

}