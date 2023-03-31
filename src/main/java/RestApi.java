import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class RestApi {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder completed = new StringBuilder();
        String url = "http://94.198.50.185:7081/api/users";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        String key = response.getHeaders().get("Set-Cookie").get(0).substring(0,response.getHeaders().get("Set-Cookie").get(0).indexOf(';'));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Cookie",key);

        User user = new User();
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte) 31);
        HttpEntity<User> userHttpEntity = new HttpEntity<>(user,httpHeaders);
        completed.append(restTemplate.postForObject(url,userHttpEntity,String.class));
        System.out.println(completed);

        user.setName("Thomas");
        user.setLastName("Shelby");
        HttpEntity<User> userHttpEntity1 = new HttpEntity<>(user, httpHeaders);
        completed.append(restTemplate.exchange(url, HttpMethod.PUT,userHttpEntity,String.class).getBody());
        System.out.println(completed);

        HttpEntity<User> userHttpEntity2 = new HttpEntity<>(httpHeaders);
        completed.append(restTemplate.exchange(url+"/3",HttpMethod.DELETE,userHttpEntity2,String.class).getBody());

        System.out.println(completed);
    }
}
