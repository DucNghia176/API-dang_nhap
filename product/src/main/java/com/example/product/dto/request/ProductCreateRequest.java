package com.example.product.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ProductCreateRequest {
    String productID;
    String productName; //tên sp
    Float price;    //giá
    int stock;  //sl tồn kho
    String category; //loại sp
}
