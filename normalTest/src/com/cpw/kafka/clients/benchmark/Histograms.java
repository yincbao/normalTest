package com.cpw.kafka.clients.benchmark;
import org.HdrHistogram.Histogram; 
 
import com.cpw.kafka.clients.benchmark.Consts; 
 
/**
 * Utility class that provides shortcut methods for 
 * histogram initializations. 
 */ 
public final class Histograms { 
 
    /**
     * Creates a new {@link org.HdrHistogram.Histogram} with {@link Consts.HIGHEST_TRACKABLE_VALUE} 
     * and {@link Consts.NUMBER_OF_SIGNIFICANT_VALUE_DIGITS} as arguments. 
     * 
     * @return 
     */ 
    public static Histogram create() { 
        return new Histogram(Consts.HIGHEST_TRACKABLE_VALUE, Consts.NUMBER_OF_SIGNIFICANT_VALUE_DIGITS); 
    } 
 
    /**
     * The caller references the static methods using <tt>Histograms.create()</tt>, 
     * and so on. Thus, the caller should be prevented from constructing objects of 
     * this class, by declaring this private constructor. 
     */ 
    private Histograms() { 
        //this prevents even the native class from 
        //calling this ctor as well : 
        throw new AssertionError(); 
    } 
}