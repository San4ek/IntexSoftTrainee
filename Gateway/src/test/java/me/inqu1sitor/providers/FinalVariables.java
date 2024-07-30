package me.inqu1sitor.providers;

public class FinalVariables {

    public static final String USER_SERVICE_PORT_PROPERTY = "remote-services.user-service.port";
    public static final String USER_SERVICE_HOST_PROPERTY = "remote-services.user-service.host";
    public static final String JWT = "eyJraWQiOiIzYzU5M2RjNi00OTg2LTRjNDktOTVlMS1mMTYzNDM2OTk3ZWQiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJjMGE4MDA2Ny05MDU0LTEyMzUtODE5MC01NGEyNTFkNzAwMDAiLCJhdWQiOiJpbnRleHNvZnQiLCJuYmYiOjE3MTk0MTAxOTYsInJvbGUiOlsiQURNSU4iXSwic2NvcGUiOlsiYXV0aCJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjkwMDAiLCJleHAiOjE3MTk0MTA0OTYsImlhdCI6MTcxOTQxMDE5NiwianRpIjoiYWZlOWE5MWMtZDEyMS00N2I5LWEzNzgtMzEwYzczNzE0MWFmIn0.Zii6Zx-G_L8GQhIdH2iCX-SFZGjmtQOd1uvwT84woCTtCau-Lr3VkT7gQQqInM_ieUuKJQD-_TV8Ua9FgEyBFS36AZU3mVXDAQaEoRnB9zPrXDlG20GJi9CO5GKLjm8evi2yXWGeHxqFEbQHeY0HGR3pNsbSOCeEXHuBbIFuHn9PAzhqKxxK2An7UOUw1dn7HWfkFn8qPgfJFUE7g6Ae3dKwSdRIU0I-3AR85MkBq3iZIF-5OLJA6Ej-fM4WgL03jDko6k1AVJpr55vsnXxGwrwV2FKMm8qfS7y8NJ1hJnBkJAHnoZsBdgooZZdwk5OwWGxKpH7eCcQaVlULfZAjaw";
    public static final String AUTH_HEADER_VALUE = "Bearer " + JWT;
    public static final String REDIS_KEY = "access_for_id:" + JWT;
    public static final String JSON_RESPONSE_BODY = """
            {
                "response":"Response body"
            }
            """;
    public static final String ACCOUNT_ID_PARAM = "accountId";
    public static final String ACCOUNT_ID_PARAM_VALUE = "c0a80067-9054-11bc-8190-54b23e160000";
    public static final String AUTH_SERVICE_HOST_PROPERTY = "remote-services.auth-service.host";
    public static final String AUTH_SERVICE_PORT_PROPERTY = "remote-services.auth-service.port";

}
