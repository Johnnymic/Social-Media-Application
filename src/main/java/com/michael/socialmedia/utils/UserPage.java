package com.michael.socialmedia.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;
@Getter
@Setter
public class UserPage {

    private  int pageNo=0;

    private  int pageSize= 10;

    private Sort.Direction sortDir = Sort.Direction.ASC;

    private  String sortBy = "username";


}
