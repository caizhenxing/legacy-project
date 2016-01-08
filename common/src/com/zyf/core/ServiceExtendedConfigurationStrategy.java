package com.zyf.core;

public interface ServiceExtendedConfigurationStrategy {
    boolean isExtendedConfiguration(String content);
    
    Object adapter(Class interfaceName);
}
