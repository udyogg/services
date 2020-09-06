FROM cassandra:3.11
COPY cqlshrc /root/.cqlshrc
WORKDIR /root
RUN ls -l
WORKDIR /var/lib/cssandra/data/system/
RUN rm -rf .
