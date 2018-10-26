package com.infinira.crm.dao;

import static com.infinira.crm.util.CrmConstant.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.infinira.crm.model.User;
import com.infinira.crm.service.DataService;
import com.infinira.crm.util.LoggerUtil;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class UserDao {
	private static final DataService dataService = DataService.getInstance();
	static {
		Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
	}

	public static void createUser(User user) {
		MongoClient mongoClient = dataService.getClient();
		Document document = new Document().append(USER_ID, (int) getNextSequence("userId")).append("password",
				user.getPassword());
		try {
			MongoDatabase db = mongoClient.getDatabase(dataService.getDatabaseName());
			MongoCollection<Document> collection = db.getCollection("user");
			collection.insertOne(document);
		} catch (Throwable th) {
			LoggerUtil.loggAndThrowException("CRM_0001", th, "CustomerDao.createCustomer");
		} finally {
			dataService.close(null, mongoClient);
		}
	}

	private static double getNextSequence(String seqName) {

		MongoClient mongoClient = dataService.getClient();
		MongoCursor<Document> cursor = null;
		Document res = null;
		double seq = 0;

		// where _id = the input sequence name
		Document query = new Document(SEQID, seqName);
		Document update = new Document(INC, new Document(SEQ, 1));
		try {
			MongoDatabase db = mongoClient.getDatabase(dataService.getDatabaseName());
			MongoCollection<Document> collection = db.getCollection(USER_COUNTERS);

			collection.updateOne(query, update);
			cursor = collection.find(new Document(query)).iterator();

			if (cursor.hasNext()) {
				res = cursor.next();
				seq = res.getDouble(SEQ);
			}

		} catch (Throwable th) {
			LoggerUtil.loggAndThrowException("Unable to create user Id", th, "CustomerDao.getNextSequence");
		} finally {
			dataService.close(cursor, mongoClient);
		}
		return seq;
	}

	public static boolean authenticateUser(User user) {
		if (user == null) {
			throw new RuntimeException("Invalid User Object is Given!");
		}
		MongoClient mongoClient = dataService.getClient();
		MongoCursor<Document> cursor = null;
		try {
			MongoDatabase db = mongoClient.getDatabase(dataService.getDatabaseName());
			MongoCollection<Document> collection = db.getCollection(USER);

			List<Document> query = new ArrayList<Document>();
			query.add(new Document(USER_ID, user.getUserId()));
			query.add(new Document(PASSWORD, user.getPassword()));
			cursor = collection.find(new Document("$and", query)).iterator();
			if (cursor.hasNext()) {
				Document document = cursor.next();
				long userId = document.getInteger(USER_ID);
				String password = document.getString(PASSWORD);
				return userId == user.getUserId() && password.equals(user.getPassword());
			}
		} catch (Throwable th) {
			LoggerUtil.loggAndThrowException(MessageFormat.format("Failed to authenticate user {0}", user.getUserId()), th);
		} finally {
			dataService.close(cursor, mongoClient);
		}
		return false;
	}
}
