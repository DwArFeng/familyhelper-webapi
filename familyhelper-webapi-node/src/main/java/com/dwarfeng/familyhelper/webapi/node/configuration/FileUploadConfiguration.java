package com.dwarfeng.familyhelper.webapi.node.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletContext;

@Configuration
public class FileUploadConfiguration {

    private final ServletContext servletContext;

    @Value("${file_upload.max_size}")
    private long maxSize;
    @Value("${file_upload.max_size_per_file}")
    private long maxSizePerFile;

    public FileUploadConfiguration(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Bean
    public CommonsMultipartResolver commonsMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setServletContext(servletContext);
        multipartResolver.setMaxUploadSize(maxSize);
        multipartResolver.setMaxUploadSizePerFile(maxSizePerFile);
        return multipartResolver;
    }
}
