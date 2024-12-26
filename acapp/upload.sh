scp ./dist/css/*.css kob-server:kob/acapp/
scp ./dist/js/*.js kob-server:kob/acapp/

ssh kob-server 'cd ./kob/acapp && ./rename.sh'
