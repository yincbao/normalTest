package com.cpw.kafka.clients;

import java.util.concurrent.Callable; 

import org.HdrHistogram.Histogram; 
 
/**
 * Primary interface that defines message consumer structure. 
 * Implement this if you want to add a new consumer type. 
 */ 
public interface MessageConsumer extends Callable<Histogram>, AutoCloseable { 
}