package com.infinira.crm.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.io.InputStream;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCursor;

import org.bson.Document;

import static com.infinira.crm.util.CrmConstant.*;
import com.infinira.crm.util.LoggerUtil;
import com.infinira.crm.util.StringUtils;

public class DataService {
	private static volatile DataService dataService;

	private String dbName;
	private String uri;
	private MongoClientURI mongoClientURI;

	// Singleton Pattern
	private DataService() {
		init();
	}

	public static DataService getInstance() {
		if (dataService == null) {
			synchronized (DataService.class) {
				if (dataService == null) {
					dataService = new DataService();
				}
			}
		}
		return dataService;
	}

	private void init() {

		String connectionTimeout = null;

		InputStream is = getClass().getClassLoader().getResourceAsStream(DB_PROPERTY_FILE_NAME);

		if (is == null) {
			LoggerUtil.loggAndThrowException("CRM_0007", null, DB_PROPERTY_FILE_NAME, "DataService.init()");
		}

		try {
			Properties props = new Properties();
			props.loadFromXML(is);

			uri = props.getProperty(URI);
			connectionTimeout = props.getProperty(CONNECTION_TIMEOUT);
		} catch (Throwable th) {
			LoggerUtil.loggAndThrowException("CRM_0008", th, DB_PROPERTY_FILE_NAME, "DataService.init()");
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Throwable th) {
					LoggerUtil.error("CRM_0009", th, "DataService.init()");
				}
			}
		}

		if (StringUtils.isNullOrEmpty(uri)) {
			LoggerUtil.loggAndThrowException("CRM_0010", null, DB_PROPERTY_FILE_NAME);
		}

		if (StringUtils.isNullOrEmpty(connectionTimeout)) {
			LoggerUtil.loggAndThrowException("CRM_0011", null, DB_PROPERTY_FILE_NAME);
		}

		Map<String, Object> options = new HashMap<String, Object>();
		options.put(CONNECTION_TIMEOUT, connectionTimeout);

		mongoClientURI = prepareClientURI(uri, options);

		dbName = mongoClientURI.getDatabase();

		if (StringUtils.isNullOrEmpty(dbName)) {
			LoggerUtil.loggAndThrowException("CRM_0012", null, DB_PROPERTY_FILE_NAME);
		}

	}

	private MongoClientURI prepareClientURI(String uri, Map<String, Object> options) {

		MongoClientOptions.Builder builder = new Builder();

		try {
			builder.connectTimeout(Integer.parseInt(options.get(CONNECTION_TIMEOUT).toString()));
		} catch (Exception ex) {
			LoggerUtil.loggAndThrowException("CRM_0013", ex, DB_PROPERTY_FILE_NAME, "DataService.prepareClientURI");
		}
		return new MongoClientURI(uri, builder);
	}

	// Creating Client
	public MongoClient getClient() {
		try {
			return new MongoClient(mongoClientURI);
		} catch (Throwable th) {
			LoggerUtil.error("CRM_0014", th, mongoClientURI.getUsername(), mongoClientURI.getHosts());
			throw new RuntimeException(th);
		}
	}

	// getDatabaseName
	public String getDatabaseName() {
		return dbName;
	}

	// Closing Database Resources
	public void close(MongoCursor<Document> cursor, MongoClient mongoClient) {

		if (cursor != null) {
			try {
				cursor.close();
			} catch (Throwable th) {
				LoggerUtil.error("CRM_0006", th);
			}
		}

		if (mongoClient != null) {
			try {
				mongoClient.close();
			} catch (Throwable th) {
				LoggerUtil.error("CRM_0005", th);
			}
		}
	}
}
