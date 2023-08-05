package com.michael.socialmedia.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;
@Getter
@Setter
public class UserPage {

    public   int pageNo=0;

    public   int pageSize= 10;

    public Sort.Direction sortDir = Sort.Direction.ASC;

    public   String sortBy = "username";


}
