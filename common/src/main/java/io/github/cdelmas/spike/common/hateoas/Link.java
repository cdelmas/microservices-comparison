/*
   Copyright 2015 Cyril Delmas

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.cdelmas.spike.common.hateoas;

public class Link {
    private String rel;
    private String href;
    private String method;

    public static Link self(String href) {
        return new Link("self", href);
    }

    public static Link remove(String href) {
        return new Link("remove", href,"DELETE");
    }

    public Link(String rel, String href, String method) {
        this.rel = rel;
        this.href = href;
        this.method = method;
    }

    public Link(String rel, String href) {
        this(rel, href, "GET");
    }

    Link() {
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
