docker run -d \
--name db \
-e POSTGRES_PASSWORD=postgres \
-e POSTGRES_DB=cleevio \
-p 5432:5432 \
-v pgdata:/var/lib/postgresql/data \
--restart always \
postgres