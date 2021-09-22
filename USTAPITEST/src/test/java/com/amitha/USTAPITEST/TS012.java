package com.amitha.USTAPITEST;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import junit.framework.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.containsString;

public class TS012 {
	
	public static String response;
	public static String vID;
	public static String vMsg;

	public static void main(String[] args) throws InterruptedException {

		RestAssured.baseURI="http://localhost:8080/";
		CREAT_CRUD();
		Thread.sleep(5000);
		GET_CRUD();
		Thread.sleep(5000);
		UPDATE_CRUD();
		Thread.sleep(5000);
		DELETE_CRUD();

	}
	
public static void CREAT_CRUD() {
		
		response=given().header("Content-Type","application/json").header("Connection","keep-alive").body(addPizzaPayLoad())
		.when().post("addPizza").then().assertThat().statusCode(201).header("unique", containsString("VegPizza3")).extract().response().asString();

		System.out.println(response);
		
		JsonPath jpath = new JsonPath(response);
		vID=jpath.getString("id");
		vMsg=jpath.getString("msg");
		
		System.out.println("ID is ="+vID);
		System.out.println("Msg is "+vMsg);
		
	}
	
	public static void GET_CRUD() {
		
		
		given().when().get("getPizza/"+vID).then().log().all().extract().response().asString();
		JsonPath jpath=new JsonPath(response);
		Assert.assertEquals(vID, jpath.getString("id"));
	}
	
	public static void UPDATE_CRUD() {
		
		System.out.println("This is update api");
		given().header("Content-Type","application/json").body(updatePizza())
		.when().put("/updatePizza/"+vID)
		.then().log().body();
	}
	
	public static void DELETE_CRUD() {
		
		System.out.println("This is delete API");
		given().header("Content-Type","application/json").body(deletePizza())
		.when().delete("/deletePizza")
		.then().log().body().log();
	}
	
	
	public static String addPizzaPayLoad() {
		
		return "{\r\n"
				+ "\"productname\":\"VegPizza\",\r\n"
				+ "\"price\":\"500\"\r\n"
				+ "}";
	}
	
	public static String updatePizza() {
		
		return "{\r\n"
				+ "\"productname\":\"VegieUpdated\",\r\n"
				+ "\"price\":\"5003\"\r\n"
				+ "}";
	}
	
	public static String deletePizza() {
		
		return "{\r\n"
				+ "    \"pid\":\""+vID+"\"\r\n"
				+ "}";
	}

}


