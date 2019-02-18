package book;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.BookVO;

import java.io.IOException;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
class JSONtoBookConverter implements Converter<String, BookVO> {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    public BookVO convert(String jsonString) {
        try {
			return jsonMapper.readValue(jsonString, BookVO.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
}