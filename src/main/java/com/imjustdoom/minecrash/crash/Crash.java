package com.imjustdoom.minecrash.crash;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class Crash {

    private String error;
    private List<String> matches;
    private HashMap<String, String> arguments;
    private String solution;
}