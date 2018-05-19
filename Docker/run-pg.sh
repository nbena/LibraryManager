#!/bin/sh

docker run --rm -it --publish=5434:5432 --name library_manager_db pg_library_manager