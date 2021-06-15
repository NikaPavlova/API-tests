package data;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import com.google.gson.JsonObject;

import io.restassured.RestAssured;

public class PatchData {
	String api = "api/users/2";
	String name = "morpheus2";
	String job = "zion resident";
	String oldTimeStamp;
	String ROOT_URI;
	JsonObject jsonObject;

	public PatchData(String ROOT_URI) {
		this.ROOT_URI = ROOT_URI;
	}

	public PatchData getPreviousTimeStamp() {
		oldTimeStamp = RestAssured.given().header("Content-Type", "application/json").body("")
				.patch(ROOT_URI + "api/users/2").then().extract().path("updatedAt");
		return this;
	}
	
	public PatchData reqBody() {
		jsonObject = new JsonObject();
		jsonObject.addProperty("name", name);
		jsonObject.addProperty("job", job);
		return this;
	}

	public void requestAndCompare() {
		RestAssured.given().header("Content-Type", "application/json").body(jsonObject.toString())
				.patch(ROOT_URI + "api/users/2").then().assertThat().statusCode(200).body("name", equalTo(name))
				.body("updatedAt", not(oldTimeStamp));
	}
}
