package com.neoeteric.appointment;

import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Hospital {
    String hospitalname;
    String hospitaladdress;

    public Hospital(String hospitalname, String hospitaladdress) {
        this.hospitalname = hospitalname;
        this.hospitaladdress = hospitaladdress;
    }

    public static void main(String[] args){

        Hospital hospitalname = new Hospital("Yoshada","Survey No. 41/14, JNTU to Hitec City Road, Kothaguda, Hyderabad, Telangana 500084");
    }
}
