
package com.example.interview.model;

import java.util.HashMap;
import java.util.Map;

public class Paging {

    private Cursors cursors;
    private String next;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The cursors
     */
    public Cursors getCursors() {
        return cursors;
    }

    /**
     * 
     * @param cursors
     *     The cursors
     */
    public void setCursors(Cursors cursors) {
        this.cursors = cursors;
    }

    /**
     * 
     * @return
     *     The next
     */
    public String getNext() {
        return next;
    }

    /**
     * 
     * @param next
     *     The next
     */
    public void setNext(String next) {
        this.next = next;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
