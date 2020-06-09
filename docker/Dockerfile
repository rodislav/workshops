FROM nginx

LABEL maintainer="rodislav@gmail.com"

COPY index.html /usr/share/nginx/html

COPY index2.html /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]

# In case you want to change how you start nginx you can change the CMD's content
# CMD ["nginx", "-xyz"]

# The CMD has other ways of being described
# CMD service php5-fpm start && nginx

# http://goinbigdata.com/docker-run-vs-cmd-vs-entrypoint/

# CMD instruction allows you to set a default command, which will be executed only when you run container without specifying a command. If Docker container runs with a command, the default command will be ignored. If Dockerfile has more than one CMD instruction, all but last CMD instructions are ignored.

# ENTRYPOINT instruction allows you to configure a container that will run as an executable. It looks similar to CMD, because it also allows you to specify a command with parameters. The difference is ENTRYPOINT command and parameters are not ignored when Docker container runs with command line parameters. (There is a way to ignore ENTTRYPOINT, but it is unlikely that you will do it.)