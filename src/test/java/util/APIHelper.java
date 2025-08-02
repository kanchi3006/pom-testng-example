package util;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class APIHelper {
        static RequestSpecBuilder  requestBuilder = new RequestSpecBuilder();
    static RequestSpecification requestSpec;

            Response response;
    public void requestSpec(String path) {
        if(requestSpec!=null)
          requestSpec = given().spec(requestSpec).basePath(path).log().all();
        else
            requestSpec = given().contentType(ContentType.JSON).basePath(path).log().all();
    }
    public void addFormParam(String formName , String formValue) {
        setContentType("urlEncoded");
        requestSpec = requestSpec.formParam(formName,formValue);
    }

    public void setContentType(String type) {
        requestSpec = requestSpec.contentType("application/x-www-form-urlencoded");
    }

    public void method(String methodName) {
        switch (methodName.toLowerCase()) {
            case "get":
                response = requestSpec.get();
                break;
            case "post":
                response = requestSpec.post();
                break;
            case "delete":
                response = requestSpec.delete();
                break;
        }
    }
    public void statusCodeValidation(int statusCode) {
        response.then().log().all().assertThat().statusCode(statusCode);
    }
}
