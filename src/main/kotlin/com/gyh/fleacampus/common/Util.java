package com.gyh.fleacampus.common;

import com.gyh.fleacampus.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by gyh on 2021/2/4
 */
public class Util {
    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
