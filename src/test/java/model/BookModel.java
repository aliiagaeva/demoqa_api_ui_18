package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BookModel {
    private String isbn;
    private String title;
    private String author;
    private String publisher;
}
