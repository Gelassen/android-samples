
package com.example.interview.model.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ThumbnailData implements Serializable {

    private String id;
    private Integer height;
    private Integer scale;
    private String uri;
    private Integer width;
    private boolean isPreferred;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * 
     * @param height
     *     The height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * 
     * @return
     *     The scale
     */
    public Integer getScale() {
        return scale;
    }

    /**
     * 
     * @param scale
     *     The scale
     */
    public void setScale(Integer scale) {
        this.scale = scale;
    }

    /**
     * 
     * @return
     *     The uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * 
     * @param uri
     *     The uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * 
     * @return
     *     The width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * 
     * @param width
     *     The width
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * 
     * @return
     *     The isPreferred
     */
    public boolean getIsPreferred() {
        return isPreferred;
    }

    /**
     * 
     * @param isPreferred
     *     The is_preferred
     */
    public void setIsPreferred(boolean isPreferred) {
        this.isPreferred = isPreferred;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
