package common_tools;

import java.util.Map;

/**
 * Created by ivanybma on 4/25/16.
 */
public class RestHttpResponse {
        // the request url
        String url;

        public String getUrl() {
                return url;
        }

        public void setUrl(String url) {
                this.url = url;
        }

        public String getOrigin() {
                return origin;
        }

        public void setOrigin(String origin) {
                this.origin = origin;
        }

        public Map getHeaders() {
                return headers;
        }

        public void setHeaders(Map headers) {
                this.headers = headers;
        }

        public Map getArgs() {
                return args;
        }

        public void setArgs(Map args) {
                this.args = args;
        }

        public Map getForm() {
                return form;
        }

        public void setForm(Map form) {
                this.form = form;
        }

        public Map getJson() {
                return json;
        }

        public void setJson(Map json) {
                this.json = json;
        }

        // the requester ip
        String origin;

        // all headers that have been sent
        Map headers;

        // url arguments
        Map args;

        // post form parameters
        Map form;

        // post body json
        Map json;
}
