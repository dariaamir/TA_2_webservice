package steps;

import classes.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Assert;
import requests.RequestSender;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;


public class StepDefinitions {

    private String BaseURL = "https://jsonplaceholder.typicode.com/";
    private HttpURLConnection connection;

    //Requests

    @When("^user requests for the post by it's id$")
    public void user_requests_for_the_post_by_its_id(DataTable dataTable) throws IOException {
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        String id = dataMap.get("id");
        RequestSender.requestSend("GET",BaseURL + "posts/" + id, "");
    }

    @When("^user requests for the comment by it's id$")
    public void user_requests_for_the_comment_by_its_id(DataTable dataTable) throws IOException {
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        String id = dataMap.get("id");
        RequestSender.requestSend("GET",BaseURL + "comments/" + id, "");
    }

    @When("^user requests for all todos by user id$")
    public void user_requests_for_all_todos_by_user_id(DataTable dataTable) throws IOException {
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        String id = dataMap.get("id");
        RequestSender.requestSend("GET",BaseURL + "todos?userId=" + id, "");
    }

    @When("^user requests for todo by id$")
    public void user_requests_for_todo_by_id(DataTable dataTable) throws IOException {
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        String id = dataMap.get("id");
        RequestSender.requestSend("GET", BaseURL + "todos/" + id, "");
    }

    @When("^user creates new post with parameters$")
    public void userCreatesNewPostWithParameters(DataTable dataTable) throws IOException {
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        String title = dataMap.get("title");
        String body = dataMap.get("body");
        String userId = dataMap.get("userId");
        String callBody = String.format("{\"title\": \"%s\", \"body\": \"%s\", \"userId\": %s}", title, body, userId);
        RequestSender.requestSend("POST", BaseURL + "posts", callBody);
    }

    @When("^user finds a post by id and updates all post fields$")
    public void userFindsAPostByIdAndUpdatesAllPostFields(DataTable dataTable) throws IOException {
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        String postId = dataMap.get("postId");
        String title = dataMap.get("title");
        String body = dataMap.get("body");
        String userId = dataMap.get("userId");
        String callBody = String.format("{\"id\": \"%s\", \"title\": \"%s\", \"body\": \"%s\", \"userId\": %s}"
                ,postId, title, body, userId);
        RequestSender.requestSend("PUT", BaseURL + "posts/" + postId, callBody);
    }

    @When("^user finds a post by id and updates one field with new value$")
    public void userUpdatesPostFieldWithNewValue(DataTable dataTable) throws IOException {

        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        String post_field = dataMap.get("post_field");
        String new_value = dataMap.get("new_value");
        String postId = dataMap.get("postId");
        String callBody = String.format("{\"%s\": \"%s\"}", post_field, new_value);
        RequestSender.requestSend("PATCH", BaseURL + "posts/" + postId, callBody);
    }

    //Responce codes

    @Then("^response code is (\\d+)$")
    public void responce_code_is(int code){
        Assert.assertEquals(code, RequestSender.getResponseCodeFromCall());
    }

    //Post

    @And("^response for the post returns correct user id$")
    public void response_for_the_post_returns_correct_user_id(DataTable dataTable){
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        String author = dataMap.get("author");
        Assert.assertEquals(author, RequestSender.getResponseBodyElementFromCall("userId"));
    }

    @And("^response for the post returns correct title$")
    public void response_for_the_post_returns_correct_title(DataTable dataTable){
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        String title = dataMap.get("title");
        Assert.assertEquals(title, RequestSender.getResponseBodyElementFromCall("title"));
    }

    @And("^response for the post returns correct body$")
    public void response_for_the_post_returns_correct_body(DataTable dataTable) {
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        String body = dataMap.get("body");
        Assert.assertEquals(body, RequestSender.getResponseBodyElementFromCall("body"));
    }

    @Then("^response contains updated data$")
    public void responseContainsUpdatedData(DataTable dataTable) throws IOException {
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        String title = dataMap.get("title");
        String body = dataMap.get("body");
        String userId = dataMap.get("userId");

        Assert.assertEquals(title, RequestSender.getResponseBodyElementFromCall("title"));
        Assert.assertEquals(body, RequestSender.getResponseBodyElementFromCall("body"));
        Assert.assertEquals(userId, RequestSender.getResponseBodyElementFromCall("userId"));
    }

    @And("^response returns updated value at the changed field$")
    public void responseReturnsUpdatedValueAtTheChangedField(DataTable dataTable) throws IOException {
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        String postField = dataMap.get("post_field");
        String changedValue = dataMap.get("new_value");
        Assert.assertEquals(changedValue, RequestSender.getResponseBodyElementFromCall(postField));
    }

    @When("^user deleted post by post id$")
    public void userDeletedPostBypostId(DataTable dataTable) throws IOException {
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        String id = dataMap.get("postId");
        RequestSender.requestSend("DELETE", BaseURL + "posts/" + id, "");
    }

    @And("^response body is empty$")
    public void responseBodyIsEmpty(){
        String emptyResponse = "{}\n";
        Assert.assertEquals(emptyResponse, RequestSender.getResponseBodyFromCall());
    }

    // Comment

    @And("^response for the comment returns correct post id$")
    public void response_or_the_comment_returns_correct_poctid(DataTable dataTable){
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        String postId = dataMap.get("postId");
        Assert.assertEquals(postId, RequestSender.getResponseBodyElementFromCall("postId"));
    }

    @And("^response for the comment returns correct name$")
    public void response_or_the_comment_returns_correct_name(DataTable dataTable){
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        String name = dataMap.get("name");
        Assert.assertEquals(name, RequestSender.getResponseBodyElementFromCall("name"));
    }

    @And("^response for the comment returns correct email$")
    public void response_or_the_comment_returns_correct_email(DataTable dataTable){
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        String email = dataMap.get("email");
        Assert.assertEquals(email, RequestSender.getResponseBodyElementFromCall("email"));
    }

    @And("^response for the comment returns correct body$")
    public void response_for_the_comment_returns_correct_body(DataTable dataTable){
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        String body = dataMap.get("body");
        String compageBody = RequestSender.getResponseBodyElementFromCall("body");
        Assert.assertEquals(body, compageBody);
    }

    // Todos
    @And("^response returns correct number of todos$")
    public void response_returns_correct_number_of_todos(DataTable dataTable){
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        int number = Integer.parseInt(dataMap.get("number"));
        String searchString = dataMap.get("search_request");
        Assert.assertEquals(number, RequestSender.getResponseBodyNumberOfElementsFromCall(searchString));
    }

    @And("^response returns correct number of completed todos$")
    public void responseReturnsCorrectNumberOfCompletedTodos(DataTable dataTable) {
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        int number = Integer.parseInt(dataMap.get("number"));
        String searchString = dataMap.get("search_request");
        Assert.assertEquals(number, RequestSender.getResponseBodyNumberOfElementsFromCall(searchString));
    }

    @And("^response returns correct status$")
    public void responseReturnsCorrectStatus(DataTable dataTable){
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        String status = dataMap.get("status");
        Assert.assertEquals(status, RequestSender.getResponseBodyElementFromCall("completed"));
    }

    @Then("^todos list is empty$")
    public void todosListLsEmpty(){
        String emptyResponse = "[]\n";
        Assert.assertEquals(emptyResponse, RequestSender.getResponseBodyFromCall());
    }

    //Users
    @When("^user sends request to create new user with required data$")
    public void userSendsRequestToCreateNewUserWithRequiredData(DataTable dataTable) throws IOException {
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        String name = dataMap.get("name");
        String username = dataMap.get("username");
        String email = dataMap.get("email");
        String street = dataMap.get("street");
        String suite = dataMap.get("suite");
        String city = dataMap.get("city");
        String zipcode = dataMap.get("zipcode");
        String lat = dataMap.get("lat");
        String lng = dataMap.get("lng");
        String phone = dataMap.get("phone");
        String website = dataMap.get("website");
        String c_name = dataMap.get("c_name");
        String catchPhrase = dataMap.get("catchPhrase");
        String bs = dataMap.get("bs");

        Users.Company company = new Users.Company(c_name, catchPhrase, bs);
        Users.Geo geo = new Users.Geo(lat, lng);
        Users.Address address = new Users.Address(street, suite, city, zipcode, geo);
        Users user = new Users(name, username, email, address, phone, website, company);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestBody = ow.writeValueAsString(user);
        RequestSender.requestSend("POST", BaseURL + "users", requestBody);
    }

    @Then("^response returns correct user data$")
    public void responseReturnsCorrectUserData(DataTable dataTable) throws IOException {
        Map<String, String> dataMap = dataTable.asMap(String.class, String.class);
        String name = dataMap.get("name");
        String username = dataMap.get("username");
        String email = dataMap.get("email");
        String street = dataMap.get("street");
        String suite = dataMap.get("suite");
        String city = dataMap.get("city");
        String zipcode = dataMap.get("zipcode");
        String lat = dataMap.get("lat");
        String lng = dataMap.get("lng");
        String phone = dataMap.get("phone");
        String website = dataMap.get("website");
        String c_name = dataMap.get("c_name");
        String catchPhrase = dataMap.get("catchPhrase");
        String bs = dataMap.get("bs");

        Assert.assertEquals(name, RequestSender.getResponseBodyElementFromCall("name"));
        Assert.assertEquals(username, RequestSender.getResponseBodyElementFromCall("username"));
        Assert.assertEquals(email, RequestSender.getResponseBodyElementFromCall("email"));
        Assert.assertEquals(street, RequestSender.getResponseBodyNestedElementFromCall("street", "address"));
        Assert.assertEquals(suite, RequestSender.getResponseBodyNestedElementFromCall("suite", "address"));
        Assert.assertEquals(city, RequestSender.getResponseBodyNestedElementFromCall("city", "address"));
        Assert.assertEquals(zipcode, RequestSender.getResponseBodyNestedElementFromCall("zipcode", "address"));
        Assert.assertEquals(lat, RequestSender.getResponseBodyNestedElementFromCall("lat", "address", "geo"));
        Assert.assertEquals(lng, RequestSender.getResponseBodyNestedElementFromCall("lng", "address", "geo"));
        Assert.assertEquals(phone, RequestSender.getResponseBodyElementFromCall("phone"));
        Assert.assertEquals(website, RequestSender.getResponseBodyElementFromCall("website"));
        Assert.assertEquals(c_name, RequestSender.getResponseBodyNestedElementFromCall("name", "company"));
        Assert.assertEquals(catchPhrase, RequestSender.getResponseBodyNestedElementFromCall("catchPhrase", "company"));
        Assert.assertEquals(bs, RequestSender.getResponseBodyNestedElementFromCall("bs", "company"));

    }

    @AfterClass()
    public void afterScenario() {
        connection.disconnect();
    }
}
