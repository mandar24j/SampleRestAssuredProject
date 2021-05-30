package TestCases;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TC_VideoGameAPI {
    String recordId = "" + ((int) (Math.random() * 9000) + 10000);


    @Test
    public void TC_001_GetAllRecords() {
        given()
                .when()
                .get("http://localhost:8080/app/videogames")
                .then()
                .statusCode(200);
    }

    @Test
    public void TC_002_PostNewRecord() {
        Map data = new HashMap();

        data.put("id", recordId);
        data.put("name", "Spider Man");
        data.put("releaseDate", "2021-05-30T11:02:14.553Z");
        data.put("reviewScore", "99");
        data.put("category", "Action");
        data.put("rating", "Uni");
        //System.out.println(data);
        Response res =
                given()
                        .contentType(ContentType.JSON)
                        .body(data)
                        .when()
                        .post("http://localhost:8080/app/videogames")
                        .then()
                        .statusCode(200)
                        .log().body()
                        .extract().response();

        String resString = res.asString();
        Assert.assertTrue(resString.contains("Record Added Successfully"));
    }

    @Test
    public void TC_003_GetRecord() {
        given()
                .when()
                .get("http://localhost:8080/app/videogames/" + recordId)
                .then()
                .statusCode(200)
                .log().body()
                .body("videoGame.id", equalTo(recordId))
                .body("videoGame.name", equalTo("Spider Man"))
                .body("videoGame.reviewScore", equalTo("99"))

        ;

    }

    @Test
    public void TC_004_UpdateRecord() {
        Map data = new HashMap();
        data.put("id", recordId);
        data.put("name", "Spider Man 2");
        data.put("releaseDate", "2021-05-30T11:02:14.553Z");
        data.put("reviewScore", "99");
        data.put("category", "Action");
        data.put("rating", "Uni");
        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .put("http://localhost:8080/app/videogames/" + recordId)
                .then()
                .statusCode(200)
                .log().body()
                .body("videoGame.id", equalTo(recordId))
                .body("videoGame.name", equalTo("Spider Man 2"))
        ;
    }

    @Test
    public void TC_005_DeleteRecord() {
        Response res =
                given()
                        .when()
                        .delete("http://localhost:8080/app/videogames/" + recordId)
                        .then()
                        .statusCode(200)
                        .log().body()
                        .extract().response();
        String resString = res.asString();
        Assert.assertTrue(resString.contains("Record Deleted Successfully"));
    }

}
