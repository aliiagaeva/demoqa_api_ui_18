package model;

import lombok.Data;

import java.util.List;

@Data
public class BookAddRequestModel {
    private String userId;
    private List<IsbnModel> collectionOfIsbns;
}