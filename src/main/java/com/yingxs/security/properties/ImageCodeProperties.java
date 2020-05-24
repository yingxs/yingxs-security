package com.yingxs.security.properties;

import lombok.Data;

@Data
public class ImageCodeProperties {

    private int width = 67;
    private int height = 23;
    private int length = 4;
    private int expireIn = 60;
    private boolean enable = false;
}
