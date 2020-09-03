package com.cm.datalog;

import com.cm.datalog.entity.RequestInfo;

public class TracingLog extends BaseLog {
    
    private RequestInfo reqService;    // Request service (What)
    private int statusCode;          		// Response code
    private String content;         // Response body content
    private int duration;           // How much time took from request to response in milliseconds.
    private String invokeBy;		// Who send this request
    private String hostname;

    private TracingLog(Builder builder) {
        super(builder);
        this.reqService = builder.request;
        this.statusCode = builder.code;
        this.content = builder.content;
        this.duration = builder.duration;
        this.invokeBy = builder.invokeBy;
        this.hostname = builder.hostname;
    }

    public RequestInfo getReqService() {
        return reqService;
    }

    public void setReqService(RequestInfo service) {
        this.reqService = service;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int code) {
        this.statusCode = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getInvokeBy() {
        return invokeBy;
    }

    public void setInvokeBy(String invokeBy) {
        this.invokeBy = invokeBy;
    }

    public static class Builder extends BaseLog.Builder<Builder> {
        private RequestInfo request;
        private int code;
        private String content;
        private int duration;
        private String invokeBy;
        //节点名称
        private String hostname;

        @Override
        public BaseLog build() {
            return new TracingLog(this);
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder reqProvider(String provider) {
            if (provider != null) {
                createObjectIfNecessary();
                this.request.setProvider(provider);
            }
            return this;
        }
        public Builder reqService(String service) {
            if (service != null) {
                createObjectIfNecessary();
                this.request.setService(service);
            }
            return this;
        }
        public Builder reqFunction(String func) {
            if (func != null) {
                createObjectIfNecessary();
                this.request.setFunction(func);
            }
            return this;
        }
        public Builder reqMethod(String method) {
            if (method != null) {
                createObjectIfNecessary();
                this.request.setMethod(method);
            }
            return this;
        }
        public Builder reqURL(String url) {
            if (url != null) {
                createObjectIfNecessary();
                this.request.setUrl(url);
            }
            return this;
        }
        public Builder reqHeader(String header) {
            if (header != null) {
                createObjectIfNecessary();
                this.request.setHeader(header);
            }
            return this;
        }
        public Builder reqBody(String body) {
            if (body != null) {
                createObjectIfNecessary();
                this.request.setBody(body);
            }
            return this;
        }
        public Builder reqHost(String host) {
            if (host != null) {
                createObjectIfNecessary();
                this.request.setHost(host);
            }
            return this;
        }
        public Builder reqTimestamp(String timestamp) {
            if (timestamp != null) {
                createObjectIfNecessary();
                this.request.setTimestamp(timestamp);
            }
            return this;
        }
        public Builder reqExtra(Object extra) {
            if (extra != null) {
                createObjectIfNecessary();
                this.request.setExtra(extra);
            }
            return this;
        }
        public Builder respStatusCode(int code) {
            this.code = code;
            return this;
        }
        public Builder respContent(String content) {
            this.content = content;
            return this;
        }
        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }
        public Builder invokeBy(String invokeBy) {
            this.invokeBy = invokeBy;
            return this;
        }
        public Builder hostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        private void createObjectIfNecessary() {
            if (this.request == null) {
                this.request = new RequestInfo();
            }
        }
    }
}
