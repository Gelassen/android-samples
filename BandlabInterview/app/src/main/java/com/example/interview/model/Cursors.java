
package com.example.interview.model;

import java.util.HashMap;
import java.util.Map;

public class Cursors {

    private String before;
    private String after;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The before
     */
    public String getBefore() {
        return before;
    }

    /**
     * 
     * @param before
     *     The before
     */
    public void setBefore(String before) {
        this.before = before;
    }

    /**
     * 
     * @return
     *     The after
     */
    public String getAfter() {
        return after;
    }

    /**
     * 
     * @param after
     *     The after
     */
    public void setAfter(String after) {
        this.after = after;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
