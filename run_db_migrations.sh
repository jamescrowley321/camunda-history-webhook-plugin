PGPASSWORD=strongP@ssw0rd psql -h localhost -p 5432 -U postgres -c "create database process_engine"
PGPASSWORD=strongP@ssw0rd psql -h localhost -p 5432 -U postgres -c "create user camunda with PASSWORD 'strongP@ssw0rd'"
PGPASSWORD=strongP@ssw0rd psql -h localhost -p 5432  -U postgres -c "GRANT CONNECT ON DATABASE process_engine TO camunda;"
PGPASSWORD=strongP@ssw0rd psql -h localhost -p 5432  -U postgres -c "GRANT USAGE ON SCHEMA public TO camunda;"
PGPASSWORD=strongP@ssw0rd psql -h localhost -p 5432 -U postgres -c "ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON TABLES TO camunda;"
PGPASSWORD=strongP@ssw0rd psql -h localhost -p 5432 -U postgres -c "ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON SEQUENCES TO camunda;"

wget -q -O scripts.zip https://app.camunda.com/nexus/repository/camunda-bpm/org/camunda/bpm/distro/camunda-sql-scripts/7.14.0/camunda-sql-scripts-7.14.0.zip
unzip scripts.zip
rm scripts.zip

PGPASSWORD=strongP@ssw0rd psql -h localhost -p 5432 -U postgres -f camunda-sql-scripts-7.14.0/create/postgres_engine_7.14.0.sql
PGPASSWORD=strongP@ssw0rd psql -h localhost -p 5432 -U postgres -f camunda-sql-scripts-7.14.0/create/postgres_identity_7.14.0.sql