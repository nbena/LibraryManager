#!/bin/sh

docker run --rm -d --publish=5435:5432 --name library_manager_db_test pg_library_manager_test
