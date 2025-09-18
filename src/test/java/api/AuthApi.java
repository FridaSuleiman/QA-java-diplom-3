package api;

import data.User;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthApi {

    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    private static final String API_PATH = "/api";
    private static final String AUTH_PATH = "/auth";

    private static final String REGISTER_ENDPOINT = API_PATH + AUTH_PATH + "/register";
    private static final String LOGIN_ENDPOINT = API_PATH + AUTH_PATH + "/login";
    private static final String USER_ENDPOINT = API_PATH + AUTH_PATH + "/user";

    static {
        RestAssured.baseURI = BASE_URL;
    }

    @Step("API: Регистрация пользователя")
    public static Response registerUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(REGISTER_ENDPOINT);
    }

    @Step("API: Вход пользователя")
    public static Response loginUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(LOGIN_ENDPOINT);
    }

    @Step("API: Удаление пользователя")
    public static void deleteUser(String accessToken) {
        given()
                .header("Authorization", accessToken)
                .when()
                .delete(USER_ENDPOINT);
    }
}