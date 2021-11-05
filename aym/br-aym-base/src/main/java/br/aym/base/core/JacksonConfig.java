package br.aym.base.core;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonConfig {

	private static final String zoneDateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    private static final DateTimeFormatter ISO_8601_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            //.withZone(ZoneId.of("GMT"));
    
    @Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(OffsetDateTime.class, new JsonSerializer<OffsetDateTime>() {
            @Override
            public void serialize(OffsetDateTime offsetDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            		throws IOException, JsonProcessingException {
                jsonGenerator.writeString(ISO_8601_FORMATTER.format(offsetDateTime));
            }
        });
        simpleModule.addDeserializer(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
			@Override
			public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt)
					throws IOException, JsonProcessingException {
				return OffsetDateTime.parse(p.getText(), ISO_8601_FORMATTER);
			}
           
        });
        
        System.out.println("Config is starting.");
        ObjectMapper mapper2 = builder.build()
				   .registerModule(new JavaTimeModule())
				   .registerModule(new Hibernate5Module())
				   .registerModule(simpleModule)
				   .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				   .configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true)
				   .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				   .enable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID)
				   .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				   .setDateFormat(new SimpleDateFormat(zoneDateTimeFormat));
        return mapper2;
    }
}
