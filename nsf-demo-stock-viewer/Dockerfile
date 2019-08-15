FROM tomcat:7.0.77-jre8


ADD target/nsf*.war /usr/local/tomcat/webapps/

ADD nsf.yml /opt/nsf/nsf.yml

RUN cd /usr/local/tomcat/webapps && \
    mv nsf*.war ROOT.war && \
    rm -rf ROOT 
    
RUN wget "https://qingzhou-gitops.nos-eastchina1.126.net/nsf-agent/nsf-agent-v1-release-2.4-82f04565-20190814-172552.jar" -O nsf-agent.jar

RUN wget -P /nce-dev/nce/nsf-demo-stock/apm ﻿https://qingzhou-agent.nos-eastchina1.126.net/v5.3.2-190725-95c8ea61/napm-java-agent.tar.gz && \
    tar zxvf /nce-dev/nce/nsf-demo-stock/apm/napm-java-agent.tar.gz -C /nce-dev/nce/nsf-demo-stock/apm && \
    cd /nce-dev/nce/nsf-demo-stock/apm/napm-java-agent && \
    cd conf && \
    echo "endpoint=http://apm-collector.v190613.163skiff.com" >> napm-agent.properties

CMD ["catalina.sh", "run"]

