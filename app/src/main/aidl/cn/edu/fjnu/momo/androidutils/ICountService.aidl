// ICountService.aidl
package cn.edu.fjnu.momo.androidutils;

// Declare any non-default types here with import statements

interface ICountService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    int getCount();
}
