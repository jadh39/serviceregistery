package com.infobip.serviceregistry;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

@WebAppConfiguration
public abstract class AbstractAPITest extends AbstractTest {

    protected MockMvc mvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    protected MockRestServiceServer mockRestServiceServer;

    @Autowired
    protected RestTemplate restTemplate;


    /**
     * Prepares the test class for execution of web tests. Builds a MockMvc
     * instance. Call this method from the concrete JUnit test class in the
     * <code>@Before</code> setup method.
     */
    protected void setUp() {

        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);

    }

    /**
     * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
     *
     * @param obj The Object to map.
     * @return A String of JSON.
     * @throws JsonProcessingException Thrown if an error occurs while mapping.
     */
    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    /**
     * Maps a String of JSON into an instance of a Class of type T. Uses a
     * Jackson ObjectMapper.
     *
     * @param json  A String of JSON.
     * @param clazz A Class of type T. The mapper will attempt to convert the
     *              JSON into an Object of this Class type.
     * @return An Object of type T.
     * @throws JsonParseException   Thrown if an error occurs while mapping.
     * @throws JsonMappingException Thrown if an error occurs while mapping.
     * @throws IOException          Thrown if an error occurs while mapping.
     */
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, clazz);
    }

    protected void expectRestCallOk(String URI,HttpMethod httpMethod) {

        mockRestServiceServer.expect(requestTo(URI))
                .andExpect(method(httpMethod))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK));

    }

}
