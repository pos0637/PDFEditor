package com.example.pdfdemo1.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 书签
 *
 * @author Alex
 */
@Getter
@Setter
@AllArgsConstructor
public class Bookmark {
    /**
     * 标题
     */
    private String title;

    /**
     * 页码
     */
    private int page;
}
