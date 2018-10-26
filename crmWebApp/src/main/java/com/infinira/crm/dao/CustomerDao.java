package com.infinira.crm.dao;

import com.infinira.crm.model.Customer;
import com.infinira.crm.service.DataService;
import com.infinira.crm.util.LoggerUtil;

import static com.infinira.crm.util.CrmConstant.*;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Projections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

public class CustomerDao {

	private static final DataService dataService = DataService.getInstance();

	static {
		Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
	}

	public static void createCustomer(Customer customer) {

		MongoClient mongoClient = dataService.getClient();
		Document document = new Document().append(CUSTOMER_ID, (int) getNextSequence("customerId"))
				.append(FIRST_NAME, customer.getFirstName()).append(MIDDLE_NAME, customer.getMiddleName())
				.append(LAST_NAME, customer.getLastName()).append(GENDER, customer.getGender().toString())
				.append(EMAIL_ID, customer.getEmailId()).append(CONTACTS, customer.getContacts())
				.append(IDENTITIES, customer.getIdentities()).append(COMMENT, customer.getComment());

		try {
			MongoDatabase db = mongoClient.getDatabase(dataService.getDatabaseName());
			MongoCollection<Document> collection = db.getCollection(CUSTOMER);
			collection.insertOne(document);
		} catch (Throwable th) {
			LoggerUtil.loggAndThrowException("CRM_0001", th, customer.getFirstName(), "CustomerDao.createCustomer");
		} finally {
			dataService.close(null, mongoClient);
		}
	}

	public static void updateCustomer(Customer customer) {

		MongoClient mongoClient = dataService.getClient();

		Document document = new Document().append(FIRST_NAME, customer.getFirstName())
				.append(MIDDLE_NAME, customer.getMiddleName()).append(LAST_NAME, customer.getLastName())
				.append(GENDER, customer.getGender().toString()).append(EMAIL_ID, customer.getEmailId())
				.append(CONTACTS, customer.getContacts()).append(IDENTITIES, customer.getIdentities())
				.append(COMMENT, customer.getComment());

		try {
			MongoDatabase db = mongoClient.getDatabase(dataService.getDatabaseName());
			MongoCollection<Document> collection = db.getCollection(CUSTOMER);
			collection.updateOne(new Document(CUSTOMER_ID, customer.getCustomerId()), document);

		} catch (Throwable th) {
			LoggerUtil.loggAndThrowException("CRM_0002", th, customer.getCustomerId(), "CustomerDao.updateCustomer");
		} finally {
			dataService.close(null, mongoClient);
		}
	}

	public static void deleteCustomer(int customerId) {

		MongoClient mongoClient = dataService.getClient();

		try {
			MongoDatabase db = mongoClient.getDatabase(dataService.getDatabaseName());
			MongoCollection<Document> collection = db.getCollection(CUSTOMER);
			collection.deleteOne(new Document(CUSTOMER_ID, customerId));
		} catch (Throwable th) {
			LoggerUtil.loggAndThrowException("CRM_0003", th, customerId, "CustomerDao.deleteCustomer");
		} finally {
			dataService.close(null, mongoClient);
		}
	}

	@SuppressWarnings("unchecked")
	public static Customer getCustomer(int customerId) {

		MongoClient mongoClient = dataService.getClient();
		MongoCursor<Document> cursor = null;
		Customer customer = null;

		try {

			MongoDatabase db = mongoClient.getDatabase(dataService.getDatabaseName());
			MongoCollection<Document> collection = db.getCollection(CUSTOMER);
			cursor = collection.find(new Document(CUSTOMER_ID, customerId)).iterator();

			if (cursor.hasNext()) {
				Document document = cursor.next();
				customer = new Customer(document.getInteger(CUSTOMER_ID), document.getString(FIRST_NAME),
				Customer.Gender.valueOf(document.getString(GENDER)));
				customer.setMiddleName(document.getString(MIDDLE_NAME));
				customer.setLastName(document.getString(LAST_NAME));
				customer.setEmailId(document.getString(EMAIL_ID));
				customer.setContacts((Map<String, String>) document.get(CONTACTS));
				customer.setIdentities((Map<String, String>) document.get(IDENTITIES));
				customer.setComment((List<String>) document.get(COMMENT));
			}
		} catch (Throwable th) {
			LoggerUtil.loggAndThrowException("CRM_0004", th, customerId, "CustomerDao.getCustomer");
		} finally {
			dataService.close(cursor, mongoClient);
		}
		return customer;
	}

	@SuppressWarnings("unchecked")
	public static List<Customer> getAllCustomers() {

		MongoClient mongoClient = dataService.getClient();
		MongoCursor<Document> cursor = null;
		List<Customer> customers = new ArrayList<>();

		try {

			MongoDatabase db = mongoClient.getDatabase(dataService.getDatabaseName());
			MongoCollection<Document> collection = db.getCollection(CUSTOMER);

			AggregateIterable<Document> output = collection
					.aggregate(Arrays.asList(Aggregates.project(Projections.fields(Projections.include(CUSTOMER_ID),
							Projections.include(FIRST_NAME), Projections.include(MIDDLE_NAME),
							Projections.include(LAST_NAME), Projections.include(GENDER), Projections.include(EMAIL_ID),
							Projections.include(CONTACTS), Projections.include(IDENTITIES), Projections.include(COMMENT)

			)), Aggregates.limit(100), Aggregates.sort(new Document(FIRST_NAME, 1))));

			Customer customer = null;
			Document document = null;

			cursor = output.iterator();

			while (cursor.hasNext()) {
				document = cursor.next();
				customer = new Customer(document.getInteger(CUSTOMER_ID), document.getString(FIRST_NAME),
						Customer.Gender.valueOf(document.getString(GENDER)));
				customer.setMiddleName(document.getString(MIDDLE_NAME));
				customer.setLastName(document.getString(LAST_NAME));
				customer.setEmailId(document.getString(EMAIL_ID));
				customer.setContacts((Map<String, String>) document.get(CONTACTS));
				customer.setIdentities((Map<String, String>) document.get(IDENTITIES));
				customer.setComment((List<String>) document.get(COMMENT));
				customers.add(customer);
			}

		} catch (Throwable th) {
			LoggerUtil.loggAndThrowException("CRM_0004", th, "CustomerDao.getCustomer");
		} finally {
			dataService.close(cursor, mongoClient);
		}
		return customers;
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
			MongoCollection<Document> collection = db.getCollection(COUNTERS);

			collection.updateOne(query, update);
			cursor = collection.find(new Document(query)).iterator();

			if (cursor.hasNext()) {
				res = cursor.next();
				seq = res.getDouble(SEQ);
			}
			
		} catch (Throwable th) {
			LoggerUtil.loggAndThrowException("CRM_0015", th, "CustomerDao.getNextSequence");
		} finally {
			dataService.close(cursor, mongoClient);
		}
		return seq;
	}

	/*
	 * private static double getNextSequence1(String seqName) {
	 * 
	 * MongoClient mongoClient = dataService.getClient(); double seq = 0;
	 * 
	 * BasicDBObject query = new BasicDBObject(SEQID, seqName); // where _id =
	 * the input sequence name BasicDBObject update = new BasicDBObject(INC, new
	 * Document(SEQ, 1)); // the $inc here is a mongodb command for increment
	 * 
	 * try {
	 * 
	 * @SuppressWarnings("deprecation") DB db =
	 * mongoClient.getDB(dataService.getDatabaseName()); DBCollection collection
	 * = db.getCollection(COUNTERS);
	 * 
	 * DBObject result = collection.findAndModify( query, //query null, // what
	 * fields to return null, // no sorting false, //we don't remove selected
	 * document update, //increment value true, //true = return modified
	 * document true); //true = upsert, insert if no matching document seq =
	 * (double)result.get(SEQ); } catch (Throwable th) {
	 * LoggerUtil.loggAndThrowException("CRM_0015",
	 * th,"CustomerDao.getNextSequence"); } finally { dataService.close(null,
	 * mongoClient); }
	 * 
	 * return seq;
	 * 
	 * }
	 */

}
