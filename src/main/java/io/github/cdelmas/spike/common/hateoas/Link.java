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
