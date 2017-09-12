// IAddService.aidl
package com.example.pancho.w6project;

import java.util.List;

// Declare any non-default types here with import statements

interface ICalService {

     /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     int getPid();
     List<String> getRandomData();
}