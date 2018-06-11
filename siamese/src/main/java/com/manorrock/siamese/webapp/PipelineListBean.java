/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.webapp;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;
import static javax.faces.annotation.FacesConfig.Version.JSF_2_3;

/**
 * The JSF bean for showing a list of pipelines.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 */
@RequestScoped
@FacesConfig(version = JSF_2_3)
public class PipelineListBean {
    
    /**
     * Get the pipelines.
     * 
     * @return the pipelines.
     */
    public List<Pipeline> getPipelines() {
        return new ArrayList<>();
    }
}
