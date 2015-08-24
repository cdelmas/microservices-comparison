package io.github.cdelmas.spike.restlet.infrastructure;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.restlet.data.MediaType;
import org.restlet.ext.jackson.JacksonConverter;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;

public class JacksonCustomConverter extends JacksonConverter {

    @Override
    protected <T> JacksonRepresentation<T> create(MediaType mediaType, T source) {
        ObjectMapper mapper = createMapper();
        JacksonRepresentation<T> jr = new JacksonRepresentation<>(mediaType, source);
        jr.setObjectMapper(mapper);
        return jr;
    }

    @Override
    protected <T> JacksonRepresentation<T> create(Representation source, Class<T> objectClass) {
        ObjectMapper mapper = createMapper();
        JacksonRepresentation<T> jr = new JacksonRepresentation<>(source, objectClass);
        jr.setObjectMapper(mapper);
        return jr;
    }


    private ObjectMapper createMapper() {
        JsonFactory jsonFactory = new JsonFactory();
        jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        ObjectMapper mapper = new ObjectMapper(jsonFactory);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jdk8Module());
        return mapper;
    }

}