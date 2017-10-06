package org.esupportail.activfo.web;

import java.util.Map;
import java.util.HashMap;
import javax.servlet.*;

import org.springframework.web.context.support.WebApplicationContextUtils;
import edu.yale.its.tp.cas.client.filter.CASFilter;
import edu.yale.its.tp.cas.proxy.ProxyTicketReceptor;

public class WebXml implements ServletContextListener {
        
    public void contextDestroyed(ServletContextEvent event) {}

    public void contextInitialized(ServletContextEvent event) {
        configure(event.getServletContext());
    }

    private void configure(ServletContext sc) {
        configureCas(sc);
    }

    private void configureCas(ServletContext sc) {
        Map<String, String> params = (Map<String, String>) WebApplicationContextUtils.getWebApplicationContext(sc).getBean("casParams");

        addFilter(sc, "CASFilter", CASFilter.class,
                  asMap("edu.yale.its.tp.cas.client.filter.loginUrl",         params.get("cas.url") + "/login")
                   .add("edu.yale.its.tp.cas.client.filter.validateUrl",      params.get("cas.url") + "/serviceValidate")
                   .add("edu.yale.its.tp.cas.client.filter.serverName",       params.get("tomcat.host") + ":" + params.get("tomcat.port"))
                   .add("edu.yale.its.tp.cas.client.filter.proxyCallbackUrl", params.get("cas.proxyCallbackUrl"))
                   .add("edu.yale.its.tp.cas.client.filter.wrapRequest", "true"),
                  "/stylesheets/welcomeCas.faces", "/stylesheets/accountDataChange.faces");
        addServlet(sc, "ProxyTicketReceptor", ProxyTicketReceptor.class, 
                   asMap("edu.yale.its.tp.cas.proxyUrl", params.get("cas.url") + "/proxy"),
                   "/CasProxyServlet");
    }

    static void addFilter(ServletContext sc, String name, Class<? extends Filter> clazz, Map<String,String> params, String... urls) {
        FilterRegistration.Dynamic o = sc.addFilter(name, clazz);
        if (params != null) o.setInitParameters(params);
        o.addMappingForUrlPatterns(null, true, urls);
    }
        
    static void addServlet(ServletContext sc, String name, Class<? extends Servlet> clazz, Map<String,String> params, String... urls) {
        ServletRegistration.Dynamic o = sc.addServlet(name, clazz);
        if (params != null) o.setInitParameters(params);
        o.addMapping(urls);
    }
    
    static <V> MapBuilder<V> asMap(String key, V value) {
        return new MapBuilder<V>().add(key, value);
    }

    @SuppressWarnings("serial")
    static class MapBuilder<V> extends HashMap<String, V> {
        MapBuilder<V> add(String key, V value) {
            this.put(key, value);
            return this;
        }
    }

}
