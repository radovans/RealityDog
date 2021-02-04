docker run -d -p 5532:5432 --name propertydog --restart always -e POSTGRES_USER=propertydog -e POSTGRES_PASSWORD=propertydog -e POSTGRES_DB=propertydog -v propertydog_postgres_data:/var/lib/postgresql/data postgres
docker run -d -p 3000:3000 --name metabase --restart always metabase/metabase
docker inspect propertydog - gateway 172.17.0.1:5532    
