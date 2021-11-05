package br.flower.boot.auth.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JacksonEnumConverter implements GenericConverter {

    private ObjectMapper mapper;

    private Set<ConvertiblePair> set;

    @Autowired
    public JacksonEnumConverter(ObjectMapper mapper) {
        set = new HashSet<>();
        set.add(new ConvertiblePair(String.class, Enum.class));
        this.mapper = mapper;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return set;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) {
            return null;
        }
        try {
            return mapper.readValue("\"" + source + "\"", targetType.getType());
        } catch (IOException e) {
        	return null;
            //throw new InvalidFieldException(targetType.getName(),source.toString());
        }
    }
}